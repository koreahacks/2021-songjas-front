package com.example.timmo_songjas.chatting.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timmo_songjas.R;

public class TeamChatFragment extends Fragment {
    RecyclerView recyclerView;
    TeamChatRecyclerViewAdapter adapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_team,container,false);
        recyclerView  = (RecyclerView) view.findViewById(R.id.rv_teamchat_frag);
        //recycler뷰를 어떤 형식으로 넣어줄것이냐 지정한것, 리니어로 넣어준다.
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        //getFragmentManager

        adapter = new TeamChatRecyclerViewAdapter( container.getContext() );

        recyclerView.setAdapter( adapter );
        return view;
    }
}
