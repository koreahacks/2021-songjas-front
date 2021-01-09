package com.example.timmo_songjas.chatting.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timmo_songjas.R;
import com.example.timmo_songjas.chatting.chat.GroupMessageActivity;
import com.example.timmo_songjas.chatting.model.ChatModel;
import com.example.timmo_songjas.chatting.model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.timmo_songjas.feature.utils.CommonValues.SEVER_USERID_LOGGED_IN;

//
public class SelectFriendActivity extends AppCompatActivity {
    ChatModel chatModels = new ChatModel();
    private ValueEventListener valueEventListener;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_friend);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_selectfriend);
        recyclerView.setAdapter(new SelectFriendRecyclerViewAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //초대
        Button btn = (Button)findViewById(R.id.btn_selectfriend);
        //TODO:여기 내용 프로젝트 에드 2에서 받아서 3에서 완성시키는, 채팅방 먼저 만들어서 룸키 서버에 보내는
        btn.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(chatModels.users.size()  > 1){ // 체크한 사람이 있을 때만 방 만들게
                    //Server Test
                    //String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    chatModels.users.put(SEVER_USERID_LOGGED_IN,true); // 로그인한 유저의 서버에서 받은 id 넣기
                    chatModels.isTeamChat = true;//팀협업
                    FirebaseDatabase.getInstance().getReference().child("chatrooms").push().setValue(chatModels);
                    //chatModel의 user 파트가 hashmap으로 구현되어 있어 이렇게만 해도
                    // db에 값이 넣어지나봐...여러 데이터가 한번에
                    Toast.makeText(getApplicationContext(),"단체 만들어짐" , Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }
        });

    }

    class SelectFriendRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        List<UserModel> userModels;//친구 목록 쌓이는 리스트
        //바로 DB로 접속할것이기 때문에 생성자 필요, DB를 검색할것임 여기서
        public SelectFriendRecyclerViewAdapter() {
            userModels = new ArrayList<>();//이게 DB를 검색하나봐봐
            //final String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            //이거 싱글톤 패턴으로 해도 된다
            //나중에 7강 강의에 ANR 오류 파트 보고 리스너 바꿔주기!!!!!!!! 안바꿔도 뭐
            //https://stack07142.tistory.com/282
            FirebaseDatabase.getInstance().getReference().child("users").addValueEventListener(new ValueEventListener() {

                @Override //서버에서 넘어오는 데이터, 여기서 넘어오는 데이터를 userModel list에 담으면 됨
                public void onDataChange(DataSnapshot dataSnapshot) {
                    userModels.clear();//이게 없으면 12123 이렇게 될 수 있음, 누적 데이터 없애기

                    for(DataSnapshot snapshot :dataSnapshot.getChildren()){ //넘어온 데이터를 list에 add 할건데
                        //Log.d("people fragment", "ValueEventListener : " + snapshot.getValue());
                        UserModel userModel = snapshot.getValue(UserModel.class);

                        //Server Test
                        if(userModel.userid.equals(SEVER_USERID_LOGGED_IN)){
                            continue;
                        }
                        userModels.add(userModel);
                    }
                    //새로 고침 해야해서 이거 꼭 넣어야함, 데이터가 쌓이고나서 새로 고침해야 데이터가 뜨니까
                    notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //여긴 아이템 넣기기
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend_selected,parent,false);

            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
//            Glide.with
//                    (holder.itemView.getContext())
//                    .load(userModels.get(position).profileImageUrl)
//                    .apply(new RequestOptions().circleCrop())//어떻게 이미지를 줄건지
//                    .into(((CustomViewHolder)holder).imageView);
//            ((CustomViewHolder)holder).textView.setText(userModels.get(position).userName);


            //그 사람 리스트에서 그 사람 칸 전체 클릭 시 이벤트 처리
            //클릭하면 채팅창 불러오는 애니메이션 추가
            //해당 아이템 누르면 채팅방으로 이동하는 코드인데 굳이
            //이거 일대일 인가봐
            //TODO:프로젝트 다 작성 후 지울지 말지 결정
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), GroupMessageActivity.class);
                    intent.putExtra("destinationRoom",userModels.get(position).userid); //상대방 uid

                    //애니매이션 효과
                    ActivityOptions activityOptions = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        //들어오는 화면이 오른쪽에서 들어올것임, 기존화면은 왼쪽으로 밀려나고
                        activityOptions = ActivityOptions.makeCustomAnimation(view.getContext(), R.anim.fromright,R.anim.toleft);
                        startActivity(intent,activityOptions.toBundle());
                    }

                }
            });


            //체크박스 리스너너
            ((CustomViewHolder) holder).checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        chatModels.users.put(userModels.get(position).userid , true);
                        //체팅방에 초대할 사람 uid랑 true를 같이 넣네
                        // 체크된 사람면 해쉬맵에 같이 넣음
                    }
                    else{//체크 취소 상태
                        chatModels.users.remove(userModels.get(position).userid , true);
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return userModels.size();
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {
            //iterm_friend에 있던 것들 , 친구 목록에 item에 들어가는 요소들
            public ImageView imageView;
            public TextView textView;
            //            public TextView textView_comment;
            public CheckBox checkBox;

            public CustomViewHolder(View view) {
                super(view);
                imageView = (ImageView) view.findViewById(R.id.iv_selectfriend);
                textView = (TextView) view.findViewById(R.id.tv_name_selectfriend);
//                textView_comment = (TextView)view.findViewById(R.id.tv_stateComment_selectfriend);
                checkBox = (CheckBox)view.findViewById(R.id.cb_selectfriend);

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