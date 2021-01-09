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

public class Signup1Activity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText emailAuth;
    private Button btn_signup1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up1);

        init();

    }


    void init(){
        email = (EditText) findViewById(R.id.et_email_signup1);
        emailAuth = (EditText)findViewById(R.id.tet_pw_signup1);
        password = (EditText) findViewById(R.id.et_pw_signup1);
        btn_signup1 = (Button) findViewById(R.id.btn_next_signup1);
    }


}