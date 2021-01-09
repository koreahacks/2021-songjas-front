package com.example.timmo_songjas.feature.member;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.timmo_songjas.R;

import java.util.ArrayList;
import java.util.List;

public class MemberAddActivity extends AppCompatActivity {

    Spinner spType;
    String typeString;
    Spinner spField;
    String fieldString;
    String[] type = {"공모전", "해커톤", "교내 팀플", "졸업작품", "경진대회", "유형을 선택하세요."};
    String[] field = {"경영", "경제", "디자인", "IT", "무역", "홍보/마케팅", "분야를 선택하세요"};
    Spinner spPosition;
    String positioinStr;
    ArrayList<String> date = new ArrayList<>();
    ArrayList<String> content = new ArrayList<>();
    int btnCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_add);

        //툴바
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_timgleadd);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); //툴바 타이틀 삭제
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //툴바 뒤로가기 버튼 생성
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left_24_dp);

        //제목
        EditText etTitle = (EditText)findViewById(R.id.et_title_resume);

        //스피너구현(유형)
        spType = (Spinner)findViewById(R.id.sp_type_resume);
        ArrayAdapter<String> typeAapter = new ArrayAdapter<String>(this, R.layout.item_spinner, type){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(R.id.text1)).setText("");
                    ((TextView)v.findViewById(R.id.text1)).setHint("유형을 선택하세요"); //"Hint to be displayed"
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
        spField = (Spinner)findViewById(R.id.sp_field_resume);
        ArrayAdapter<String> feildAapter = new ArrayAdapter<String>(this, R.layout.item_spinner, field){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(R.id.text1)).setText("");
                    ((TextView)v.findViewById(R.id.text1)).setHint("분야를 선택하세요"); //"Hint to be displayed"
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

        //스피너 구현(희망포지션)
        String[] memberPosition = {"디자인", "개발", "기획","홍보/마케팅", "희망 포지션을 선택해주세요."};
        spPosition = (Spinner)findViewById(R.id.sp_position_resume);
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
                positioinStr=memberPosition[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //소개
        EditText etIntro = (EditText)findViewById(R.id.et_introduce_resume);

        //활동 기간
        EditText etTerm = (EditText)findViewById(R.id.et_term_resume);

        //활동 내용
        EditText etContent = (EditText)findViewById(R.id.et_content_resume);

        //활동 추가 버튼
        btnCount = 0;
        LinearLayout llAddArea = (LinearLayout)findViewById(R.id.ll_add_resume);
        Button btnActAdd = (Button)findViewById(R.id.btn_actadd_resume);
        btnActAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCount++;
                int tvTopMargin = Math.round(20 * getResources().getDisplayMetrics().density);
                int etTopMargin = Math.round(8 * getResources().getDisplayMetrics().density);

                //활동 기간 글씨
                TextView tvTerm = new TextView(getApplicationContext());
                tvTerm.setText("활동기간");
                tvTerm.setGravity(Gravity.LEFT);
                tvTerm.setTextColor(Color.parseColor("#c5ccd6"));
                tvTerm.setTextSize(14);
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.FILL_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                tvTerm.setLayoutParams(params1);
                params1.topMargin=tvTopMargin;
                llAddArea.addView(tvTerm, params1);

                //활동 기간 입력
                EditText etTerm = new EditText(getApplicationContext());
                etTerm.setId(btnCount*2);
                etTerm.setHint("YYYY-MM-DD");
                etTerm.setHintTextColor(Color.parseColor("#e1e3e8"));
                etTerm.setGravity(Gravity.LEFT);
                etTerm.setTextColor(Color.parseColor("#666666"));
                etTerm.setTextSize(16);
                etTerm.setBackground(getResources().getDrawable(R.drawable.underline));
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.FILL_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                params2.topMargin=etTopMargin;
                etTerm.setLayoutParams(params2);
                llAddArea.addView(etTerm, params2);

                //활동 내용 글씨
                TextView tvActContent = new TextView(getApplicationContext());
                tvActContent.setText("활동내용");
                tvActContent.setGravity(Gravity.LEFT);
                tvActContent.setTextColor(Color.parseColor("#c5ccd6"));
                tvActContent.setTextSize(14);
                LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.FILL_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                tvActContent.setLayoutParams(params1);
                params3.topMargin=tvTopMargin;
                llAddArea.addView(tvActContent, params3);

                //활동 내용 입력
                EditText etContent = new EditText(getApplicationContext());
                etContent.setId(btnCount*2 +1);
                etContent.setHint("활동 내용을 입력해주세요.");
                etContent.setHintTextColor(Color.parseColor("#e1e3e8"));
                etContent.setGravity(Gravity.LEFT);
                etContent.setTextColor(Color.parseColor("#666666"));
                etContent.setTextSize(16);
                etContent.setBackground(getResources().getDrawable(R.drawable.underline));
                LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.FILL_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                params4.topMargin=etTopMargin;
                etTerm.setLayoutParams(params4);
                llAddArea.addView(etContent, params4);

            }
        });

        //포트폴리오 작성
        EditText etPort = (EditText)findViewById(R.id.et_port_resume);

        //라디오 버튼
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.rg_univ_filter);

        //완료 버튼 이벤트
        Button btnFinish = (Button)findViewById(R.id.btn_finish_resume);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //라디오 버튼 입력 받기
                int id = radioGroup.getCheckedRadioButtonId();
                RadioButton rbOpen = (RadioButton) findViewById(id);

                //입력된 데이터 정리
                String title = etTitle.getText().toString();
                //typeString(유형)
                //fieldString(분야)
                //positioinStr(희망포지션)
                String introduction = etIntro.getText().toString();
                //date(ArrayList, String type)하단에 구현
                //content(ArrayList, String type)하단에 구현
                date.add(etTerm.getText().toString());
                content.add(etContent.getText().toString());
                for(int i = 0; i <btnCount; i++){
                    int termID = (i+1)*2;
                    int contentID = (i+1)*2+1;
                    EditText tmpTerm = (EditText)findViewById(termID);
                    date.add(tmpTerm.getText().toString());
                    EditText tmpContent = (EditText)findViewById(contentID);
                    content.add(tmpContent.getText().toString());
                }

                String port = etPort.getText().toString();
                //String openRadio = rbOpen.getText().toString();//true 값으로 전달
                boolean open = (rbOpen.getText().toString().equals("공개")? true: false);
                try {
                    finish();
                }catch (Exception e){
                    //Toast.makeText(getApplicationContext(), "모든 항목을 정확하게 채워주세요.", Toast.LENGTH_LONG).show();
                }

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