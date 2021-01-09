package com.example.timmo_songjas.feature.sign;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timmo_songjas.MainActivity;
import com.example.timmo_songjas.R;
import com.example.timmo_songjas.data.SigninData;
import com.example.timmo_songjas.data.SigninResponse;
import com.example.timmo_songjas.network.RetrofitClient;
import com.example.timmo_songjas.network.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.timmo_songjas.feature.utils.CommonValues.USER_TOKEN;
import static com.example.timmo_songjas.feature.utils.CommonValues.SERVER_USERID;
import static com.example.timmo_songjas.feature.utils.CommonValues.USER_NAME_LOGGED_IN;
import static com.example.timmo_songjas.feature.utils.CommonValues.SEVER_USERID_LOGGED_IN;

public class SignInActivity extends AppCompatActivity {
    private Button login;
    private TextView signup;

    private EditText et_email;
    private EditText et_pw;
    private String email,pw;

    RetrofitService service;
    SharedPreferences preferences;
    boolean saveSigninData = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        init(); //초기 세팅

        preferences = getSharedPreferences("appData", MODE_PRIVATE);
        service = RetrofitClient.getClient().create(RetrofitService.class);

        load();

        //이전에 로그인 정보를 저장시킨 기록이 있다면
        if(saveSigninData){
            et_email.setText(email);
            et_pw.setText(pw);
        }

        login.setOnClickListener(view -> startLogin(new SigninData(et_email.getText().toString() , et_pw.getText().toString() )));

        signup.setOnClickListener(v -> startActivity(new Intent(SignInActivity.this, SignUpActivity1.class )));

    }

    private void startLogin(SigninData data){
        //enqueue 로 비동기 통신 실행
        service.userSignin(data).enqueue(new Callback<SigninResponse>(){
            @Override
            public void onResponse(@NonNull Call<SigninResponse> call, @NonNull Response<SigninResponse> response) {
                if(response.isSuccessful()){

                    SigninResponse result = response.body();

                    Toast.makeText(SignInActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("signin 나와라", response.headers().toString());

                    USER_TOKEN = result.getData().getAccessToken();
                    Log.d("signin 토큰  ", USER_TOKEN );

                    setForFirebase(result.getData().getId() );
                    saveData();
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);

                }
                else{
                    Toast.makeText(SignInActivity.this, "onResponse 실패" + response.errorBody(),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<SigninResponse> call,@NonNull Throwable t) {
                Toast.makeText(SignInActivity.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("로그인 에러 발생", t.getMessage());
                t.printStackTrace(); // 에러 발생시 에러 발생 원인 단계별로 출력해줌
                //showProgress(false);
            }
        });

    }

    void init(){
        et_email = (EditText) findViewById(R.id.et_email_signin);
        et_pw = (EditText) findViewById(R.id.et_pw_signin);
        login = (Button) findViewById(R.id.btn_login_signin);
        signup = (TextView) findViewById(R.id.tx_signupbtn_signin);
    }


    //설정값을 저장하는 함수
    private  void saveData(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("SAVE_SIGNIN_DATA",true);
        editor.putString("ACCESS_TOKEN", USER_TOKEN );
        editor.putString("EMAIL",et_email.getText().toString());
        editor.putString("PW",et_pw.getText().toString());

        Toast.makeText(SignInActivity.this, "saveData : "+ USER_TOKEN, Toast.LENGTH_SHORT).show();

        editor.apply();
    }

    private void setForFirebase(int id ){
        //firebase 파트
        SERVER_USERID = id;
        SEVER_USERID_LOGGED_IN = Integer.toString(SERVER_USERID);

        //TODO:프로필 데이터에서 이름 받아와서 name에 설정
        USER_NAME_LOGGED_IN = SEVER_USERID_LOGGED_IN;

    }

    //설정값을 불러오는 함수
    private void load() {
        saveSigninData = preferences.getBoolean("SAVE_SIGNIN_DATA", false);
        email = preferences.getString("EMAIL", "");
        USER_TOKEN = preferences.getString("ACCESS_TOKEN", "");
        pw = preferences.getString("PW", "");
        Toast.makeText(SignInActivity.this, "load : "+ USER_TOKEN, Toast.LENGTH_SHORT).show();

    }

}