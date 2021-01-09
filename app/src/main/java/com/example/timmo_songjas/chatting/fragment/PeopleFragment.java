package com.example.timmo_songjas.chatting.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timmo_songjas.R;
import com.example.timmo_songjas.chatting.chat.MessageActivity;
import com.example.timmo_songjas.chatting.model.UserModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.example.timmo_songjas.feature.utils.CommonValues.SEVER_USERID_LOGGED_IN;

public class PeopleFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_people, container, false);

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.rv_peoplefrag);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        recyclerView.setAdapter(new PeopleFragmentRecyclerViewAdapter());

        return view;
    }

    @SuppressWarnings("NullableProblems")
    class PeopleFragmentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        List<UserModel> userModels;//친구 목록 쌓이는 리스트

        //바로 DB로 접속할것이기 때문에 생성자 필요, DB를 검색할것임 여기서
        public PeopleFragmentRecyclerViewAdapter() {
            userModels = new ArrayList<>();//이게 DB를 검색하나봐봐
            //Server Test
            //final String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            //이거 싱글톤 패턴으로 해도 된다
            //나중에 7강 강의에 ANR 오류 파트 보고 리스너 바꿔주기!!!!!!!!!!!!!!!!!1
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
//                        if(userModel.uid.equals(myUid)){
//                            continue;
//                        }
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend,parent,false);

            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

            //클릭하면 채팅창 불러오는 애니메이션 추가
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), MessageActivity.class);
                    intent.putExtra("destinationUserid",userModels.get(position).userid); //상대방 uid

                    //애니매이션 효과
                    ActivityOptions activityOptions = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        //들어오는 화면이 오른쪽에서 들어올것임, 기존화면은 왼쪽으로 밀려나고
                        activityOptions = ActivityOptions.makeCustomAnimation(view.getContext(), R.anim.fromright,R.anim.toleft);
                        startActivity(intent,activityOptions.toBundle());
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
            //public TextView textView_comment;

            public CustomViewHolder(View view) {
                super(view);
                imageView = (ImageView) view.findViewById(R.id.imageView_profile_friendItem);
                textView = (TextView) view.findViewById(R.id.textView_emailId_friendItem);
                //textView_comment = (TextView)view.findViewById(R.id.textview_stateComment_friendItem);
            }
        }
    }

}
