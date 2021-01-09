package com.example.timmo_songjas.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectMembers {
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("MemberId")
    @Expose
    private Integer memberId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public ProjectMembers(Integer userId, Integer memberId) {
        this.userId = userId;
        this.memberId = memberId;
    }
}
