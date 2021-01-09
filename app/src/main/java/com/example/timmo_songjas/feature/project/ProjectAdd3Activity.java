package com.example.timmo_songjas.feature.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.Toast;

import com.example.timmo_songjas.MainActivity;
import com.example.timmo_songjas.R;
import com.example.timmo_songjas.chatting.model.ChatModel;
import com.example.timmo_songjas.data.ProjectAddData;
import com.example.timmo_songjas.data.ProjectAddResponse;
import com.example.timmo_songjas.data.ProjectMembers;
import com.example.timmo_songjas.data.ProjectPositions;
import com.example.timmo_songjas.network.RetrofitClient;
import com.example.timmo_songjas.network.RetrofitService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.timmo_songjas.feature.utils.CommonValues.SEVER_USERID_LOGGED_IN;
import static com.example.timmo_songjas.feature.utils.CommonValues.USER_TOKEN;

public class ProjectAdd3Activity extends AppCompatActivity {

    Spinner spPosition;
    ArrayList<String> positioinStr = new ArrayList<>();
    EditText etNumber;
    ArrayList<String> positionNumInt = new ArrayList<>();
    int btnCount;

    RetrofitService service1;

    String roomkey="";//서버에 보낼 룸키 데이터

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_add3);

        //안 보이게 만들기
        EditText etPositionNum2 = (EditText)findViewById(R.id.et_number2_project);
        etPositionNum2.setVisibility(View.GONE);
        EditText etPositionNum3 = (EditText)findViewById(R.id.et_number3_project);
        etPositionNum3.setVisibility(View.GONE);

        //툴바
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_timmoadd3);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); //툴바 타이틀 삭제
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //툴바 뒤로가기 버튼 생성
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left_24_dp);

        //add2에서 정보 받아오기
        Intent intent = getIntent();
        String image = intent.getStringExtra("image");
        String title = intent.getStringExtra("title");
        String type = intent.getStringExtra("type");
        String feild = intent.getStringExtra("feild");
        String startDate = intent.getStringExtra("startDate");
        String endDate = intent.getStringExtra("endDate");
        String content = intent.getStringExtra("content");
        int myId = Integer.parseInt(intent.getStringExtra("myId"));
        int timgle = Integer.parseInt(intent.getStringExtra("timgle"));
        String port = intent.getStringExtra("port");
        boolean morning = (Integer.parseInt(intent.getStringExtra("morning")) != 0 );
        boolean night = (Integer.parseInt(intent.getStringExtra("night")) != 0 );
        boolean dawn = (Integer.parseInt(intent.getStringExtra("dawn")) != 0 );
        boolean plan = (Integer.parseInt(intent.getStringExtra("plan")) != 0 );
        boolean focus = (Integer.parseInt(intent.getStringExtra("focus")) != 0 );
        boolean leader = (Integer.parseInt(intent.getStringExtra("leader")) != 0 );
        boolean follow = (Integer.parseInt(intent.getStringExtra("follow")) != 0 );
        boolean challenge = (Integer.parseInt(intent.getStringExtra("challenge")) != 0 );
        boolean reality = (Integer.parseInt(intent.getStringExtra("reality")) != 0 );
        List<Integer> memberList = (ArrayList<Integer>) intent.getSerializableExtra("membersList");

        //스피너 구현(희망팀원 포지션)
        String[] memberPosition = {"디자인", "개발", "기획","홍보/마케팅", "희망 포지션을 선택해주세요."};
        spPosition = (Spinner)findViewById(R.id.sp_position_project);
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
                positioinStr.add(memberPosition[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //인원 입력 받기
        etNumber = (EditText) findViewById(R.id.et_number_project);

        //버튼 누르면 추가하기
        btnCount = 0;
        Button btnPosition = (Button) findViewById(R.id.btn_positionadd_project);
        btnPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCount ++;
                String[] memberPosition = {"디자인", "개발", "기획","홍보/마케팅", "희망 포지션을 선택해주세요."};
                if(btnCount == 1){
                    //희망 포지션 글씨
                    TextView tvPosition2 = (TextView)findViewById(R.id.tv_position2_project);
                    tvPosition2.setVisibility(View.VISIBLE);

                    //스피너 구현(희망팀원 포지션)
                    Spinner spPosition2 = (Spinner)findViewById(R.id.sp_position2_project);
                    spPosition2.setVisibility(View.VISIBLE);
                    ArrayAdapter<String> textAapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.item_spinner, memberPosition){

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
                    spPosition2.setAdapter(textAapter);
                    spPosition2.setSelection(textAapter.getCount());

                    spPosition2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            positioinStr.add(memberPosition[position]);
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });

                    //인원 글씨
                    TextView tvPositionNum2 = (TextView)findViewById(R.id.tv_number2_project);
                    tvPositionNum2.setVisibility(View.VISIBLE);

                    //인원 입력 받기
                    etPositionNum2.setVisibility(View.VISIBLE);

                }
                else if(btnCount ==2){
                    //희망 포지션 글씨
                    TextView tvPosition3 = (TextView)findViewById(R.id.tv_position3_project);
                    tvPosition3.setVisibility(View.VISIBLE);

                    //스피너 구현(희망팀원 포지션)
                    Spinner spPosition3 = (Spinner)findViewById(R.id.sp_position3_project);
                    spPosition3.setVisibility(View.VISIBLE);
                    ArrayAdapter<String> textAapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.item_spinner, memberPosition){

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
                    spPosition3.setAdapter(textAapter);
                    spPosition3.setSelection(textAapter.getCount());

                    spPosition3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Log.d("에러난다고 ", memberPosition[position]);
                            positioinStr.add(memberPosition[position]);
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });

                    //인원 글씨
                    TextView tvPositionNum3 = (TextView)findViewById(R.id.tv_number3_project);
                    tvPositionNum3.setVisibility(View.VISIBLE);

                    //인원 입력 받기
                    etPositionNum3.setVisibility(View.VISIBLE);
                }
                else{
                    Toast.makeText(getApplicationContext(), "희망 포지션과 인원은 최대 3개까지 입력 가능합니다.", Toast.LENGTH_LONG).show();
                }
            }//버튼 클릭 이벤트 끝
        });//버튼 클릭 이벤트 끝

        //시/도
        EditText etState = (EditText) findViewById(R.id.et_state_filter);

        //시/군/구
        EditText etCounty = (EditText) findViewById(R.id.et_county_filter);

        //라디오 버튼
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.rg_univ_filter);

        //완료 버튼
        Button btnFinish = (Button)findViewById(R.id.btn_finish_project);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                roomkey="";
                //TODO:아래 작업 팀 빌딩 채팅방 만든 후 진행해야함
                ChatModel chatModels = new ChatModel(); //채팅방 정보
                //TODO:상대방 id 스트링으로 서버에서 받은 팀원 아이디 넣기
                chatModels.teamchatuserid = SEVER_USERID_LOGGED_IN;
                chatModels.users.put(SEVER_USERID_LOGGED_IN,true);
                chatModels.isTeamChat = true;//팀협업
                for( Integer i : memberList){
                    chatModels.users.put(String.valueOf(i),true);
                }
                FirebaseDatabase.getInstance().getReference()
                        .child("chatrooms").push().setValue(chatModels)
                        .addOnCompleteListener(new OnCompleteListener<Void>(){
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getApplicationContext(),"단체채팅방이 생성되었습니다." , Toast.LENGTH_SHORT).show();
                                //chatmodel 타임순으로 역순정렬 후
                                FirebaseDatabase.getInstance().getReference().child("chatrooms").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        List<ChatModel> chatModelList = new ArrayList<>();
                                        List<String> key_string =new ArrayList<>();
                                        for(DataSnapshot item : snapshot.getChildren()){
                                            chatModelList.add(item.getValue(ChatModel.class));
                                            key_string.add(item.getKey());
                                        }
                                        Collections.reverse(chatModelList);
                                        Collections.reverse(key_string);

                                        for(int i = 0 ; i < chatModelList.size() ; i++){
                                            if(chatModelList.get(i).teamchatuserid.equals(SEVER_USERID_LOGGED_IN)){
                                                roomkey = key_string.get(i);
                                                Toast.makeText(getApplicationContext(),"방키찾음" , Toast.LENGTH_SHORT).show();

                                                break;
                                            }
                                        }

                                        Log.d("방키 끝", "");
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                        intent.putExtra("userid",SEVER_USERID_LOGGED_IN); //팀장 userid
//                                        intent.putExtra("destinationRoom",roomkey); //룸키
                                        //TODO:일단 얼랏해놓고 의견 물어보자자                //인원 입력 받기
                                        switch (btnCount){
                                            case 0:
                                                positionNumInt.add(etNumber.getText().toString());
                                                break;
                                            case 1:
                                                positionNumInt.add(etNumber.getText().toString());
                                                positionNumInt.add(etPositionNum2.getText().toString());
                                                break;
                                            case 2:
                                                positionNumInt.add(etNumber.getText().toString());
                                                positionNumInt.add(etPositionNum2.getText().toString());
                                                positionNumInt.add(etPositionNum3.getText().toString());
                                                break;
                                        }

                                        //시/시도, 시/군/구 입력
                                        String state = etState.getText().toString();
                                        String county = etCounty.getText().toString();
                                        Log.d("시군구", "후");

                                        //라디오 버튼 입력 받기
                                        int id = radioGroup.getCheckedRadioButtonId();
                                        RadioButton rbUniv = (RadioButton) findViewById(id);
                                        String univ = rbUniv.getText().toString();
                                        boolean univType;
                                        if(univ.equals("자대") ) univType=true;
                                        else univType=false;


                                            Toast.makeText(getApplicationContext(), "3 트라이", Toast.LENGTH_LONG);


                                            List<ProjectMembers> pmList = new ArrayList<>();
                                            pmList.add(new ProjectMembers(myId, timgle));
                                            for(int i =0; i < memberList.size(); i++){
                                                Toast.makeText(getApplicationContext(), "4", Toast.LENGTH_LONG);

                                                pmList.add(new ProjectMembers(memberList.get(i), null));
                                            }
                                            List<ProjectPositions> ppList = new ArrayList<>();

//                                            for(String item : positioinStr){
//                                                ppList.add(new ProjectPositions(item))
//                                            }
                                        Log.d("에러난다고 사이즈 ", positioinStr.size() + "  " +positionNumInt.size() );


                                        for(int i= 0;i < positioinStr.size(); i++){
                                                ppList.add(new ProjectPositions(positioinStr.get(i), Integer.parseInt(positionNumInt.get(i))));
                                            }

                                            Log.d("데이터 확인 ", image + title+ type+feild+startDate+endDate+content+port);
                                            Log.d("데이터 확인 2: ", state+county);
                                            ProjectAddData data = new ProjectAddData(
                                                    image, roomkey, title, type, feild, startDate, endDate,
                                                    content, port, morning, night, dawn, plan, focus, leader,
                                                    follow, challenge, reality, state, county, univType, pmList, ppList);
                                            send(data);
                                            //TODO: 데이터 서버로 전송
                                            //TODO: 서버에 보낼 데이터 중 roomkey도 있음, 채팅방 ,룸키 값 받아왔으니 보내주기만하면됨

                                            Toast.makeText(getApplicationContext(), "3 인텐", Toast.LENGTH_LONG);


//                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
//                                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                            startActivity(intent);

                                            Toast.makeText(getApplicationContext(), "모든 항목을 정확하게 입력해주세요.", Toast.LENGTH_LONG);

                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        });

            }
        });
    }

    //데이터 전송
    private void send(ProjectAddData data){
        //retrofilt2 연결
        service1 = RetrofitClient.getClient().create(RetrofitService.class);
        Call<ProjectAddResponse> call = service1.projectAdd(USER_TOKEN,  data);

        call.enqueue(new Callback<ProjectAddResponse>() {
            @Override
            public void onResponse(Call<ProjectAddResponse> call, Response<ProjectAddResponse> response) {
                if (response.isSuccessful()) {
                    //메인 스레드에서 작업하는 부분 UI 작업 가능
                    ProjectAddResponse result = response.body();
                    if (result.getStatus() == 201) {
                        Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "연결 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProjectAddResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "그냥 실패", Toast.LENGTH_SHORT).show();
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