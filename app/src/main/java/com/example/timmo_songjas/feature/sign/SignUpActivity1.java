package com.example.timmo_songjas.feature.sign;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.timmo_songjas.R;

public class SignUpActivity1 extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText emailAuth;
    private Button btn_signup1;

    private boolean emailAuthEnd = true;     // TODO:false이 기본값 , 바꿔주기
    //TODO:서버 내용 들어와야함
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up1);

        init();


        btn_signup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailAuthEnd & password.getText().toString().equals("")) { //필수항목 다 입력해야해
                    Intent intent = new Intent(SignUpActivity1.this, SignUpActivity2.class);
                    intent.putExtra("email", email.getText().toString());
                    intent.putExtra("password", password.getText().toString());
                    startActivity(intent);
                }
            }

        } );

    }


    void init(){
        email = (EditText) findViewById(R.id.et_email_signup1);
        emailAuth = (EditText)findViewById(R.id.tet_pw_signup1);
        password = (EditText) findViewById(R.id.et_pw_signup1);
        btn_signup1 = (Button) findViewById(R.id.btn_next_signup1);
    }


}