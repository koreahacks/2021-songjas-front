package com.example.timmo_songjas.feature.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timmo_songjas.R;
import com.example.timmo_songjas.data.ProjectMembers;
import com.example.timmo_songjas.data.TimgleListResponse;
import com.example.timmo_songjas.network.RetrofitClient;
import com.example.timmo_songjas.network.RetrofitService;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.timmo_songjas.feature.utils.CommonValues.PARTY_TO_PROADD;
import static com.example.timmo_songjas.feature.utils.CommonValues.USER_TOKEN;

public class ProjectAdd2Activity extends AppCompatActivity {

    Spinner spText;
    String timgleString;
    int morningCount;
    int nightCount;
    int dawnCount;
    int planCount;
    int focusCount;
    int leaderCount;
    int followCount;
    int challCount;
    int realCount;
    String id;
    String email;
    String name;
    String img;

    String[] titleStr;

    public ArrayList<ProjectAddMember> member_list = new ArrayList<>();
    private RecyclerView rv_member;
    private ProjectAddMemberAdapter projectAddMemberAdapter;

    RetrofitService service1;
    List<String> timgleTitle = new ArrayList<>();
    List<Integer> userId = new ArrayList<>();
    List<ProjectMembers> membersList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_add2);

        //툴바
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_timmoadd2);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); //툴바 타이틀 삭제
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //툴바 뒤로가기 버튼 생성
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left_24_dp);


        //add1에서 정보 받아오기
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String type = intent.getStringExtra("type");
        String feild = intent.getStringExtra("feild");
        String startDate = intent.getStringExtra("startDate");
        String endDate = intent.getStringExtra("endDate");
        String content = intent.getStringExtra("content");


        //TODO: 서버에서 이력서 제목 받아와서 String 배열로 만들기

        //스피너 구현(팀글 첨부)
        //String[] timgle  = {"나야나", "나를 데리겨", "나는 최고", "팀글을 선택하세요"};
        spText = (Spinner)findViewById(R.id.sp_text_project);
        ArrayAdapter<String> textAapter = new ArrayAdapter<String>(this, R.layout.item_spinner, timgleTitle){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                return v;
            }
            @Override
            public int getCount() {
                return super.getCount(); // you dont display last item. It is used as hint.
            }
        };
        textAapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spText.setAdapter(textAapter);
        //spText.setSelection(textAapter.getCount());


        spText.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                timgleString =timgleTitle.get(position);
                //timgleString = timgle[position];
                Log.d("스피너 선택", timgleString);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //포트폴리오 입력받기
        EditText dtPort =(EditText)findViewById(R.id.et_port_project);

        //개인성향 이벤트 처리
        //아침형
        morningCount = 0;
        TextView tvMorning = (TextView) findViewById(R.id.tv_morning_filter);
        tvMorning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                morningCount++;
                if(morningCount %2 == 1){
                    tvMorning.setTextColor(Color.WHITE);
                    tvMorning.setBackground(getResources().getDrawable(R.drawable.btn_orange_selected));

                }
                else{
                    tvMorning.setTextColor(Color.parseColor("#c5ccd6"));
                    tvMorning.setBackground(getResources().getDrawable(R.drawable.text_grey_rect));
                }
            }
        });
        //저녁
        nightCount = 0;
        TextView tvNight = (TextView) findViewById(R.id.tv_night_filter);
        tvNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nightCount++;
                if(nightCount %2 == 1){
                    tvNight.setTextColor(Color.WHITE);
                    tvNight.setBackground(getResources().getDrawable(R.drawable.btn_orange_selected));

                }
                else{
                    tvNight.setTextColor(Color.parseColor("#c5ccd6"));
                    tvNight.setBackground(getResources().getDrawable(R.drawable.text_grey_rect));
                }
            }
        });
        //새벽
        dawnCount = 0;
        TextView tvDawn = (TextView) findViewById(R.id.tv_dawn_filter);
        tvDawn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dawnCount++;
                if(dawnCount %2 == 1){
                    tvDawn.setTextColor(Color.WHITE);
                    tvDawn.setBackground(getResources().getDrawable(R.drawable.btn_orange_selected));

                }
                else{
                    tvDawn.setTextColor(Color.parseColor("#c5ccd6"));
                    tvDawn.setBackground(getResources().getDrawable(R.drawable.text_grey_rect));
                }
            }
        });
        //계획
        planCount = 0;
        TextView tvPlan = (TextView) findViewById(R.id.tv_plan_filter);
        tvPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                planCount++;
                if(planCount %2 == 1){
                    tvPlan.setTextColor(Color.WHITE);
                    tvPlan.setBackground(getResources().getDrawable(R.drawable.btn_orange_selected));

                }
                else{
                    tvPlan.setTextColor(Color.parseColor("#c5ccd6"));
                    tvPlan.setBackground(getResources().getDrawable(R.drawable.text_grey_rect));
                }
            }
        });
        //몰입
        focusCount = 0;
        TextView tvFocus = (TextView) findViewById(R.id.tv_focus_filter);
        tvFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                focusCount++;
                if(focusCount %2 == 1){
                    tvFocus.setTextColor(Color.WHITE);
                    tvFocus.setBackground(getResources().getDrawable(R.drawable.btn_orange_selected));

                }
                else{
                    tvFocus.setTextColor(Color.parseColor("#c5ccd6"));
                    tvFocus.setBackground(getResources().getDrawable(R.drawable.text_grey_rect));
                }
            }
        });
        //리더
        leaderCount= 0;
        TextView tvLeader = (TextView) findViewById(R.id.tv_leader_filter);
        tvLeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leaderCount++;
                if(leaderCount %2 == 1){
                    tvLeader.setTextColor(Color.WHITE);
                    tvLeader.setBackground(getResources().getDrawable(R.drawable.btn_orange_selected));

                }
                else{
                    tvLeader.setTextColor(Color.parseColor("#c5ccd6"));
                    tvLeader.setBackground(getResources().getDrawable(R.drawable.text_grey_rect));
                }
            }
        });
        //팔로워
        followCount= 0;
        TextView tvFollow = (TextView) findViewById(R.id.tv_follow_filter);
        tvFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followCount++;
                if(followCount %2 == 1){
                    tvFollow.setTextColor(Color.WHITE);
                    tvFollow.setBackground(getResources().getDrawable(R.drawable.btn_orange_selected));

                }
                else{
                    tvFollow.setTextColor(Color.parseColor("#c5ccd6"));
                    tvFollow.setBackground(getResources().getDrawable(R.drawable.text_grey_rect));
                }
            }
        });
        //도전
        challCount= 0;
        TextView tvChall = (TextView) findViewById(R.id.tv_challenge_filter);
        tvChall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                challCount++;
                if(challCount %2 == 1){
                    tvChall.setTextColor(Color.WHITE);
                    tvChall.setBackground(getResources().getDrawable(R.drawable.btn_orange_selected));

                }
                else{
                    tvChall.setTextColor(Color.parseColor("#c5ccd6"));
                    tvChall.setBackground(getResources().getDrawable(R.drawable.text_grey_rect));
                }
            }
        });
        //현실
        realCount= 0;
        TextView tvReal = (TextView) findViewById(R.id.tv_real_filter);
        tvReal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realCount++;
                if(realCount %2 == 1){
                    tvReal.setTextColor(Color.WHITE);
                    tvReal.setBackground(getResources().getDrawable(R.drawable.btn_orange_selected));

                }
                else{
                    tvReal.setTextColor(Color.parseColor("#c5ccd6"));
                    tvReal.setBackground(getResources().getDrawable(R.drawable.text_grey_rect));
                }
            }
        });

        //팀원 추가하기
        ImageView ivMemberAdd =(ImageView)findViewById(R.id.iv_plusmember_project);
        ivMemberAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), PartyFindActivity.class);
                startActivityForResult(intent, PARTY_TO_PROADD);
            }
        });

        //member_list.add(new ProjectAddMember("https://joepark7.files.wordpress.com/2015/04/d189ec9889__jpg.jpg", "눈송이"));

        //리싸이클러뷰로 멤버 보여주기
        rv_member = findViewById(R.id.rv_member_project);
        projectAddMemberAdapter = new ProjectAddMemberAdapter(member_list, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_member.setLayoutManager(linearLayoutManager);
        rv_member.setAdapter(projectAddMemberAdapter);

        //다음 버튼
        Button nextBtn = (Button)findViewById(R.id.btn_next_project);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProjectAdd3Activity.class);
                try {
                    Toast.makeText(getApplicationContext(), timgleString, Toast.LENGTH_LONG).show();
                    //add1에서 받아온 데이터
                    intent.putExtra("title", title);
                    intent.putExtra("type", type);
                    intent.putExtra("feild", feild);
                    intent.putExtra("startDate", startDate);
                    intent.putExtra("endDate", endDate);
                    intent.putExtra("content", content);

                    //add에서 입력 받은 데이터
                    intent.putExtra("timgle", timgleString);
                    intent.putExtra("port", dtPort.getText().toString());
                    intent.putExtra("morning", String.valueOf(morningCount % 2));
                    intent.putExtra("night", String.valueOf(nightCount % 2));
                    intent.putExtra("dawn", String.valueOf(dawnCount % 2));
                    intent.putExtra("plan", String.valueOf(planCount % 2));
                    intent.putExtra("focus", String.valueOf(focusCount % 2));
                    intent.putExtra("leader", String.valueOf(leaderCount % 2));
                    intent.putExtra("follow", String.valueOf(followCount % 2));
                    intent.putExtra("challenge", String.valueOf(challCount % 2));
                    intent.putExtra("reality", String.valueOf(realCount % 2));

                    //TODO: 선택된 팀원 정보-->(PartyFind에서 받아와야 함)
                    intent.putExtra("id", id);
                    intent.putExtra("email", email);
                    intent.putExtra("name", name);
                    intent.putExtra("img", img);

                    startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "모든 항목을 정확히 입력해주세요.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //TODO:데이터 받아오기
    //팀글 목록 가져오기
    public void loadTimgleList(){
        service1 = RetrofitClient.getClient().create(RetrofitService.class);
        Call<TimgleListResponse> call = service1.userTigleList(USER_TOKEN);
        call.enqueue(new Callback<TimgleListResponse>() {
            @Override
            public void onResponse(Call<TimgleListResponse> call, Response<TimgleListResponse> response) {
                if (response.isSuccessful()) {
                    TimgleListResponse result = response.body();
                    if (result.getStatus() == 200) {
                        timgleTitle.clear();
                        userId.clear();
                        //int num = result.getData().size();
                        for(int i =0; i < result.getData().size(); i++){
                            timgleTitle.add(result.getData().get(i).getTitle());
                            userId.add(result.getData().get(i).getId());
                            //titleStr[i] = result.getData().get(i).getTitle();
                        }
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "연결 실패", Toast.LENGTH_SHORT).show();
                    Log.d("연결: ", "연결 실패");
                }
            }

            @Override
            public void onFailure(Call<TimgleListResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "그냥 실패", Toast.LENGTH_SHORT).show();
                Log.d("연결: ", "완전 실패");
            }
        });
    }

    //partyFind 에서 팀원 데이터 받아오기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PARTY_TO_PROADD){
            id = data.getStringExtra("id");
            email = data.getStringExtra("email");
            name = data.getStringExtra("name");
            img = data.getStringExtra("img");

            //add3로 넘겨야 할 데이터
            membersList.add(Integer.parseInt(id), null);

            //TODO: 리싸이클러뷰로 보여주기
            member_list.add(new ProjectAddMember(img, name));
            projectAddMemberAdapter.notifyDataSetChanged();
        }
    }

    //툴바
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