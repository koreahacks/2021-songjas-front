package com.example.timmo_songjas.feature.project;

public class ProjectFindItem {
    public String univ, l_addr, s_addr, dday, title, type, field, hope_position;

    public int project_id;

    public ProjectFindItem(int project_id, String univ, String l_addr, String s_addr, String dday, String title, String type, String field, String hope_position){
        this.project_id = project_id;
        this.univ = univ;
        this.l_addr = l_addr;
        this.s_addr = s_addr;
        this.dday = dday;
        this.title = title;
        this.type = type;
        this.field = field;
        this.hope_position = hope_position;
    }
}
