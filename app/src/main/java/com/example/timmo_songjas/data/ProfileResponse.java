package com.example.timmo_songjas.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfileResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Users")
    @Expose
    private MemberDetailResponse.Users users;
    @SerializedName("ProjectApplicants")
    @Expose
    private List<ProjectDetailResponse.ProjectApplicants> projectApplicants = null;
    @SerializedName("Projects")
    @Expose
    private List<ProjectDetailResponse.Projects> projects = null;
    @SerializedName("Members")
    @Expose
    private List<MemberDetailResponse.Members> members = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MemberDetailResponse.Users getUsers() {
        return users;
    }

    public void setUsers(MemberDetailResponse.Users users) {
        this.users = users;
    }

    public List<ProjectDetailResponse.ProjectApplicants> getProjectApplicants() {
        return projectApplicants;
    }

    public void setProjectApplicants(List<ProjectDetailResponse.ProjectApplicants> projectApplicants) {
        this.projectApplicants = projectApplicants;
    }

    public List<ProjectDetailResponse.Projects> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectDetailResponse.Projects> projects) {
        this.projects = projects;
    }

    public List<MemberDetailResponse.Members> getMembers() {
        return members;
    }

    public void setMembers(List<MemberDetailResponse.Members> members) {
        this.members = members;
    }
}
