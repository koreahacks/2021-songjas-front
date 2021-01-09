package com.example.timmo_songjas.chatting.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timmo_songjas.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TeamChatRecyclerViewAdapter extends RecyclerView.Adapter<TeamChatRecyclerViewAdapter.ViewHolder> {
    private Context context;

    public TeamChatRecyclerViewAdapter(Context context) {
        this.context = context;
    }



    @NonNull
    @Override
    public TeamChatRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat,parent,false);
        //그냥 view 리턴하면 에러나서 뷰 재사용할 수 있는 뷰홀더 사용해서 리턴
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamChatRecyclerViewAdapter.ViewHolder holder, int position) {

        final TeamChatRecyclerViewAdapter.ViewHolder customViewHolder = (TeamChatRecyclerViewAdapter.ViewHolder)holder;

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
