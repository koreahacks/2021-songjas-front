package com.example.timmo_songjas.feature.member;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.timmo_songjas.R;
import com.example.timmo_songjas.feature.TeamTypeAdapter;
import com.example.timmo_songjas.feature.TeamTypeItem;

import java.util.ArrayList;

public class MemberDetailActivity extends AppCompatActivity {


    public ArrayList<TeamTypeItem> team_type_list = new ArrayList<>();
    private RecyclerView rv_teamtype;
    private TeamTypeAdapter teamTypeAdapter;

    public ArrayList<MemberDetailCareerItem> career_list = new ArrayList<>();
    private RecyclerView rv_career;
    private MemberDetailCareerAdapter memberDetailCareerAdapter;

    private Button send_message;

    private void initDataset() {
        team_type_list.clear();
        team_type_list.add(new TeamTypeItem("아침형"));
        team_type_list.add(new TeamTypeItem("계획형"));
        team_type_list.add(new TeamTypeItem("아침형"));

        career_list.clear();
        career_list.add(new MemberDetailCareerItem("팀매칭 및 협업 어플 개발", "2020.12.01"));
    }

    private int member_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detail);

        // TODO: MemberFind에서 선택한 팀글의 아이디를 전달 받음
        Intent intent = getIntent();
        member_id = intent.getIntExtra("member_id", 0);

        //data
        initDataset();

        //team_type
        rv_teamtype = findViewById(R.id.rv_teamtype_memberdetail);
        teamTypeAdapter = new TeamTypeAdapter(team_type_list, this);
        LinearLayoutManager mLinearLayoutManeger1 = new LinearLayoutManager(this);
        mLinearLayoutManeger1.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_teamtype.setLayoutManager(mLinearLayoutManeger1);
        rv_teamtype.setAdapter(teamTypeAdapter);

        //career
        rv_career = findViewById(R.id.rv_careercontent_memberdetail);
        memberDetailCareerAdapter = new MemberDetailCareerAdapter(career_list, this);
        LinearLayoutManager mLinearLayoutManager2 = new LinearLayoutManager(this);
        mLinearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_career.setLayoutManager(mLinearLayoutManager2);
        rv_career.setAdapter(memberDetailCareerAdapter);


        //TODO: 팀 빌딩 메시지
        send_message = findViewById(R.id.finish_memberdetail);
        send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //메시지 보내기 버튼
//                sendMessage();

            }
        });

        //툴바
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_timgle);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); //툴바 타이틀 삭제
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //툴바 뒤로가기 버튼 생성
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left_24_dp);
    }

    void init(){

    }
    public void sendMessage(){

//        Intent intent1 = new Intent(MemberDetailActivity.this ,MessageActivity.class);
//        //TODO:String으로 바꿔서 ID 보내기!!
//        intent1.putExtra("destinationUserid",member_id);
//        startActivity(intent1);
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