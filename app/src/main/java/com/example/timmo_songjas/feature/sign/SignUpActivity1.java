package com.example.timmo_songjas.feature.sign;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.timmo_songjas.R;
import com.example.timmo_songjas.data.EmailAuthData;
import com.example.timmo_songjas.data.EmailAuthResponse;
import com.example.timmo_songjas.network.RetrofitClient;
import com.example.timmo_songjas.network.RetrofitService;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity1 extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private EditText emailAuth;
    private Button btn_signup1;
    private Button btn_email;
    private String email_check_result;
        //이메일 인증 버튼 클릭 여부
    private boolean clickEmailAuth = false; // TODO: false이 기본값 , 바꿔주기
//이메일 인증 번호 최종 확인 완료
    private boolean emailAuthEnd = false;

    RetrofitService service;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up1);

        //툴바
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_singup1);
        setSupportActionBar(mToolbar);
        //        getSupportActionBar().setDisplayShowTitleEnabled(false); //툴바 타이틀 삭제
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false); //툴바 타이틀 삭제
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //툴바 뒤로가기 버튼 생성
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left_24_dp);

        init();

        service = RetrofitClient.getClient().create(RetrofitService.class);

        btn_signup1.setOnClickListener(v -> {
            if(!clickEmailAuth){//이메일 인증 버튼 누르지 않음
                Toast.makeText(SignUpActivity1.this, "이메일 인증해주세요", Toast.LENGTH_SHORT).show();
            }
            else if(email_check_result.equals(emailAuth.getText().toString()) ){ // 인증번호확인

                Toast.makeText(SignUpActivity1.this, "인증 성공", Toast.LENGTH_SHORT).show();
                emailAuthEnd = true ; // 이메일 성공 & 인증 끝남.
            }
            else{ //인증번호 틀림
                Toast.makeText(SignUpActivity1.this, "인증 실패", Toast.LENGTH_SHORT).show();
            }

            if(emailAuthEnd){ // 이메일 인증 성공했으니
                if(password.getText().toString().length() >= 6){ //비밀번호 6자리 이상
                    Intent intent = new Intent(SignUpActivity1.this, SignUpActivity2.class );
                    intent.putExtra("email",email.getText().toString());
                    intent.putExtra("password",password.getText().toString());
                    startActivity(intent);
                }
                else{
                    Toast.makeText(SignUpActivity1.this, "비밀번호는 6자리 이상 입력해주세요", Toast.LENGTH_SHORT).show();

                }
            }

        });


        btn_email.setOnClickListener(v->{
            startEmailAuth(new EmailAuthData( email.getText().toString()) );
        });
    }

    private void startEmailAuth(EmailAuthData data){
        Toast.makeText(SignUpActivity1.this, "인증번호가 발송되었습니다.",Toast.LENGTH_SHORT).show();

        btn_email.setEnabled(false);
        //enqueue 로 비동기 통신 실행
        service.emailAuth(data).enqueue(new Callback<EmailAuthResponse>() {
            @Override
            public void onResponse(@NonNull Call<EmailAuthResponse> call, @NonNull Response<EmailAuthResponse> response) {
                if(response.isSuccessful()){
                    EmailAuthResponse result = response.body();
                    email_check_result = result.getData().getAuthNum();
                    //인증번호 받아두고
                    clickEmailAuth = true;
                }
                else{
                    //Toast.makeText(SignUpActivity1.this, " 인증 실패 : " + response.errorBody(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<EmailAuthResponse> call, @NonNull Throwable t) {
                //Toast.makeText(SignUpActivity1.this, "이메일 인증 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("인증 에러 발생", t.getMessage());
                t.printStackTrace(); // 에러 발생시 에러 발생 원인 단계별로 출력해줌
                //showProgress(false);
            }

        });
        btn_email.setEnabled(true);
    }

    void init(){
        email = (EditText) findViewById(R.id.et_email_signup1);
        emailAuth = (EditText)findViewById(R.id.tet_pw_signup1);
        password = (EditText) findViewById(R.id.et_pw_signup1);
        btn_signup1 = (Button) findViewById(R.id.btn_next_signup1);
        btn_email = (Button)findViewById(R.id.btn_email_check);
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