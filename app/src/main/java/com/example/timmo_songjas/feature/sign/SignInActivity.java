package com.example.timmo_songjas.feature.sign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.timmo_songjas.R;

public class SignInActivity extends AppCompatActivity {

    private Button signin;
    private TextView signup;

    private EditText et_email;
    private EditText et_pw;
    private String email,pw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        init();

        signin.setOnClickListener(view -> {

        });


        signup.setOnClickListener(v -> startActivity(new Intent(SignInActivity.this, SignUpActivity1.class )));
    }

    void init(){
        et_email = (EditText) findViewById(R.id.et_email_signin);
        et_pw = (EditText) findViewById(R.id.et_pw_signin);
        signin = (Button) findViewById(R.id.btn_login_signin);
        signup = (TextView) findViewById(R.id.tx_signupbtn_signin);
    }

}