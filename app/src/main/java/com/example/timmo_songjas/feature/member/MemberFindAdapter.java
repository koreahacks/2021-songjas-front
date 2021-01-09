package com.example.timmo_songjas.feature.member;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.timmo_songjas.R;

import java.util.ArrayList;

public class MemberFindAdapter extends RecyclerView.Adapter<MemberFindAdapter.MemberFindViewHolder> {

    private ArrayList<MemberFindItem> mProject;
    private Context context;

    public MemberFindAdapter(ArrayList<MemberFindItem> mProject, Context context) {
        this.mProject = mProject;
        this.context = context;
    }

    @NonNull
    @Override
    //뷰 홀더 생성
    public MemberFindViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member_find, parent, false);
        MemberFindViewHolder vh = new MemberFindViewHolder(view);
        return vh;
    }

    //만들어진 뷰 홀더에 data 삽입
    @Override
    public void onBindViewHolder(@NonNull MemberFindViewHolder holder, int position) {
        //My my = myList.get(position);
        MemberFindAdapter.MemberFindViewHolder viewHolder = (MemberFindAdapter.MemberFindViewHolder)holder;

        //TODO: viewHolder.itemView.getContext() 인지 그냥 context인지 확인
        Glide.with(viewHolder.itemView.getContext())
                .load(mProject.get(position).profile)
                .apply(new RequestOptions().circleCrop())
                .into(holder.image);
        viewHolder.loca.setText(mProject.get(position).loca);
        viewHolder.title.setText(mProject.get(position).title);
        viewHolder.type.setText(mProject.get(position).type);
        viewHolder.field.setText(mProject.get(position).field);
        viewHolder.hope_position.setText(mProject.get(position).hope_position);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MemberDetailActivity.class);
                intent.putExtra("member_id", mProject.get(position).member_id);
                context.startActivity(intent);
            }
        });
    }

    //데이터 리스트 개수
    @Override
    public int getItemCount() {
        return mProject.size();
    }

    //보존 뷰 홀더
    public static class MemberFindViewHolder extends RecyclerView.ViewHolder{

        public ImageView image;

        public TextView loca;
        public TextView title;
        public TextView type;
        public TextView field;
        public TextView hope_position;

        public ConstraintLayout member_item ;


        public MemberFindViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.pic_partyfind);
            loca = (TextView) itemView.findViewById(R.id.tv_loca_memberfind);
            title = (TextView) itemView.findViewById(R.id.tv_title_memberfind);
            type = (TextView) itemView.findViewById(R.id.tv_type_memberfind);
            field = (TextView) itemView.findViewById(R.id.tv_field_memberfind);
            hope_position = (TextView) itemView.findViewById(R.id.tv_position_memberfind);

            member_item = itemView.findViewById(R.id.member_item_layout);
        }
    }
}
