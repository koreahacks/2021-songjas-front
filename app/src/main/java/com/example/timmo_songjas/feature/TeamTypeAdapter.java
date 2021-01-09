package com.example.timmo_songjas.feature;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timmo_songjas.R;

import java.util.ArrayList;

public class TeamTypeAdapter extends RecyclerView.Adapter<TeamTypeAdapter.ViewHolder> {

    private ArrayList<TeamTypeItem> list;
    private Context context;

    public TeamTypeAdapter(ArrayList<TeamTypeItem> member_list, Context context) {
        this.list = member_list;
        this.context = context;
    }

    @NonNull
    @Override
    //뷰 홀더 생성
    //위에서 받아온 데이터를 보여주기 위해
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_teamtype_list, parent, false);

        //
        return new ViewHolder(view);
    }

    //만들어진 뷰 홀더에 data 삽입
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //My my = myList.get(position);
        //holder.teamtype.setText(list.get(position).teamtype);

        //아래 커스텀뷰홀더에서 찾은 것을 바인딩 시켜해야
        //커스텀 뷰 홀더를 만들고
        final TeamTypeAdapter.ViewHolder viewHolder = (TeamTypeAdapter.ViewHolder)holder;
        //TODO:만약에 에러나면 리스트 사용법 다시 찾기
        viewHolder.teamtype.setText( list.get(position).teamtype );

    }

    //데이터 리스트 개수
    @Override
    public int getItemCount() {
        return list.size();
    }

    //보존 뷰 홀더
    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView teamtype;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teamtype = (TextView) itemView.findViewById(R.id.tv_teamtype);
        }
    }
}
