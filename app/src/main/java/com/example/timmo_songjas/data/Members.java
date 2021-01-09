package com.example.timmo_songjas.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Members {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("field")
    @Expose
    private String field;
    @SerializedName("MemberPositions")
    @Expose
    private List<MemberPositions> memberPositions = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public List<MemberPositions> getMemberPositions() {
        return memberPositions;
    }

    public void setMemberPositions(List<MemberPositions> memberPositions) {
        this.memberPositions = memberPositions;
    }
}
