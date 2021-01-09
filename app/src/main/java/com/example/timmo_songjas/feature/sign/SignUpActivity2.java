package com.example.timmo_songjas.feature.sign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.timmo_songjas.R;

public class SignUpActivity2 extends AppCompatActivity {

    //signup1에서 받아올
    private String email;
    private String password;

    private EditText name;
    private Button btn_signup;
    private EditText largeAddress,smallAddress,univ,major,grade;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        init();

        btn_signup.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity2.this, SignInActivity.class);
            startActivity(intent);
        });
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



}