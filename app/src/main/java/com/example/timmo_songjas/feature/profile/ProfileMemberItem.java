package com.example.timmo_songjas.feature.profile;

public class ProfileMemberItem {
    String state;
    String content;
    String type;
    String field;
    String member;

    //constructor
    public ProfileMemberItem(String state, String content, String type, String field, String member) {
        this.state = state;
        this.content = content;
        this.type = type;
        this.field = field;
        this.member = member;
    }

    //setter and getter
    public String getState() { return state; }

    public void setState(String state) {
        this.state = state;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }


}

