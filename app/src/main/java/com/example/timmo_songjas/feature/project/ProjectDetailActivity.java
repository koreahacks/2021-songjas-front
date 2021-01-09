package com.example.timmo_songjas.feature.project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.example.timmo_songjas.R;
import com.example.timmo_songjas.chatting.chat.MessageActivity;
import com.example.timmo_songjas.data.ProjectDetailApplyData;
import com.example.timmo_songjas.data.ProjectDetailApplyResponse;
import com.example.timmo_songjas.data.ProjectDetailChoiceTimgleResponse;
import com.example.timmo_songjas.data.ProjectDetailResponse;
import com.example.timmo_songjas.feature.TeamTypeAdapter;
import com.example.timmo_songjas.feature.TeamTypeItem;
import com.example.timmo_songjas.network.RetrofitClient;
import com.example.timmo_songjas.network.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.timmo_songjas.feature.utils.CommonValues.USER_TOKEN;

public class ProjectDetailActivity extends AppCompatActivity {

    //팀성향 리싸이클러뷰 선언
    public ArrayList<TeamTypeItem> team_type_list = new ArrayList<>();
    private RecyclerView rv_teamtype;
    private TeamTypeAdapter projectDetailTeamTypeAdapter;

    //팀원 리싸이클러뷰 선언
    public ArrayList<ProjectDetailMemberItem> member_list = new ArrayList<>();
    private RecyclerView rv_member;
    private ProjectDetailMemberAdapter projectDetailMemberAdapter1;

    //지원자 리싸이클러뷰 선언
    public ArrayList<ProjectDetailMemberItem> apply_list = new ArrayList<>();
    private RecyclerView rv_apply;
    private ProjectDetailMemberAdapter projectDetailMemberAdapter2;

    //버튼
    private Button btn_no_applier; //미지원자
    private ImageView btn_chat;

    //xml id
    ImageView iv_intro_projectdetail;
    TextView tv_univ_projectdetail,tv_dday_projectdetail;
    TextView tv_title_projectdetail,tv_date_projectdetail;

    TextView tv_largeaddress_projectdetail,tv_smalladdress_projectdetail;
    TextView tv_startdate_projectdetail, tv_enddate_projectdetail;
    TextView tv_typecontent_projectdetail,tv_filedcontent_projectdetail,tv_hopecontent_projectdetail;

    TextView tv_collectcontent_projectdetail;

    TextView tv_linkcontent_productdetail;

    RecyclerView rv_teamtype_projectdetail, rv_teammember_projectdetail, rv_teamapply_projectdetail;



    List<String> title = new ArrayList<>() ;
    List<Integer> timgle_id = new ArrayList<>();
    boolean flag =false;


    private int project_id, member_id;

    //레트로핏
    RetrofitService service;

    @RequiresApi
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);

        //TODO: ProjectFind에서 선택한 모집글의 아이디를 전달받음
        Intent intent = getIntent();
        project_id = intent.getIntExtra("project_id", 1);
        Log.e("idddddddddddddddddddd", Integer.toString(project_id));
        //data
        init();

        loadDetail(project_id);
    }

    void init(){

        //툴바
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_timmo);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); //툴바 타이틀 삭제
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //툴바 뒤로가기 버튼 생성
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left_24_dp);

        team_type_list.clear();
        member_list.clear();
        apply_list.clear();

        //team_type
        rv_teamtype = findViewById(R.id.rv_teamtype_projectdetail);
        projectDetailTeamTypeAdapter = new TeamTypeAdapter(team_type_list, this);
        LinearLayoutManager mLinearLayoutManeger = new LinearLayoutManager(this);
        mLinearLayoutManeger.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_teamtype.setLayoutManager(mLinearLayoutManeger);
        rv_teamtype.setAdapter(projectDetailTeamTypeAdapter);

        //member
        rv_member = findViewById(R.id.rv_teammember_projectdetail);
        projectDetailMemberAdapter1 = new ProjectDetailMemberAdapter(member_list, this);
        LinearLayoutManager mLinearLayoutManeger2 = new LinearLayoutManager(this);
        mLinearLayoutManeger2.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_member.setLayoutManager(mLinearLayoutManeger2);
        rv_member.setAdapter(projectDetailMemberAdapter1);

        //apply
        rv_apply = findViewById(R.id.rv_teamapply_projectdetail);
        projectDetailMemberAdapter2 = new ProjectDetailMemberAdapter(apply_list, this);
        LinearLayoutManager mLinearLayoutManeger3 = new LinearLayoutManager(this);
        mLinearLayoutManeger3.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_apply.setLayoutManager(mLinearLayoutManeger3);
        rv_apply.setAdapter(projectDetailMemberAdapter2);


        //버튼 바인딩. 사용자 flag에 따라 visibility 조정 예정
        btn_no_applier = findViewById(R.id.btn_no_applier);
        btn_chat = findViewById(R.id.btn_chat);

        iv_intro_projectdetail = findViewById(R.id.iv_intro_projectdetail);

        tv_univ_projectdetail = findViewById(R.id.tv_univ_projectdetail);
        tv_dday_projectdetail = findViewById(R.id.tv_dday_projectdetail);
        tv_date_projectdetail = findViewById(R.id.tv_date_projectdetail);

        tv_title_projectdetail = findViewById(R.id.tv_title_projectdetail);

        tv_largeaddress_projectdetail = findViewById(R.id.tv_largeaddress_projectdetail);
        tv_smalladdress_projectdetail = findViewById(R.id.tv_smalladdress_projectdetail);

        tv_startdate_projectdetail = findViewById(R.id.tv_startdate_projectdetail);
        tv_enddate_projectdetail = findViewById(R.id.tv_enddate_projectdetail);

        tv_typecontent_projectdetail = findViewById(R.id.tv_typecontent_projectdetail);
        tv_filedcontent_projectdetail = findViewById(R.id.tv_filedcontent_projectdetail);

        tv_hopecontent_projectdetail = findViewById(R.id.tv_hopecontent_projectdetail);

        tv_collectcontent_projectdetail = findViewById(R.id.tv_collectcontent_projectdetail);

        tv_linkcontent_productdetail = findViewById(R.id.tv_linkcontent_productdetail);

        rv_teamtype_projectdetail = findViewById(R.id.rv_teamtype_projectdetail);
        rv_teammember_projectdetail = findViewById(R.id.rv_teammember_projectdetail);
        rv_teamapply_projectdetail = findViewById(R.id.rv_teamapply_projectdetail);


    }

    private void loadDetail(int id){
        service = RetrofitClient.getClient().create(RetrofitService.class);
        Call<ProjectDetailResponse> call = service.projectDetail(USER_TOKEN, "application/json", id);

        call.enqueue(new Callback<ProjectDetailResponse>(){
            @Override
            public void onResponse(Call<ProjectDetailResponse> call, Response<ProjectDetailResponse> response){
                if(response.isSuccessful()) {
                    ProjectDetailResponse result = response.body();
                    if (result.getStatus() == 200) {
                        Toast.makeText(getApplicationContext(), "팀모 조회 성공", Toast.LENGTH_SHORT).show();

                        //TODO: 자대, D-day는 귀찮으니까 전달 받으면 안되나여?

                        boolean limitUniv;

                        tv_date_projectdetail.setText(result.getProjects().getCreatedAt());
                        tv_title_projectdetail.setText(result.getProjects().getTitle());
                        tv_largeaddress_projectdetail.setText(result.getProjects().getLargeAddress());
                        tv_smalladdress_projectdetail.setText(result.getProjects().getSmallAddress());
                        tv_startdate_projectdetail.setText(result.getProjects().getStartDate());
                        tv_enddate_projectdetail.setText(result.getProjects().getEndDate());
                        tv_typecontent_projectdetail.setText(result.getProjects().getType());
                        tv_filedcontent_projectdetail.setText(result.getProjects().getField());

                        String pos = "";
                        List<ProjectDetailResponse.ProjectPositions> position_list = result.getProjectPositions();
                        for(int i = 0 ; i < position_list.size() ; i++){
                            pos = pos + position_list.get(i).getPosition() + " "+ position_list.get(i).getHeadCount()+"명" ;
                            if(i != position_list.size()-1 ) pos = pos + " / ";
                        }
                        tv_hopecontent_projectdetail.setText(pos);


                        tv_collectcontent_projectdetail.setText(result.getProjects().getContent());
                        tv_linkcontent_productdetail.setText(result.getProjects().getLink());

                        //리싸이클러뷰 팀성향, 팀원, 지원자
                        //1. 팀성향
                        boolean morning = result.getProjects().getMorning();
                        boolean night = result.getProjects().getNight();
                        boolean dawn = result.getProjects().getDawn();
                        boolean plan = result.getProjects().getPlan();
                        boolean cramming = result.getProjects().getCramming();
                        boolean leader = result.getProjects().getLeader();
                        boolean follower = result.getProjects().getFollower();
                        boolean challenge = result.getProjects().getChallenge();
                        boolean realistic = result.getProjects().getRealistic();

                        if(morning)
                            team_type_list.add(new TeamTypeItem("아침형"));
                        if(night)
                            team_type_list.add(new TeamTypeItem("저녁형"));
                        if(dawn)
                            team_type_list.add(new TeamTypeItem("새벽형"));
                        if(plan)
                            team_type_list.add(new TeamTypeItem("계획형"));
                        if(cramming)
                            team_type_list.add(new TeamTypeItem("몰입형"));
                        if(leader)
                            team_type_list.add(new TeamTypeItem("리더"));
                        if(follower)
                            team_type_list.add(new TeamTypeItem("팔로우"));
                        if(challenge)
                            team_type_list.add(new TeamTypeItem("도전파"));
                        if(realistic)
                            team_type_list.add(new TeamTypeItem("현실파"));

                        //2 팀원
                        List<ProjectDetailResponse.ProjectMembers> members = result.getProjectMembers();
                        for(int i =0; i < members.size(); i++){
                            String m_img = members.get(i).getUserImg();
                            String m_name = members.get(i).getUserName();
                            member_list.add(new ProjectDetailMemberItem(m_img, m_name));
                        }

                        //3. 지원자
                        List<ProjectDetailResponse.ProjectApplicants> appliers = result.getProjectApplicant();
                        for(int i=0; i<appliers.size();i++){
                            String a_img = appliers.get(i).getUserImg();
                            String a_name = appliers.get(i).getUserName();
                            apply_list.add(new ProjectDetailMemberItem(a_img, a_name));
                        }

                        //4. 버튼
                        boolean apply = result.getButton();
                        if(apply){
                            btn_no_applier.setVisibility(View.VISIBLE);
                            btn_chat.setVisibility(View.VISIBLE);

                            btn_no_applier.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.d("지원 " , "버튼 누름");
                                    choiceTimegle(); //지원서 등록하기
                                }
                            });
                            btn_chat.setOnClickListener(new View.OnClickListener(){
                                @Override
                                public void onClick(View v){
                                    Intent intent1 = new Intent(ProjectDetailActivity.this , MessageActivity.class);
                                    intent1.putExtra("destinationUserid",  Integer.toString(result.getProjects().getUserId() ) );
                                    startActivity(intent1);
                                }
                            });
                        }
                        else {
                            btn_no_applier.setVisibility(View.GONE);
                            btn_chat.setVisibility(View.GONE);
                        }


                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "연결 실패", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ProjectDetailResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "그냥 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void choiceTimegle() {
        flag = false;

        Call<ProjectDetailChoiceTimgleResponse> call2 = service.projectDetailChoiceTimgle(USER_TOKEN, "application/json");

        call2.enqueue(new Callback<ProjectDetailChoiceTimgleResponse>() {
            @Override
            public void onResponse(Call<ProjectDetailChoiceTimgleResponse> call, Response<ProjectDetailChoiceTimgleResponse> response) {
                if (response.isSuccessful()) {
                    ProjectDetailChoiceTimgleResponse result2 = response.body();
                    if (result2.getStatus() == 200) {
                        Toast.makeText(getApplicationContext(), "지원 조회 성공", Toast.LENGTH_SHORT).show();

                        List<ProjectDetailChoiceTimgleResponse.Datum> apply_timgles = result2.getData();

                        //Log.d("지원하기 루프  : ", String.valueOf(apply_timgles.size()));
                        for(ProjectDetailChoiceTimgleResponse.Datum item : apply_timgles){
                            title.add(item.getTitle());
                            timgle_id.add(item.getId());
                            Log.d("지원하기 루프  : ", item.getTitle());
                        }

                        //alert의 title과 Messege 세팅
                        AlertDialog alertDialog =  new AlertDialog.Builder(ProjectDetailActivity.this).setTitle("팀글을 선택해주세요")
                                .setSingleChoiceItems(  title.toArray(new String[title.size()])   , -1, new DialogInterface.OnClickListener() {
                                    @Override public void onClick(DialogInterface dialog, int which) {
                                        member_id = timgle_id.get(which);
                                        Log.d("지원 얼랏", String.valueOf(which));
                                    }
                                }) .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // ok 버튼 클릭시
                                        applyTimgle(member_id, project_id);
                                    }
                                }).setNegativeButton("취소",null)  .show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProjectDetailChoiceTimgleResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "그냥 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void applyTimgle(int m_id, int p_id){
        Log.d("팀글", "시작어플" + Integer.toString(m_id)+Integer.toString(p_id));

        RequestBody mid = RequestBody.create(MultipartBody.FORM, Integer.toString(m_id));
        RequestBody pid = RequestBody.create(MultipartBody.FORM, Integer.toString(p_id));


        Call<ProjectDetailApplyResponse> call3 = service.projectDetailApplyResponse(USER_TOKEN, "application/json", new ProjectDetailApplyData(Integer.toString(m_id),Integer.toString(p_id) ));


        call3.enqueue(new Callback<ProjectDetailApplyResponse>() {
            @Override
            public void onResponse(Call<ProjectDetailApplyResponse> call, Response<ProjectDetailApplyResponse> response) {
                if (response.isSuccessful()) {
                    ProjectDetailApplyResponse result3 = response.body();
                    if (result3.getStatus() == 201) {
                        Toast.makeText(getApplicationContext(), "지원이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        //TODO: INTENT를 다시 보내거나 OnCreate를 다시 부르거나
                        btn_no_applier.setVisibility(View.GONE);
                        btn_chat.setVisibility(View.GONE);

                    }
                }
                else{
                    Log.d("팀글3 ", response.toString());
                }
            }
            @Override
            public void onFailure(Call<ProjectDetailApplyResponse> call, Throwable t) {
                Toast.makeText(ProjectDetailActivity.this, "팀글3 ", Toast.LENGTH_SHORT).show();
                Log.e("로그인 에러 발생", t.getMessage());
                t.printStackTrace(); // 에러 발생시 에러 발생 원인 단계별로 출력해줌
            }
        });
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
