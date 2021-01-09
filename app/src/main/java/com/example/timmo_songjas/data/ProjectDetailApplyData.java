package com.example.timmo_songjas.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectDetailApplyData {
    @SerializedName("MemberId")
    @Expose
    private String MemberId;

    @SerializedName("ProjectId")
    @Expose
    private String ProjectId;

    public ProjectDetailApplyData(String MemberId, String ProjectId) {
        this.MemberId = MemberId;
        this.ProjectId = ProjectId;
    }

    public String getMemberId() { return MemberId; }

    public void setMemberId(String MemberId) { this.MemberId = MemberId; }

    public String getProjectId() { return ProjectId; }

    public void setProjectId(String ProjectId) { this.ProjectId = ProjectId; }
}
