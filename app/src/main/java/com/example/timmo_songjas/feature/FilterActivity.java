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

    int morningCount;
    int nightCount;
    int dawnCount;
    int planCount;
    int focusCount;
    int leaderCount;
    int followCount;
    int challCount;
    int realCount;

    //    /projects?title=&type=&field=&position=&largeAddress=&smallAddress=&limitUniv=&morning=&night=&dawn=&plan=&cramming=&leader=&follower=&challenge=&realistic=

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        //툴바
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_filter);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); //툴바 타이틀 삭제
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //툴바 뒤로가기 버튼 생성
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left_24_dp);


        //스피너구현(유형)
        spType = (Spinner)findViewById(R.id.sp_type_filter);
        ArrayAdapter<String> typeAapter = new ArrayAdapter<String>(this, R.layout.item_spinner, type){

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
        spType.setSelection(typeAapter.getCount());

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
        ArrayAdapter<String> feildAapter = new ArrayAdapter<String>(this, R.layout.item_spinner, field){
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
        spField.setSelection(feildAapter.getCount());

        spField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fieldString = field[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //스피너 구현(희망팀원 포지션)
        String[] memberPosition = {"디자인", "개발", "기획","홍보/마케팅", "희망 포지션을 선택해주세요."};
        spPosition = (Spinner)findViewById(R.id.sp_position_filter);
        ArrayAdapter<String> textAapter = new ArrayAdapter<String>(this, R.layout.item_spinner, memberPosition){

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
        EditText etState = (EditText) findViewById(R.id.et_state_filter);
        String state = etState.getText().toString();

        //시/군/구
        EditText etCounty = (EditText) findViewById(R.id.et_county_filter);
        String country = etCounty.getText().toString();

        //개인성향 이벤트 처리 TODO: and or 고려해줘야 하는 거 추가!!!!!
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

        boolean opt_morning = (Integer.parseInt(String.valueOf(morningCount % 2))!= 0 );
        boolean opt_night = (Integer.parseInt(String.valueOf(nightCount % 2))!= 0 );
        boolean opt_dawn = (Integer.parseInt(String.valueOf(dawnCount % 2))!= 0 );
        boolean opt_plan = (Integer.parseInt(String.valueOf(planCount % 2))!= 0 );
        boolean opt_cramming = (Integer.parseInt(String.valueOf(focusCount % 2))!= 0 );
        boolean opt_leader = (Integer.parseInt(String.valueOf(leaderCount % 2))!= 0 );
        boolean opt_follower = (Integer.parseInt(String.valueOf(followCount % 2))!= 0 );
        boolean opt_challenge = (Integer.parseInt(String.valueOf(challCount % 2))!= 0 );
        boolean opt_realistic = (Integer.parseInt(String.valueOf(realCount % 2))!= 0 );

        //대학
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.rg_univ_filter);
        int id = radioGroup.getCheckedRadioButtonId();
        RadioButton rbUniv = (RadioButton) findViewById(id);
        String univ = rbUniv.getText().toString();

        boolean opt_limitUniv;
        if(univ == "전체"){ // true
            opt_limitUniv = true; }
        else { //자대 false
            opt_limitUniv = false; }

        //완료 버튼
        Button finishBtn = (Button)findViewById(R.id.finish_filter);
        finishBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //option 값으로 네트워킹 searchfilter(data);

                //TODO: 성향은 false, true로 모든 정보 다 보내야 함? True인 것만?
                Intent intent = getIntent();


                intent.putExtra("type", type);
                intent.putExtra("field", field);
                intent.putExtra("position", positionString);
                intent.putExtra("largeAddress", state);
                intent.putExtra("smallAddress", country);
                //아래는 true인 것만 보내기
                if(opt_morning)
                    intent.putExtra("morning", opt_morning);
                if(opt_night)
                    intent.putExtra("night", opt_night);
                if(opt_dawn)
                    intent.putExtra("dawn", opt_dawn);
                if(opt_plan)
                    intent.putExtra("plan", opt_plan);
                if(opt_cramming)
                    intent.putExtra("cramming", opt_cramming);
                if(opt_leader)
                    intent.putExtra("leader", opt_leader);
                if(opt_follower)
                    intent.putExtra("follower", opt_follower);
                if(opt_challenge)
                    intent.putExtra("challenge", opt_challenge);
                if(opt_realistic)
                    intent.putExtra("reality", opt_realistic);

                intent.putExtra("limitUniv", opt_limitUniv);
                setResult(RESULT_OK, intent);

                finish();
            }
        });
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