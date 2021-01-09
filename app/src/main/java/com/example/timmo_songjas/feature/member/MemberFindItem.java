package com.example.timmo_songjas.feature.member;

public class MemberFindItem {
    public String profile, loca, title, type, field, hope_position;

    public int member_id;

    public MemberFindItem(int member_id, String profile, String loca, String title, String type, String field, String hope_position){
        this.member_id = member_id;

        this.profile = profile;
        this.loca = loca;
        this.title = title;
        this.type = type;
        this.field = field;
        this.hope_position = hope_position;
    }
}

