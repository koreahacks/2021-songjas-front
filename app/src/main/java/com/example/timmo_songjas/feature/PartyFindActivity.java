package com.example.timmo_songjas.feature;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.timmo_songjas.R;


public class PartyFindActivity extends AppCompatActivity {


    private ImageView back_btn;
    private ImageView pic_partyfind;
    private TextView tv_name_partyfind;
    private TextView tv_email_partyfind;

    ConstraintLayout result_partyfind_true;
    TextView result_partyfind_false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_find);

        init();

        //툴바
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_partyfind);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //뒤로가기
        back_btn = (ImageView) mToolbar.findViewById(R.id.back_partyfind);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        SearchView searchView = findViewById(R.id.search_view_partyfind);
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setIconifiedByDefault(false);
        searchView.setIconified(false); //키보드 위로 올라오도록

        //SearchView의 검색 이벤트
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {  //키워드 입력 후 엔터 입력
                //Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
                //load(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) { return false; }
        });
    }

    void init(){
        //검색 결과 true/false
        result_partyfind_true = findViewById(R.id.result_partyfind_true);
        result_partyfind_false = findViewById(R.id.result_partyfind_false);

        pic_partyfind = (ImageView) findViewById(R.id.pic_partyfind);
        tv_name_partyfind = (TextView) findViewById(R.id.tv_name_partyfind);
        tv_email_partyfind = findViewById(R.id.tv_email_partyfind);
    }

}
