package com.example.timmo_songjas.feature.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timmo_songjas.R;
import com.example.timmo_songjas.feature.TeamTypeAdapter;
import com.example.timmo_songjas.feature.TeamTypeItem;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);

        //TODO: ProjectFind에서 선택한 모집글의 아이디를 전달받음
        Intent intent = getIntent();
        project_id = intent.getIntExtra("project_id", 1);

        init();
        //TODO : 프로젝트아이디로 네트워킹
        //loadDetail(project_id);

    }
    void init(){

        team_type_list.clear();
        member_list.clear();
        apply_list.clear();

        //툴바
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_timmo);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); //툴바 타이틀 삭제
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //툴바 뒤로가기 버튼 생성
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left_24_dp);


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


    //TODO : 3번 네트워킹 필요,


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