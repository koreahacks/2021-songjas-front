package com.example.timmo_songjas.chatting.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.example.timmo_songjas.R;
import com.example.timmo_songjas.chatting.model.ChatModel;
import com.example.timmo_songjas.chatting.model.UserModel;
import com.example.timmo_songjas.data.Data;
import com.example.timmo_songjas.data.TimmoListResponse;
import com.example.timmo_songjas.network.RetrofitClient;
import com.example.timmo_songjas.network.RetrofitService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.timmo_songjas.feature.utils.CommonValues.SEVER_USERID_LOGGED_IN;
import static com.example.timmo_songjas.feature.utils.CommonValues.USER_TOKEN;

@SuppressWarnings("NullableProblems")
public class MessageActivity extends AppCompatActivity {

    private String destinatonUserid;
    private String userid; //firebase의 uid대신 uid 하위의 userid 넣을 수 있을까?

    private String chatRoomUid;
    private ImageButton send_btn;
    private EditText editText;
    public ImageView iv_invite_user;

    private RecyclerView recyclerView;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM월 dd일");
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("a hh:mm"); //오전 오후

    private UserModel destinationUserModel;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;

    private boolean canSend;//버튼 메세지 파트
    private String get_room_key; //팀 협업 쪽 가기 위한

    RetrofitService service;

    int peopleCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        init();
        //클릭시얼랏창 떠서 팀모 선택하게
        //그냥 해당 글 작성자만 이거 보낸다는 가정하에 만들자

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editText.getText().toString().equals("")) sendMessage();
            }
        });


        iv_invite_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:서버쪽에서 팀모 작성한 목록 불러와서 여기다 뿌려
                if(!userid.equals(destinatonUserid)) //방장만 보낼 수 있음
                    sendBtnMessage();
            }
        });


        checkChatRoom();
    }
    void init(){
        //intent로 넘어온 상대 uid
        destinatonUserid = getIntent().getStringExtra("destinationUserid"); // 채팅을 당하는 아이디
        userid = SEVER_USERID_LOGGED_IN;//서버에 로그인된 내 userid ,string 임
        editText = (EditText) findViewById(R.id.et_message);
        recyclerView = (RecyclerView) findViewById(R.id.rv_message);
        send_btn = (ImageButton)findViewById(R.id.ib_send_message); // 일반 텍스트 보내는 메세지지 버튼
        iv_invite_user = (ImageView)findViewById(R.id.iv_invite_message); // 팀협업 메세지 보내는 이미지
        service = RetrofitClient.getClient().create(RetrofitService.class);
        //     Toast.makeText(getApplicationContext(),"" , Toast.LENGTH_SHORT).show();


    }

    //일반 메세지
    void sendMessage(){
        //여기는 그룹메세지에서 방만드는 파트 보고 나서 다시 수정하자
        //이미 채팅창이 만들어져 있으면 글자만 전송해주고
        //없다면 채팅방에대한 DB구조까지 만들어주는 부분

        ChatModel chatModel = new ChatModel();
        chatModel.users.put(userid,true);
        chatModel.users.put(destinatonUserid,true);
        chatModel.isTeamChat = false; // 팀빌딩

        //null이면 채팅방 생성하고
        if(chatRoomUid == null){
            send_btn.setEnabled(false);
            //이제 바로 DB에 넣어주기
            //push는 일종의 pk, push 안넣으면 채팅방의 이름이 없음 ,넣어줘야 채팅방에 이름이 알아서 넣어주나?...아무튼
            FirebaseDatabase.getInstance().getReference().child("chatrooms").push().setValue(chatModel).addOnSuccessListener(new OnSuccessListener<Void>() {

                //TODO:checkroom 위치 다시 알아보기
                //콜백메소드를 달아주어서 데이터가 완료가 되었을 때 checkChatRoom을 해주는 것이 좋음
                //종종 인터넷 연결에서 firebase 연결이 끊길 때가 있으므로
                // 콜백메소드 안에 checkChatRoom을 넣어서 firebase가 끊겨서 데이터가 입력되지도 않았는데
                //방을 체크하게되는 일을 막겠지
                //그러니까 데이터 입력을 완료했다 콜백 오면 방 체크해주는 것이 좋음
                //추가로 여기쪽의 한 번 요청이 들어오면 버튼을 disable 시켜서 요청 처리되기도 전에
                //똑같은 요청이 또 들어오지 않게 해서 방을 여러개 만들지 않게 한다.
                @Override
                public void onSuccess(Void aVoid) {
                    checkChatRoom();
                }
            });
        }
        else{ //이미 채팅방이 있다면 입력한 데이터 넣어주면 되니까
            ChatModel.Comment comment = new ChatModel.Comment();
            comment.userid = userid;
            comment.isBtn = false; //버튼 아님
            comment.message = editText.getText().toString();
            comment.timestamp = ServerValue.TIMESTAMP; //서버에서 제공하는 메소드

            FirebaseDatabase.getInstance().getReference().child("chatrooms")
                    .child(chatRoomUid) //채팅방이름을 넣어줘야하는데 그 이름은 chatRoomUid가 가지고 있지지
                    .child("comments").push().setValue(comment)
                    .addOnCompleteListener(new OnCompleteListener<Void>() { //여기부턴 콜백부분
                        //데이터 입력이 된 다음 콜백 오면 그때 edText 초기화해주는
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            editText.setText("");
                        }
                    });
        }

    }

    void sendBtnMessage(){
        if(chatRoomUid == null) { //채팅방이 안만들어져있다면
            Toast.makeText(MessageActivity.this, "일반 텍스트 먼저 보내 대화를 시작하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        canSend = false;//팀모 목록 없으면 못보내니까
        get_room_key = null;

        List<String> timmo_list = new ArrayList<>(); //팀모
        List<String>room_list = new ArrayList<>();

        //메인 스레드 쪽에서만 ui 작업 가능하데 조심
        Call<TimmoListResponse> call = service.userTimmoList(USER_TOKEN); //, "application/json"
        call.enqueue(new Callback<TimmoListResponse>() {
            @Override
            public void onResponse(Call<TimmoListResponse> call, Response<TimmoListResponse> response) {
                Toast.makeText(MessageActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                if(response.isSuccessful()){
                    TimmoListResponse result = response.body();

                    Toast.makeText(MessageActivity.this, " 팀모글 사이즈 ", result.getData().size()).show();

                    canSend=true;//팀모 작성한 것이 있으면
                    for(Data item : result.getData() ){
                        timmo_list.add(item.getTitle());
                        room_list.add(item.getRoom());
                    }

                    AlertDialog alertDialog =  new AlertDialog.Builder(MessageActivity.this).setTitle("초대할 팀 프로젝트")
                            .setSingleChoiceItems( timmo_list.toArray(new String[timmo_list.size()]), -1, new DialogInterface.OnClickListener() {
                                @Override public void onClick(DialogInterface dialog, int which) {
                                    get_room_key = room_list.get(which);
                                }
                            })
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(get_room_key != null){
                                        ChatModel.Comment comment = new ChatModel.Comment();
                                        comment.userid = userid;
                                        comment.isBtn = true; //버튼임

                                        //TODO:이 해당 메세지 클릭 시 발생할 이벤트 처리도
                                        //이때 어떤 모집글의 채팅방인지 띄워주기 할 예정
                                        comment.chatroomkey = get_room_key;
                                        comment.message = editText.getText().toString();
                                        comment.timestamp = ServerValue.TIMESTAMP;
                                        comment.message = "팀 협업 메신저로 초대되었습니다. \n 수락하신다면 아래 초대장 받기를 눌러주세요.";

                                        FirebaseDatabase.getInstance().getReference().child("chatrooms")
                                                .child(get_room_key)
                                                .child("comments").push().setValue(comment);
                                    }
                                    else{
                                        Toast.makeText(MessageActivity.this, "팀빌딩 채팅방의 키가 없습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).setNegativeButton("CANCEL",null).show();


                }
                else if(response.body().getStatus() == 400){
                    Toast.makeText(MessageActivity.this, "작성한 팀모글이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TimmoListResponse> call, Throwable t) {
                Toast.makeText(MessageActivity.this, "목록 조회 실패", Toast.LENGTH_SHORT).show();
                Log.e("목록 조회 에러 발생", t.getMessage());
                t.printStackTrace(); // 에러 발생시 에러 발생 원인 단계별로 출력해줌
                //showProgress(false);

            }
        });

    }

    //중복을 체크
    void checkChatRoom(){
        FirebaseDatabase.getInstance().getReference().child("chatrooms")
                .orderByChild("users/"+userid).equalTo(true).addListenerForSingleValueEvent( new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()) {
                    //여기는 chatrooms 아래 users 의 get children해서 이 아이디가 또 있는지 검사를 할것임
                    ChatModel chatModel = item.getValue(ChatModel.class);
                    //내가 대화건 상대인 destinatonUid가 있는지
                    if (chatModel.users.containsKey(destinatonUserid) && chatModel.users.size() ==2) {
                        chatRoomUid = item.getKey(); //이것은 하나의 chatrooms 값(방id)을 말하는 것임
                        //방 id를 가져와야 내가 원하는 코멘트를 넣을 수 있으니까
                        send_btn.setEnabled(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MessageActivity.this));
                        recyclerView.setAdapter(new RecyclerViewAdapter());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //리사이클러뷰 어댑터 클래스
    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        //comment 담기 위해
        List<ChatModel.Comment> comments;
        //채팅창의 유저 정보 받아주는
        UserModel userModel;

        public RecyclerViewAdapter() {
            comments = new ArrayList<>(); //대화 내용 받아주는 부분

            //챗방 상대 유저 정보 가져오기
            FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot item : snapshot.getChildren() ){
                        if( (item.getValue(UserModel.class).userid ).equals(destinatonUserid)  ){
                            userModel = item.getValue(UserModel.class);
                            break;
                        }
                    }
                    getMessageList(); //유저 정보 가져온 후 메세지

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        //메세지 리스트를 가져와서 넣는건가 메소드
        void getMessageList(){
            //특정 방의 comments 다 읽어 들어오기 위해 코멘트 가져와서 리스너로 읽어들이고
            FirebaseDatabase.getInstance().getReference().child("chatrooms").child(chatRoomUid).child("comments")
                    .addValueEventListener( new ValueEventListener(){
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

            //viewholder는 뷰 재사용 시 쓰는 클래스, inner class로 만들어 주면 됨
            return new MessageViewHolder(view);
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            MessageViewHolder messageViewHolder = ((MessageViewHolder) holder);

            //공통 적용
            messageViewHolder.tv_main_message.setText(comments.get(position).message);
            long unixTime = (long) comments.get(position).timestamp;
            Date date = new Date(unixTime);
            simpleDateFormat2.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
            String time = simpleDateFormat2.format(date); // 시 분 만
            //조건문에서 시간 없애줌
            messageViewHolder.tv_dest_timestamp.setText(time);
            messageViewHolder.tv_my_timestamp.setText(time);

            if(comments.get(position).isBtn){ // 버튼 이라면

                if(!comments.get(position).userid.equals(destinatonUserid)){ //내가 보낸거면 버튼 기능 안넣음
                    messageViewHolder.tv_main_message.setText(userModel.userName + "님을 팀협업 채팅방으로 초대했습니다.");
                    messageViewHolder.tv_sub_message.setText("");
                }
                else{ // 버튼이고 상대방쪽에 받은거면 초대 눌렀을 때 초대 되도록
                    messageViewHolder.tv_sub_message.setText("초대 수락 하기");
                    //messageViewHolder.tv_sub_message.setOnClickListener();  이걸로도 해볼까
                    messageViewHolder.tv_sub_message.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO:해당 채팅방에 user 넣기
                            //TODO:팀모 선택 목록 조회
                            FirebaseDatabase.getInstance().getReference().child("chatrooms")
                                    .child(comments.get(position).chatroomkey)
                                    .child("users").push().setValue(userModel.userName)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() { //여기부턴 콜백부분
                                        //데이터 입력이 된 다음 콜백 오면 그때 edText 초기화해주는
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(MessageActivity.this, "팀빌딩 채팅방으로 초대 되었습니다.\n 채팅목록을 확인해주세요." ,Toast.LENGTH_LONG).show();
                                            comments.get(position).isBtn = false;
                                            comments.get(position).message="팀협업 채팅방으로 초대 완료";

                                        }
                                    });

                        }
                    });
                }

            }
            else{
                //sub 메세지 gone 처리
                //여기서는 서브 메세지 일단 다 gone
                messageViewHolder.tv_sub_message.setVisibility(View.GONE);
            }
            //버튼 아니라면
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
                messageViewHolder.tv_name.setText(userModel.userName);
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

        private class MessageViewHolder extends RecyclerView.ViewHolder {

            public TextView tv_name;
            public ImageView iv_profile;
            public LinearLayout ll_center;//기존 상대방->초대파트 구현 위한 ,배경색
            public LinearLayout ll_main;
            public LinearLayout ll_left ;

            public TextView tv_dest_timestamp;
            public TextView tv_my_timestamp;
            public TextView tv_main_message; // 글씨색
            public TextView tv_sub_message;

            public MessageViewHolder(View view) {
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