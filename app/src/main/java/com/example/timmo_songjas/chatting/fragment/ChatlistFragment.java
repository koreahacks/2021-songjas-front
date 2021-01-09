package com.example.timmo_songjas.chatting.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//이건 채팅방 리스트하는 페이지 전체 탭바 있는
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.timmo_songjas.R;
import com.google.android.material.tabs.TabLayout;
import com.example.timmo_songjas.chatting.fragment.ChatFragment;

public class ChatlistFragment extends Fragment {

    private TabLayout tabLayout;
    private View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(R.id.fl_chatlist, new ChatFragment()) .commit();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chatlist,container,false);
        initTab();
        return view;
    }

    private void initTab(){
        //각 탭
        tabLayout = (TabLayout)view.findViewById(R.id.tl_chat);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // TODO : process tab selection event.
                switch (tab.getPosition()){
                    case 0:
                        getFragmentManager().beginTransaction().replace(R.id.fl_chatlist, new ChatFragment()) .commit();
                        break;
                    case(1):
                        getFragmentManager().beginTransaction().replace(R.id.fl_chatlist, new TeamChatFragment()) .commit();
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // do nothing
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // do nothing
            }
        }) ;
    }

}
