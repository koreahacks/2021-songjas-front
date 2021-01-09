package com.example.timmo_songjas.feature.sign;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.timmo_songjas.R;
import com.example.timmo_songjas.chatting.model.UserModel;
import com.example.timmo_songjas.data.SignupData;
import com.example.timmo_songjas.data.SignupResponse;
import com.example.timmo_songjas.network.RetrofitClient;
import com.example.timmo_songjas.network.RetrofitService;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity2 extends AppCompatActivity {

    //signup1에서 받아온
    private String email;
    private String password;

    private EditText name;
    private Button btn_signup;
    private EditText largeAddress,smallAddress,univ,major,grade;

    private int id;

    RetrofitService service;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        //툴바
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_singup2);
        setSupportActionBar(mToolbar);
        //        getSupportActionBar().setDisplayShowTitleEnabled(false); //툴바 타이틀 삭제
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false); //툴바 타이틀 삭제
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //툴바 뒤로가기 버튼 생성
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left_24_dp);

        init();

        service = RetrofitClient.getClient().create(RetrofitService.class);

        btn_signup.setOnClickListener(v -> startSignup(new SignupData(email,password,
                name.getText().toString() ,
                largeAddress.getText().toString(),
                smallAddress.getText().toString(),
                univ.getText().toString(),
                major.getText().toString(),
                Integer.parseInt(grade.getText().toString()))
        ));
    }

    void init(){
        //signup1에서 받아온 데이터
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");

        name = (EditText) findViewById(R.id.et_nickname_signup2);
        btn_signup = (Button) findViewById(R.id.btn_finish_signup2);

        //private EditText
        largeAddress = (EditText)findViewById(R.id.et_city_do_signup2);
        smallAddress = (EditText)findViewById(R.id.et_gungu_signup2);
        univ = (EditText)findViewById(R.id.et_univ_signup2);
        major = (EditText)findViewById(R.id.et_major_signup2);
        grade = (EditText)findViewById(R.id.dt_grade_signup2);
    }

    private void startSignup(SignupData data){
        service.userSignup(data).enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(@NonNull Call<SignupResponse> call, @NonNull Response<SignupResponse> response) {
                if(response.isSuccessful()){
                    SignupResponse result = response.body();
                    Toast.makeText(SignUpActivity2.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                    id = result.getData().getId();
                    putDataToFirebase(id);
                    //firebase에서 데이터 넣기 성공하면 intent 넘긴다.
                }
                else{
                    Toast.makeText(SignUpActivity2.this, "onResponse 실패" + response.errorBody(),Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure( @NonNull Call<SignupResponse> call, @NonNull Throwable t) {

                Toast.makeText(SignUpActivity2.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("로그인 에러 발생", t.getMessage());
                t.printStackTrace(); // 에러 발생시 에러 발생 원인 단계별로 출력해줌
                //showProgress(false);
            }
        });


    }

    void putDataToFirebase(int id) {
        //TODO:프로필 수정의 imageUri에도 추가하기
        UserModel userModel = new UserModel();
        userModel.userName = name.getText().toString();
        userModel.userid = Integer.toString(id);
        //데이터 넣을 때 '.', '#', '$', '[', or ']' 있으면 불가!!

        Toast.makeText(SignUpActivity2.this, "firebase 데이터 넣는 중", Toast.LENGTH_SHORT).show();

        FirebaseDatabase.getInstance().getReference().child("users").push().setValue(userModel).addOnSuccessListener(aVoid -> { // 데이터 넣기 성공하면
            Toast.makeText(SignUpActivity2.this, "firebase 데이터 넣기 성공", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignUpActivity2.this, SignInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}