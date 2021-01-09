package com.example.timmo_songjas.feature;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.timmo_songjas.R;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class FilterActivity extends AppCompatActivity {

    Spinner spType;
    String typeString;
    Spinner spField;
    String fieldString;
    //TODO: 확정 후 수정 필요
    String[] type = {"공모전", "해커톤", "교내 팀플", "졸업작품", "경진대회", "유형을 입력하세요."};
    String[] field = {"경영", "경제", "디자인", "IT", "무역", "홍보/마케팅", "유형을 입력하세요."};
    Spinner spPosition;
    String positionString;
    ArrayAdapter<String> textAapter;
    String[] memberPosition = {"디자인", "개발", "기획","홍보/마케팅", "희망 포지션을 선택해주세요."};

    int morningCount;
    int nightCount;
    int dawnCount;
    int planCount;
    int focusCount;
    int leaderCount;
    int followCount;
    int challCount;
    int realCount;

    int id;

    RadioButton rbUniv;
    String univ;

    ArrayAdapter<String> typeAapter;
    ArrayAdapter<String> feildAapter;

    Map<String,String> s_q;
    Map<String ,Boolean>b_q;

    EditText etState;
    EditText etCounty;
    TextView tvMorning ,tvNight,tvDawn,tvPlan,tvFocus,tvLeader,tvFollow,tvChall,tvReal;
    boolean opt_morning,opt_night,opt_dawn,opt_plan,opt_cramming,opt_leader,opt_follower,opt_challenge,opt_realistic;

    boolean opt_limitUniv;
    RadioGroup radioGroup;

    Button finishBtn;
    String state,country;

    private  Intent intent;
    String title; // 서버에 넘길 타이틀
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        intent  = getIntent();
        title = intent.getStringExtra("search_title");

        //툴바
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_filter);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); //툴바 타이틀 삭제
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //툴바 뒤로가기 버튼 생성
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left_24_dp);


        //스피너구현(유형)
        spType = (Spinner)findViewById(R.id.sp_type_filter);



        typeAapter = new ArrayAdapter<String>(this, R.layout.item_spinner, type){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(R.id.text1)).setText("");
                    ((TextView)v.findViewById(R.id.text1)).setHint("유형을 입력하세요"); //"Hint to be displayed"
                }
                return v;
            }

            @Override
            public int getCount() {
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }
        };
        typeAapter.setDropDownViewResource(R.layout.item_spinner_dropdown);


        spType.setAdapter(typeAapter);
        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeString = type[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        //스피너구현(분야)
        spField = (Spinner)findViewById(R.id.sp_field_filter);
        feildAapter = new ArrayAdapter<String>(this, R.layout.item_spinner, field){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(R.id.text1)).setText("");
                    ((TextView)v.findViewById(R.id.text1)).setHint("유형을 입력하세요"); //"Hint to be displayed"
                }
                return v;
            }

            @Override
            public int getCount() {
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }
        };
        feildAapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spField.setAdapter(feildAapter);

        spField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fieldString = field[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //스피너 구현(희망팀원 포지션);
        spPosition = (Spinner)findViewById(R.id.sp_position_filter);
        textAapter = new ArrayAdapter<String>(this, R.layout.item_spinner, memberPosition){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(R.id.text1)).setText("");
                    ((TextView)v.findViewById(R.id.text1)).setHint("희망 포지션을 선택해주세요."); //"Hint to be displayed"
                }
                return v;
            }

            @Override
            public int getCount() {
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }
        };
        textAapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spPosition.setAdapter(textAapter);
        spPosition.setSelection(textAapter.getCount());

        spPosition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positionString = memberPosition[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //시/도
        etState = (EditText) findViewById(R.id.et_state_filter);

        //시/군/구
        etCounty = (EditText) findViewById(R.id.et_county_filter);

        //개인성향 이벤트 처리 TODO: and or 고려해줘야 하는 거 추가!!!!!
        //아침형
        morningCount = 0;
        tvMorning = (TextView) findViewById(R.id.tv_morning_filter);
        tvMorning.setOnClickListener(v -> {
            morningCount++;
            if(morningCount %2 == 1){
                tvMorning.setTextColor(Color.WHITE);
                tvMorning.setBackground(getResources().getDrawable(R.drawable.btn_orange_selected));

            }
            else{
                tvMorning.setTextColor(Color.parseColor("#c5ccd6"));
                tvMorning.setBackground(getResources().getDrawable(R.drawable.text_grey_rect));
            }
        });
        //저녁
        nightCount = 0;
        tvNight = (TextView) findViewById(R.id.tv_night_filter);
        tvNight.setOnClickListener(v -> {
            nightCount++;
            if(nightCount %2 == 1){
                tvNight.setTextColor(Color.WHITE);
                tvNight.setBackground(getResources().getDrawable(R.drawable.btn_orange_selected));

            }
            else{
                tvNight.setTextColor(Color.parseColor("#c5ccd6"));
                tvNight.setBackground(getResources().getDrawable(R.drawable.text_grey_rect));
            }
        });
        //새벽
        dawnCount = 0;
        tvDawn = (TextView) findViewById(R.id.tv_dawn_filter);
        tvDawn.setOnClickListener(v -> {
            dawnCount++;
            if(dawnCount %2 == 1){
                tvDawn.setTextColor(Color.WHITE);
                tvDawn.setBackground(getResources().getDrawable(R.drawable.btn_orange_selected));

            }
            else{
                tvDawn.setTextColor(Color.parseColor("#c5ccd6"));
                tvDawn.setBackground(getResources().getDrawable(R.drawable.text_grey_rect));
            }
        });


        //계획
        planCount = 0;
        tvPlan = (TextView) findViewById(R.id.tv_plan_filter);
        tvPlan.setOnClickListener(v -> {
            planCount++;
            if(planCount %2 == 1){
                tvPlan.setTextColor(Color.WHITE);
                tvPlan.setBackground(getResources().getDrawable(R.drawable.btn_orange_selected));

            }
            else{
                tvPlan.setTextColor(Color.parseColor("#c5ccd6"));
                tvPlan.setBackground(getResources().getDrawable(R.drawable.text_grey_rect));
            }
        });
        //몰입
        focusCount = 0;
        tvFocus = (TextView) findViewById(R.id.tv_focus_filter);
        tvFocus.setOnClickListener(v -> {
            focusCount++;
            if(focusCount %2 == 1){
                tvFocus.setTextColor(Color.WHITE);
                tvFocus.setBackground(getResources().getDrawable(R.drawable.btn_orange_selected));

            }
            else{
                tvFocus.setTextColor(Color.parseColor("#c5ccd6"));
                tvFocus.setBackground(getResources().getDrawable(R.drawable.text_grey_rect));
            }
        });


        //리더
        leaderCount= 0;
        tvLeader = (TextView) findViewById(R.id.tv_leader_filter);
        tvLeader.setOnClickListener(v -> {
            leaderCount++;
            if(leaderCount %2 == 1){
                tvLeader.setTextColor(Color.WHITE);
                tvLeader.setBackground(getResources().getDrawable(R.drawable.btn_orange_selected));

            }
            else{
                tvLeader.setTextColor(Color.parseColor("#c5ccd6"));
                tvLeader.setBackground(getResources().getDrawable(R.drawable.text_grey_rect));
            }
        });
        //팔로워
        followCount= 0;
        tvFollow = (TextView) findViewById(R.id.tv_follow_filter);
        tvFollow.setOnClickListener(v -> {
            followCount++;
            if(followCount %2 == 1){
                tvFollow.setTextColor(Color.WHITE);
                tvFollow.setBackground(getResources().getDrawable(R.drawable.btn_orange_selected));

            }
            else{
                tvFollow.setTextColor(Color.parseColor("#c5ccd6"));
                tvFollow.setBackground(getResources().getDrawable(R.drawable.text_grey_rect));
            }
        });


        //도전
        challCount= 0;
        tvChall = (TextView) findViewById(R.id.tv_challenge_filter);
        tvChall.setOnClickListener(v -> {
            challCount++;
            if(challCount %2 == 1){
                tvChall.setTextColor(Color.WHITE);
                tvChall.setBackground(getResources().getDrawable(R.drawable.btn_orange_selected));

            }
            else{
                tvChall.setTextColor(Color.parseColor("#c5ccd6"));
                tvChall.setBackground(getResources().getDrawable(R.drawable.text_grey_rect));
            }
        });
        //현실
        realCount= 0;
        tvReal = (TextView) findViewById(R.id.tv_real_filter);
        tvReal.setOnClickListener(v -> {
            realCount++;
            if(realCount %2 == 1){
                tvReal.setTextColor(Color.WHITE);
                tvReal.setBackground(getResources().getDrawable(R.drawable.btn_orange_selected));

            }
            else{
                tvReal.setTextColor(Color.parseColor("#c5ccd6"));
                tvReal.setBackground(getResources().getDrawable(R.drawable.text_grey_rect));
            }
        });

        //대학
        radioGroup = (RadioGroup)findViewById(R.id.rg_univ_filter);
        id = radioGroup.getCheckedRadioButtonId();
        rbUniv = (RadioButton) findViewById(id);


        //완료 버튼
        finishBtn = (Button)findViewById(R.id.finish_filter);
        finishBtn.setOnClickListener(v -> {
            filter();
            //option 값으로 네트워킹 searchfilter(data);
            s_q = new HashMap<>();
            b_q = new HashMap<>();
 //    /projects?title=&type=&field=&position=&largeAddress=&smallAddress=&limitUniv=&morning=&night=&dawn=&plan=&cramming=&leader=&follower=&challenge=&realistic=

            s_q.put("title", title );
            s_q.put( "type", typeString);
            s_q.put("field", fieldString );
            s_q.put("position", positionString);
            s_q.put( "largeAddress", state);
            s_q.put( "smallAddress", country);

            b_q.put("limitUniv", opt_limitUniv);
            if(opt_morning)
                b_q.put("morning", opt_morning);
            else
                b_q.put("morning", null);

            if(opt_night)
                b_q.put("night", opt_night);
            else
                b_q.put("morning", null);

            if(opt_dawn)
                b_q.put("dawn", opt_dawn);
            else
                b_q.put("dawn", null);

            if(opt_plan)
                b_q.put("plan", opt_plan);
            else
                b_q.put("plan", null);

            if(opt_cramming)
                b_q.put("cramming", opt_cramming);
            else
                b_q.put("cramming", null);

            if(opt_leader)
                b_q.put("leader", opt_leader);
            else
                b_q.put("leader", opt_leader);

            if(opt_follower)
                b_q.put("follower", opt_follower);
            else
                b_q.put("follower", null);

            if(opt_challenge)
                b_q.put("challenge", opt_challenge);
            else
                b_q.put("challenge", null);

            if(opt_realistic)
                b_q.put("reality", opt_realistic);
            else
                b_q.put("reality", null);

            intent.putExtra("s_q", (Serializable) s_q);
            intent.putExtra("b_q", (Serializable) b_q);

            //TODO: 성향은 false, true로 모든 정보 다 보내야 함? True인 것만?

            setResult(RESULT_OK, intent);

            finish();
        });
    }

    void filter(){
        spType.setSelection(typeAapter.getCount());

        spField.setSelection(feildAapter.getCount());


        state = etState.getText().toString();
        country = etCounty.getText().toString();
        univ = rbUniv.getText().toString();


        if(univ.equals("전체")){ // true
            opt_limitUniv = true; }
        else { //자대 false
            opt_limitUniv = false; }

        opt_morning = (Integer.parseInt(String.valueOf(morningCount % 2))!= 0 );
        opt_night = (Integer.parseInt(String.valueOf(nightCount % 2))!= 0 );
        opt_dawn = (Integer.parseInt(String.valueOf(dawnCount % 2))!= 0 );
        opt_plan = (Integer.parseInt(String.valueOf(planCount % 2))!= 0 );
        opt_cramming = (Integer.parseInt(String.valueOf(focusCount % 2))!= 0 );
        opt_leader = (Integer.parseInt(String.valueOf(leaderCount % 2))!= 0 );
        opt_follower = (Integer.parseInt(String.valueOf(followCount % 2))!= 0 );
        opt_challenge = (Integer.parseInt(String.valueOf(challCount % 2))!= 0 );
        opt_realistic = (Integer.parseInt(String.valueOf(realCount % 2))!= 0 );


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