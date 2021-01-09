package com.example.timmo_songjas.feature.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.timmo_songjas.R;
import com.example.timmo_songjas.feature.member.MemberDetailActivity;

public class ProfileMemberFragment extends Fragment {

    Context context;
    RecyclerView recyclerView;
    ProfileMemberAdapter profileMemberAdapter;


    public ProfileMemberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = container.getContext();
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);

        //리싸이클러뷰 불러오기
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_profilemember);
        LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        //어답터를 통해 내용 추가하기
        profileMemberAdapter = new ProfileMemberAdapter(container.getContext());

        //profileMemberAdapter.addItem(new ProfileMemberItem("서울특별시", "나야나", "공모전", "컴퓨터그래픽", "그래픽개발자"));

        recyclerView.setAdapter(profileMemberAdapter);

        //리싸이클뷰가 비었을 경우 글자 설정
        TextView emptyText = (TextView) rootView.findViewById(R.id.tv_empty_profilemember);
        if(profileMemberAdapter.getItemCount() == 0){
            emptyText.setText("아직 팀글이 없습니다.");
            emptyText.setVisibility(View.VISIBLE);
        }

        //클릭 이벤트 처리
        profileMemberAdapter.setOnItemClickListener(new ProfileMemberAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(ProfileMemberAdapter.ViewHolder holder, View view, int position) {
                ProfileMemberItem item = profileMemberAdapter.getItem(position);
                //화면 이동
                Intent intent = new Intent(getActivity(), MemberDetailActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
    
    //TODO: 서버에서 데이터 받아오기
}