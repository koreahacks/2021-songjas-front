package com.example.timmo_songjas.feature.member;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.timmo_songjas.R;
import com.example.timmo_songjas.chatting.chat.MessageActivity;
import com.example.timmo_songjas.data.MemberDetailResponse;
import com.example.timmo_songjas.feature.TeamTypeAdapter;
import com.example.timmo_songjas.feature.TeamTypeItem;
import com.example.timmo_songjas.network.RetrofitClient;
import com.example.timmo_songjas.network.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.timmo_songjas.feature.utils.CommonValues.USER_TOKEN;

public class MemberDetailActivity extends AppCompatActivity {



    //개인 성향 리싸이클러뷰 선언
    public ArrayList<TeamTypeItem> team_type_list = new ArrayList<>();
    private RecyclerView rv_teamtype;
    private TeamTypeAdapter teamTypeAdapter;


    //활동 내역 리싸이클러뷰 선언
    public ArrayList<MemberDetailCareerItem> career_list = new ArrayList<>();
    private RecyclerView rv_career;
    private MemberDetailCareerAdapter memberDetailCareerAdapter;

    //버튼
    private Button send_message;

    //xml id
    ImageView iv_profile_memberdetail;
    TextView tv_username_memberdetail;
    TextView tv_useruniv_memberdetail; //추가 해야 함.
    TextView tv_title_memberdetail, tv_date_memberdetail;
    TextView tv_largeaddress_memberdetail,tv_smalladdress_memberdetail;
    TextView tv_typecontent_memberdetail, tv_fieldcontent_memberdetail;
    TextView tv_hopecontent_memberdetail;
    TextView tv_introcontent_memberdetail;
    TextView tv_portfoliocontent_memberdetail;


    private int member_id;
    private int message_need_id =-1;

    //레트로핏
    RetrofitService service;

    @RequiresApi(api= Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detail);

        // TODO: MemberFind에서 선택한 팀글의 아이디를 전달 받음
        Intent intent = getIntent();
        member_id = intent.getIntExtra("member_id", 0);


        init();
        load(member_id);


        //TODO: 팀 빌딩 메시지
        send_message = findViewById(R.id.finish_memberdetail);
        send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //메시지 보내기 버튼
                if(message_need_id != -1)
                    sendMessage();

            }
        });

    }

    void init() {

        //툴바
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_timgle);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); //툴바 타이틀 삭제
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //툴바 뒤로가기 버튼 생성
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left_24_dp);

        team_type_list.clear();
        team_type_list.add(new TeamTypeItem("아침형"));
        team_type_list.add(new TeamTypeItem("계획형"));
        team_type_list.add(new TeamTypeItem("아침형"));

        career_list.clear();
        career_list.add(new MemberDetailCareerItem("팀매칭 및 협업 어플 개발", "2020.12.01"));

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
    }

    public void load(int id){
        service = RetrofitClient.getClient().create(RetrofitService.class);
        Call<MemberDetailResponse> call = service.memberDetail(USER_TOKEN, "application/json", id);
        call.enqueue(new Callback<MemberDetailResponse>() {
            @Override
            public void onResponse(Call<MemberDetailResponse> call, Response<MemberDetailResponse> response) {
                if(response.isSuccessful()) {
                    MemberDetailResponse result = response.body();
                    if (result.getStatus() == 200) {
                        Toast.makeText(getApplicationContext(), "팀모 조회 성공", Toast.LENGTH_SHORT).show();

                        //메세지
                        message_need_id = result.getUsers().getId();


                        if (result.getUsers().getImg() != null) {
                            Glide.with(getApplicationContext()).load(result.getUsers().getImg()).circleCrop().into(iv_profile_memberdetail);
                        }
                        tv_username_memberdetail.setText(result.getUsers().getName());
                        //TODO : 학교, 학과, 학년 분리 필요
                        tv_useruniv_memberdetail.setText(result.getUsers().getUniv());
                        tv_title_memberdetail.setText(result.getMembers().getTitle());
                        tv_date_memberdetail.setText(result.getMembers().getCreatedAt());
                        tv_largeaddress_memberdetail.setText(result.getUsers().getLargeAddress());
                        tv_smalladdress_memberdetail.setText(result.getUsers().getSmallAddress());
                        tv_typecontent_memberdetail.setText(result.getMembers().getType());
                        tv_fieldcontent_memberdetail.setText(result.getMembers().getField());

                        String pos = "";
                        List<MemberDetailResponse.MemberPositions> position_list = result.getMemberPositions();
                        for (int i = 0; i < position_list.size(); i++) {
                            pos = pos + position_list.get(i).getPosition();
                            if (i != position_list.size() - 1) pos = pos + " / ";
                        }
                        tv_hopecontent_memberdetail.setText(pos);

                        tv_introcontent_memberdetail.setText(result.getMembers().getContent());
                        tv_portfoliocontent_memberdetail.setText(result.getMembers().getLink());

                        //리싸이클러뷰 활동 내역, 개인 성향

                        //1. 활동 내역
                        List<MemberDetailResponse.MemberActivityies> careers = result.getMemberActivities();
                        for (int i = 0; i < careers.size(); i++) {
                            String m_content = careers.get(i).getContent();
                            String m_date = careers.get(i).getDate();
                            career_list.add(new MemberDetailCareerItem(m_content, m_date));
                        }

                        //2. 개인 성향
                        boolean morning = result.getUsers().getMorning();
                        boolean night = result.getUsers().getNight();
                        boolean dawn = result.getUsers().getDawn();
                        boolean plan = result.getUsers().getPlan();
                        boolean cramming = result.getUsers().getCramming();
                        boolean leader = result.getUsers().getLeader();
                        boolean follower = result.getUsers().getFollower();
                        boolean challenge = result.getUsers().getChallenge();
                        boolean realistic = result.getUsers().getRealistic();

                        if (morning)
                            team_type_list.add(new TeamTypeItem("아침형"));
                        if (night)
                            team_type_list.add(new TeamTypeItem("저녁형"));
                        if (dawn)
                            team_type_list.add(new TeamTypeItem("새벽형"));
                        if (plan)
                            team_type_list.add(new TeamTypeItem("계획형"));
                        if (cramming)
                            team_type_list.add(new TeamTypeItem("몰입형"));
                        if (leader)
                            team_type_list.add(new TeamTypeItem("리더"));
                        if (follower)
                            team_type_list.add(new TeamTypeItem("팔로우"));
                        if (challenge)
                            team_type_list.add(new TeamTypeItem("도전파"));
                        if (realistic)
                            team_type_list.add(new TeamTypeItem("현실파"));
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "연결 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MemberDetailResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "그냥 실패", Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void sendMessage(){
        Intent intent1 = new Intent(MemberDetailActivity.this , MessageActivity.class);
        intent1.putExtra("destinationUserid",Integer.toString(message_need_id)  );
        startActivity(intent1);
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