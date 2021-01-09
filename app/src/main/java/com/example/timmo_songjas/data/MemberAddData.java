package com.example.timmo_songjas.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MemberAddData {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("field")
    @Expose
    private String field;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("link")
    @Expose
    private Object link;
    @SerializedName("open")
    @Expose
    private Boolean open;
    @SerializedName("MemberPositions")
    @Expose
    private List<MemberPositions> memberPositions = null;
    @SerializedName("MemberActivities")
    @Expose
    private List<MemberActivities> memberActivities = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Object getLink() {
        return link;
    }

    public void setLink(Object link) {
        this.link = link;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public List<MemberPositions> getMemberPositions() {
        return memberPositions;
    }

    public void setMemberPositions(List<MemberPositions> memberPositions) {
        this.memberPositions = memberPositions;
    }

    public List<MemberActivities> getMemberActivities() {
        return memberActivities;
    }

    public void setMemberActivities(List<MemberActivities> memberActivities) {
        this.memberActivities = memberActivities;
    }

    public MemberAddData(String title, String type, String field, String content, Object link, Boolean open, List<MemberPositions> memberPositions, List<MemberActivities> memberActivities) {
        this.title = title;
        this.type = type;
        this.field = field;
        this.content = content;
        this.link = link;
        this.open = open;
        this.memberPositions = memberPositions;
        this.memberActivities = memberActivities;
    }
}
