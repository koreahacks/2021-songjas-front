package com.example.timmo_songjas.feature.project;

public class ProjectFindItem {
    public String univ, loca, dday, title, type, field, hope_position;

    public int project_id;

    public ProjectFindItem(int project_id, String univ, String loca, String dday, String title, String type, String field, String hope_position){
        this.project_id = project_id;
        this.univ = univ;
        this.loca = loca;
        this.dday = dday;
        this.title = title;
        this.type = type;
        this.field = field;
        this.hope_position = hope_position;
    }
}
