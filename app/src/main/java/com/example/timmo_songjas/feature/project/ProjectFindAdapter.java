package com.example.timmo_songjas.feature.project;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timmo_songjas.R;

import java.util.ArrayList;

public class ProjectFindAdapter extends RecyclerView.Adapter<ProjectFindAdapter.ProjectFindViewHolder> {

    private ArrayList<ProjectFindItem> mProject;
    private Context context;

    public ProjectFindAdapter(ArrayList<ProjectFindItem> mProject, Context context) {
        this.mProject = mProject;
        this.context = context;
    }

    @NonNull
    @Override
    //뷰 홀더 생성
    public ProjectFindViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project_find, parent, false);
        ProjectFindViewHolder vh = new ProjectFindViewHolder(view);
        return vh;
    }

    //만들어진 뷰 홀더에 data 삽입
    @Override
    public void onBindViewHolder(@NonNull ProjectFindViewHolder holder, int position) {

        ProjectFindAdapter.ProjectFindViewHolder viewHolder = (ProjectFindAdapter.ProjectFindViewHolder)holder;
        //My my = myList.get(position);
        viewHolder.univ.setText(mProject.get(position).univ);
        viewHolder.l_addr.setText(mProject.get(position).l_addr);
        viewHolder.s_addr.setText(mProject.get(position).s_addr);
        viewHolder.dday.setText(mProject.get(position).dday);
        viewHolder.title.setText(mProject.get(position).title);
        viewHolder.type.setText(mProject.get(position).type);
        viewHolder.field.setText(mProject.get(position).field);
        viewHolder.hope_position.setText(mProject.get(position).hope_position);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProjectDetailActivity.class);
                intent.putExtra("project_id", mProject.get(position).project_id);
                Log.e("idddddddddddadapter", Integer.toString(mProject.get(position).project_id));
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
    public static class ProjectFindViewHolder extends RecyclerView.ViewHolder{

        public TextView univ;
        public TextView l_addr, s_addr;
        public TextView dday;
        public TextView title;
        public TextView type;
        public TextView field;
        public TextView hope_position;

        public ConstraintLayout project_item ;


        public ProjectFindViewHolder(@NonNull View itemView) {
            super(itemView);
            univ = (TextView) itemView.findViewById(R.id.tv_univ_projectfind);
            l_addr = (TextView) itemView.findViewById(R.id.tv_largeaddress_projectfind);
            s_addr = (TextView) itemView.findViewById(R.id.tv_smalladdress_projectfind);
            dday = (TextView) itemView.findViewById(R.id.tv_dday_projectfind);
            title = (TextView) itemView.findViewById(R.id.tv_title_projectfind);
            type = (TextView) itemView.findViewById(R.id.tv_type_projectfind);
            field = (TextView) itemView.findViewById(R.id.tv_field_projectfind);
            hope_position = (TextView) itemView.findViewById(R.id.tv_position_projectfind);
            project_item = itemView.findViewById(R.id.project_item_layout);
        }
    }
}
