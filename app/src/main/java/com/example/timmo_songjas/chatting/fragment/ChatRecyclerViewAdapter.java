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
import java.util.List;

public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<String> room_keys;// 리스팅할 방에대한 키들 , 인텐트에 desti Room 으로 같이 보냄
    private ArrayList<String> destinationUsers ;    //대화하는 사람들의 데이터 담기는 곳


    public ChatRecyclerViewAdapter(Context context) {

        this.context = context;

    }
    @Override
    //위에서 받은 데이터 보여주기 위해
    public ChatRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat,parent,false);
        //그냥 view 리턴하면 에러나서 뷰 재사용할 수 있는 뷰홀더 사용해서 리턴
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ChatRecyclerViewAdapter.ViewHolder holder, int position) {
        //아래 커스텀뷰홀더에서 찾은거를 바인딩 시켜해야
        //커스텀 뷰 홀더를 만들고
        final ChatRecyclerViewAdapter.ViewHolder customViewHolder = (ChatRecyclerViewAdapter.ViewHolder) holder;
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
