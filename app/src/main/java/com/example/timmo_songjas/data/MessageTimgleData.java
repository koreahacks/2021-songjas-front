package com.example.timmo_songjas.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageTimgleData {

    @SerializedName("MemberId")
    @Expose
    private int memberId;
    @SerializedName("ProjectId")
    @Expose
    private int projectId;

    public MessageTimgleData(int memberId, int projectId) {
        this.memberId = memberId;
        this.projectId = projectId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

}