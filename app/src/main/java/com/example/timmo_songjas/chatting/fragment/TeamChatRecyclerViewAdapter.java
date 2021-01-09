package com.example.timmo_songjas.chatting.fragment;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timmo_songjas.R;
import com.example.timmo_songjas.chatting.chat.GroupMessageActivity;
import com.example.timmo_songjas.chatting.chat.MessageActivity;
import com.example.timmo_songjas.chatting.model.ChatModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import static com.example.timmo_songjas.feature.utils.CommonValues.SEVER_USERID_LOGGED_IN;


public class TeamChatRecyclerViewAdapter extends RecyclerView.Adapter<TeamChatRecyclerViewAdapter.ViewHolder> {
    private SimpleDateFormat simpleDateFormat;
//    private SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("a hh:mm"); //오전 오후

    private Context context;

    private List<ChatModel> chatModels ;
    private List<String> room_keys = new ArrayList<>();// 방에대한 키들 , 인텐트에 desti Room 으로 같이 보냄

    //대화하는 사람들의 데이터 담기는 곳
    private ArrayList<String> destinationUsers ;

    private int num_people=0;

    //팀협업 어탭터
    //어댑터생성자
    @SuppressLint("SimpleDateFormat")
    public TeamChatRecyclerViewAdapter(Context context) {
        this.context = context;
        chatModels = new ArrayList<>();
        room_keys = new ArrayList<>();
        simpleDateFormat = new SimpleDateFormat("MM월 dd일");
        destinationUsers = new ArrayList<>();

        getTeamChatListDataFromFirebase();

    }

    public void getTeamChatListDataFromFirebase(){
        //채팅 목록 정보 가져올거야                                             내가 소속된 방을 들어갈 수 있음,내uid가 true인 것 .equalTo(true)
        FirebaseDatabase.getInstance().getReference().child("chatrooms").orderByChild("users/"+ SEVER_USERID_LOGGED_IN).equalTo(true).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.getChildrenCount()>0){ //뭐지 뭐 활용할순있겠다
//                }
                chatModels.clear();
                room_keys.clear();
                for(DataSnapshot item : snapshot.getChildren()){
                    if(item.getValue(ChatModel.class).isTeamChat) {
                        chatModels.add(item.getValue(ChatModel.class));
                        room_keys.add(item.getKey());
                    }
                }
                notifyDataSetChanged();//새로고침

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    //위에서 받은 데이터 보여주기 위해
    public TeamChatRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat,parent,false);
        //그냥 view 리턴하면 에러나서 뷰 재사용할 수 있는 뷰홀더 사용해서 리턴
        return new ViewHolder(view);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TeamChatRecyclerViewAdapter.ViewHolder holder, int position) {
        //아래 커스텀뷰홀더에서 찾은거를 바인딩 시켜해야
        //커스텀 뷰 홀더를 만들고
        final TeamChatRecyclerViewAdapter.ViewHolder customViewHolder = (TeamChatRecyclerViewAdapter.ViewHolder)holder;

        String destination_userid = null;

        //챗방에 있는 유저 체크
        for(String user: chatModels.get(position).users.keySet()){
            //그 중 내가 아닌 유저 뽑아오기
            if(!user.equals(SEVER_USERID_LOGGED_IN)){
                destination_userid = user;
                destinationUsers.add(destination_userid);
            }

        }

        num_people= chatModels.get(position).users.size();
        //채팅방 인원num_people
        //채팅방의 사람 수
        customViewHolder.textView_numofpeople.setText(num_people + "명 · ");


        //이건 챗방 이미지랑 챗방 이름 설정하는거라 뭐 굳이
        customViewHolder.tV_title_chat.setText("단체 챗방");
        //뭐 나중에 프로젝트 등록 다되면 바꾸던가


        //채팅방의 마지막 메세지 띄우기기
        //메시지를 내림 차순으로 정렬 후 마지막 메세지의 키값을 가져오는 과정
        Map<String,ChatModel.Comment> commentMap = new TreeMap<>(Collections.reverseOrder());
        commentMap.putAll(chatModels.get(position).comments);//내림 차순 이니 채팅의 첫번째 값 뽑기
        //마지막 메세지가 있을 때만 표시하도록 , 에러처리해줌
        if(commentMap.keySet().toArray().length > 0){
            String lastMessageKey = (String) commentMap.keySet().toArray()[0];
            //뽑아 낸 것을 바로 바인딩
            customViewHolder.textView_last_message.setText(chatModels.get(position).comments.get(lastMessageKey).message);
            //TimeStamp
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
            //lastMessageKey 이 키를 가진 코멘트를 가져오겠다.
            long unixTime = (long) chatModels.get(position).comments.get(lastMessageKey).timestamp;
            Date date = new Date(unixTime);
            customViewHolder.textView_timestamp.setText(simpleDateFormat.format(date));
        }


        customViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                //채팅방 인원수 체크
                if (chatModels.get(position).users.size() > 2 ){
                    intent = new Intent(view.getContext(), GroupMessageActivity.class);
                    intent.putExtra("destinationRoom",room_keys.get(position));
                    intent.putExtra("userid",chatModels.get(position).teamchatuserid );
                    //팀장 아이디 필요

                }
                else{
                    intent = new Intent(view.getContext(), MessageActivity.class);
                    intent.putExtra("destinationUid",destinationUsers.get(position));
                }

                //애니메이션 추가 화면 밀리면서 이동하는 효과 줌
                // 그다음 뒤로가기 했을 때 채팅창이 또 옆으로 밀려나는 효과주기위해 messageActivity로 이동
                ActivityOptions activityOptions = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    activityOptions = ActivityOptions.makeCustomAnimation(view.getContext(), R.anim.fromright,R.anim.toleft);
                    context.startActivity(intent,activityOptions.toBundle());
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return chatModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView tV_title_chat;
        public TextView textView_last_message;
        public TextView textView_timestamp;
        public TextView textView_numofpeople;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.iv_chat);
            tV_title_chat = (TextView)view.findViewById(R.id.tv_title_chat);
            textView_last_message = (TextView)view.findViewById(R.id.tv_lastmessage_chat);
            textView_timestamp = (TextView)view.findViewById(R.id.tv_timestamp_chat);
            textView_numofpeople = (TextView)view.findViewById(R.id.tv_numofpeople_chat);
        }
    }

}

