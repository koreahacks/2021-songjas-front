package com.example.timmo_songjas.chatting.chat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timmo_songjas.R;
import com.example.timmo_songjas.chatting.model.ChatModel;
import com.example.timmo_songjas.chatting.model.UserModel;
import com.example.timmo_songjas.network.RetrofitClient;
import com.example.timmo_songjas.network.RetrofitService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;


import static com.example.timmo_songjas.feature.utils.CommonValues.SEVER_USERID_LOGGED_IN;

public class GroupMessageActivity extends AppCompatActivity {

    Map<String, UserModel> users = new HashMap<>();

    String destinationRoom_key;
    private String userid;
    EditText editText;
    ImageButton send_btn;
    public ImageView iv_timmgle_upload;


    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;
    List<ChatModel.Comment> comments = new ArrayList<>();
    private RecyclerView recyclerView;

    private boolean isRegisterTimggle = false;
    private int projectid;
    RetrofitService service;
    private int select_timgle_id;
    private String team_userid;

    List<Integer> projectid_list = new ArrayList<>(); //팀모
    List<String>title_list = new ArrayList<>();

    private SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("a hh:mm"); //오전 오후

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_message);

        userid = SEVER_USERID_LOGGED_IN;
        destinationRoom_key = getIntent().getStringExtra("destinationRoom");
        //TODO:이력서 등록하는 프로젝트에드3에서 인텐트로 프로젝트 id 같이 보내야함!!
        projectid = -1;//getIntent().getIntExtra("projectId",-1);
        team_userid = getIntent().getStringExtra("userid"); //팀장 userid

        editText = (EditText)findViewById(R.id.et_groupmessage);
        iv_timmgle_upload = (ImageView)findViewById(R.id.iv_extra_group_message);
        send_btn = (ImageButton)findViewById(R.id.ib_send_group_message);
        recyclerView = (RecyclerView)findViewById(R.id.rv_group_message);
        service = RetrofitClient.getClient().create(RetrofitService.class);


        send_btn.setOnClickListener(v -> {
            if(editText.getText().toString() != "")
                sendMessage();
        });

        iv_timmgle_upload.setOnClickListener(v -> {

        });

        getUerDataFromFirebas();
    }

    void getUerDataFromFirebas(){
        FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //받아온 데이터를 바로 userModelMap에 넣기
                //user 정보 하나씩 받아오기
                //그냥 전체 유저 다 받아오나봐
                for(DataSnapshot item : snapshot.getChildren() ){
                    users.put( item.getValue(UserModel.class).userid   , item.getValue(UserModel.class)  );
                }

                //유저를 다 불러오고 나서 채팅을 불러와야 오류가 안나지
                recyclerView.setAdapter(new GroupMessageRecyclerViewAdapter());
                recyclerView.setLayoutManager(new LinearLayoutManager(GroupMessageActivity.this));
                send_btn.setActivated(true);
                iv_timmgle_upload.setActivated(true);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void sendMessage(){
        send_btn.setActivated(false);
        iv_timmgle_upload.setActivated(false);
        //코멘트 만들어서
        ChatModel.Comment comment = new ChatModel.Comment();

        //코멘트에 uid 넘기고
        comment.userid = userid;
        comment.message = editText.getText().toString();
        comment.timestamp = ServerValue.TIMESTAMP;
        comment.isBtn = false;
        comment.projectId = projectid;

        //그 다음 바로 DB 만들어주기
        //해당 채팅방 있는지 없는지 검사하는 코드 넣어주기
        FirebaseDatabase.getInstance().getReference().child("chatrooms")
                .child(destinationRoom_key) //채팅방이름을 넣어줘야하는데 그 이름은 chatRoomUid가 가지고 있지지
                .child("comments").push().setValue(comment)
                .addOnCompleteListener(new OnCompleteListener<Void>() { //여기부턴 콜백부분
                    //데이터 입력이 된 다음 콜백 오면 그때 edText 초기화해주는
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        editText.setText("");
                        send_btn.setActivated(true);
                        iv_timmgle_upload.setActivated(true);
                    }
                });
    }



    class GroupMessageRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        public GroupMessageRecyclerViewAdapter() {
            getMessageList();
        }

        //메세지 리스트를 가져와서 넣는건가 메소드
        void getMessageList(){
            //특정 방의 comments 다 읽어 들어오기 위해 코멘트 가져와서 리스너로 읽어들이고
            FirebaseDatabase.getInstance().getReference().child("chatrooms").child(destinationRoom_key).child("comments")
                    .addValueEventListener( new ValueEventListener(){
                        //comment가 몇개인지알아야 리스트를 만들 수 있느이 아래의 getItemCount
                        @Override //이쪽으로 넘어오나봐 데이터가가
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            comments.clear();

                            for(DataSnapshot item : snapshot.getChildren()){
                                comments.add(item.getValue(ChatModel.Comment.class));
                            }

                            //메세지 갱신
                            notifyDataSetChanged();
                            //갱신 뿐 아니라 리사리클러 뷰를 이동
                            recyclerView.scrollToPosition(comments.size() - 1);//맨 마지막 포지션
                            //scrollToPosition : 어디 포지션으로 이동
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
        }


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,parent,false);
            //새 클래스 만들어서 리턴, 이너클래스 , 리사이클러뷰 안에다 만들어준다.
            return new GroupMessageViewHolder(view);
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            GroupMessageViewHolder messageViewHolder = (GroupMessageViewHolder) holder;
            //공통 적용
            messageViewHolder.tv_main_message.setText(comments.get(position).message);
            long unixTime = (long) comments.get(position).timestamp;
            Date date = new Date(unixTime);
            simpleDateFormat2.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
            String time = simpleDateFormat2.format(date); // 시 분 만
            //조건문에서 시간 없애줌
            messageViewHolder.tv_dest_timestamp.setText(time);
            messageViewHolder.tv_my_timestamp.setText(time);
            //여기서는 서브 메세지 일단 다 gone

            //내가 보낸 메세지
            if (comments.get(position).userid.equals(userid)) { //앞uid는 comment의 uid, 뒤에 uid는 위에 선언해서 firebase에 연결된 내uid
                messageViewHolder.tv_main_message.setText(comments.get(position).message);
                messageViewHolder.tv_main_message.setTextColor(R.color.white);
                messageViewHolder.ll_center.setBackgroundResource(R.drawable.bg_message_orange);
                messageViewHolder.ll_main.setGravity(Gravity.RIGHT);
                messageViewHolder.tv_my_timestamp.setVisibility(View.VISIBLE);
                messageViewHolder.ll_left.setGravity(Gravity.RIGHT);

                //상대방에 대한 부분, 띄울필요없으니 감춤
                messageViewHolder.tv_name.setVisibility(View.GONE);
                messageViewHolder.iv_profile.setVisibility(View.GONE);
                messageViewHolder.tv_dest_timestamp.setVisibility(View.GONE);


            } else { //상대방이 보낸 메세지

                messageViewHolder.tv_name.setText(users.get(comments.get(position).userid).userName);
                messageViewHolder.tv_name.setVisibility(View.VISIBLE);
                messageViewHolder.iv_profile.setVisibility(View.VISIBLE);
                messageViewHolder.tv_main_message.setTextColor(R.color.four_black);
                messageViewHolder.ll_center.setBackgroundResource(R.drawable.bg_message);
                messageViewHolder.ll_left.setGravity(Gravity.LEFT);
                messageViewHolder.ll_main.setGravity(Gravity.LEFT);
                messageViewHolder.tv_my_timestamp.setVisibility(View.GONE);
                messageViewHolder.tv_dest_timestamp.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public int getItemCount() {
            return comments.size();
        }

        private class GroupMessageViewHolder extends RecyclerView.ViewHolder {

            public TextView tv_name;
            public ImageView iv_profile;
            public LinearLayout ll_center;//기존 상대방->초대파트 구현 위한 ,배경색
            public LinearLayout ll_main;
            public LinearLayout ll_left ;

            public TextView tv_dest_timestamp;
            public TextView tv_my_timestamp;
            public TextView tv_main_message; // 글씨색
            public TextView tv_sub_message;
            public GroupMessageViewHolder(View view) {
                super(view);
                tv_name = (TextView) view.findViewById(R.id.tv_name_message);
                iv_profile = (ImageView) view.findViewById(R.id.iv_profile_message);
                ll_center = (LinearLayout) view.findViewById(R.id.ll_center_messageItem);
                ll_main = (LinearLayout) view.findViewById(R.id.ll_main_message);
                ll_left = (LinearLayout) view.findViewById(R.id.ll_left_message);
                tv_dest_timestamp = (TextView) view.findViewById(R.id.tv_dest_timestamp_messageItem);
                tv_my_timestamp = (TextView) view.findViewById(R.id.tv_mytimestamp_messageItem);
                tv_main_message = (TextView) view.findViewById(R.id.tv_main_message);
                tv_sub_message = (TextView) view.findViewById(R.id.tv_sub_message);

            }
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //databaseReference.removeEventListener(valueEventListener);

        //메세지가 없을 때 뒤로 가기 클릭 시 꺼지는 에러 위한
        //getMessage파트에서 valueEventListener 이 값이 널이면 removeE~ 이게 에러남
        if(valueEventListener != null){
            databaseReference.removeEventListener(valueEventListener);
        }

        finish();
        //finish 밑에 애니매이션을 넣어야 작동됨
        overridePendingTransition(R.anim.fromleft, R.anim.toright);
    }

}