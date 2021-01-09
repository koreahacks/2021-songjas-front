package com.example.timmo_songjas.feature.member;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timmo_songjas.R;

import java.util.ArrayList;

public class MemberDetailCareerAdapter extends RecyclerView.Adapter<MemberDetailCareerAdapter.ViewHolder> {

    private ArrayList<MemberDetailCareerItem> list;
    private Context context;

    public MemberDetailCareerAdapter(ArrayList<MemberDetailCareerItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    //뷰 홀더 생성
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_career, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    //만들어진 뷰 홀더에 data 삽입
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //My my = myList.get(position);
        final  MemberDetailCareerAdapter.ViewHolder viewHolder = (MemberDetailCareerAdapter.ViewHolder)holder;
        viewHolder.career_title.setText(list.get(position).career_title);
        viewHolder.career_date.setText(list.get(position).career_date);
    }

    //데이터 리스트 개수
    @Override
    public int getItemCount() {
        return list.size();
    }

    //보존 뷰 홀더
    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView career_title;
        public TextView career_date;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            career_title = (TextView) itemView.findViewById(R.id.career_title);
            career_date = itemView.findViewById(R.id.career_date);
        }
    }
}
