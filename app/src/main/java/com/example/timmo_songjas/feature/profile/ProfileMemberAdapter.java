package com.example.timmo_songjas.feature.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.timmo_songjas.R;

import java.util.ArrayList;

public class ProfileMemberAdapter extends RecyclerView.Adapter<ProfileMemberAdapter.ViewHolder>{
    Context context;
    ArrayList<ProfileMemberItem> items = new ArrayList<>();
    OnItemClickListener listener;

    public static interface OnItemClickListener{
        public void OnItemClick(ViewHolder holder, View view, int position);
    }

    public ProfileMemberAdapter(Context context) {this.context = context; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View  itemView  = inflater.inflate(R.layout.item_profile_member, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProfileMemberItem item = items.get(position);
        holder.setItem(item);

        holder.setOnItemClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //addItem, items  메소드
    public void addItem(ProfileMemberItem item){
        items.add(item);
    }
    public void addItems(ArrayList<ProfileMemberItem> items){
        this.items = items;
    }

    //getItem 메소드
    public ProfileMemberItem getItem(int position){
        return items.get(position);
    }

    //클릭 이벤트 처리
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    //ViewHoder 구현
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvState;
        TextView tvContent;
        TextView tvType;
        TextView tvField;
        TextView tvMember;
        OnItemClickListener listener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvState = (TextView)itemView.findViewById(R.id.tv_state_profrfile);
            tvContent = (TextView)itemView.findViewById(R.id.tv_content_profile);
            tvType = (TextView)itemView.findViewById(R.id.tv_type_profile);
            tvField = (TextView)itemView.findViewById(R.id.tv_field_profile);
            tvMember = (TextView)itemView.findViewById(R.id.tv_member_profile);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.OnItemClick(ViewHolder.this, v, position );
                    }
                }
            });

        }

        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }

        public void setItem(ProfileMemberItem item){
            tvState.setText(item.getState());
            tvContent.setText(item.getContent());
            tvType.setText(item.getType());
            tvField.setText(item.getField());
            tvMember.setText(item.getMember());
        }
    }
}
