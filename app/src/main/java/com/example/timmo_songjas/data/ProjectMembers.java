package com.example.timmo_songjas.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectMembers {
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("MemberId")
    @Expose
    private Object memberId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Object getMemberId() {
        return memberId;
    }

    public void setMemberId(Object memberId) {
        this.memberId = memberId;
    }

}
