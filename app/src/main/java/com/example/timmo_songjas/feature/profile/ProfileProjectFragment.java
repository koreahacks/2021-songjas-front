package com.example.timmo_songjas.feature.profile;

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
import com.example.timmo_songjas.feature.project.ProjectDetailActivity;

import java.util.ArrayList;


public class ProfileProjectFragment extends Fragment {

    RecyclerView recyclerView;
    ProfileProjectAdapter profileProjectAdapter;

    public ProfileProjectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_profile_project, container, false);

        //리싸이클러뷰 불러오기
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_profileproject);
        LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        //어답터를 통해 내용 추가하기
        profileProjectAdapter = new ProfileProjectAdapter(container.getContext());

        //profileProjectAdapter.addItem(new ProfileProjectItem("대전광역시", "D-7", "코리아해커톤 준비 뒤질 것 같아ㅇㅅㅇ", "해커톤", "IT/개발", "프란트2/백엔드3"));
        recyclerView.setAdapter(profileProjectAdapter);

        //리싸이클뷰가 비었을 경우 글자 설정
        TextView emptyText = (TextView) rootView.findViewById(R.id.tv_empty_profileproject);
        if(profileProjectAdapter.getItemCount() == 0){
            emptyText.setText("아직 팀모글이 없습니다.");
            emptyText.setVisibility(View.VISIBLE);
        }

        //클릭 이벤트 처리
        profileProjectAdapter.setOnItemClickListener(new ProfileProjectAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(ProfileProjectAdapter.ViewHolder holder, View view, int position) {
                ProfileProjectItem item = profileProjectAdapter.getItem(position);
                //화면 이동
                Intent intent = new Intent(getActivity(), ProjectDetailActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
    
    //TODO: 데이터 받아오기
}