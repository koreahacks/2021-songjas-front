package com.example.timmo_songjas.feature.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.timmo_songjas.R;

import java.util.ArrayList;

public class ProjectDetailMemberAdapter extends RecyclerView.Adapter<ProjectDetailMemberAdapter.ViewHolder> {

    private ArrayList<ProjectDetailMemberItem> list;
    private Context context;

    public ProjectDetailMemberAdapter(ArrayList<ProjectDetailMemberItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    //뷰 홀더 생성
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile_list, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    //만들어진 뷰 홀더에 data 삽입
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //My my = myList.get(position);
        final ProjectDetailMemberAdapter.ViewHolder viewHolder = (ProjectDetailMemberAdapter.ViewHolder)holder;
        viewHolder.nickname.setText(list.get(position).nickname);
        //Glide.with(context)
        Glide.with(viewHolder.itemView.getContext())
                .load(list.get(position).profile)
                .apply(new RequestOptions().circleCrop())
                .into(viewHolder.image);
    }

    //데이터 리스트 개수
    @Override
    public int getItemCount() {
        return list.size();
    }

    //보존 뷰 홀더
    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView nickname;
        public ImageView image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nickname = (TextView) itemView.findViewById(R.id.item_profile_list_nickname);
            image = itemView.findViewById(R.id.item_profile_list_image);
        }
    }
}
