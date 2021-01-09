package com.example.timmo_songjas.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Users {
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("largeAddress")
    @Expose
    private String largeAddress;
    @SerializedName("smallAddress")
    @Expose
    private String smallAddress;
    @SerializedName("projectApplicantCount")
    @Expose
    private Integer projectApplicantCount;
    @SerializedName("projectCount")
    @Expose
    private Integer projectCount;
    @SerializedName("memberCount")
    @Expose
    private Integer memberCount;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLargeAddress() {
        return largeAddress;
    }

    public void setLargeAddress(String largeAddress) {
        this.largeAddress = largeAddress;
    }

    public String getSmallAddress() {
        return smallAddress;
    }

    public void setSmallAddress(String smallAddress) {
        this.smallAddress = smallAddress;
    }

    public Integer getProjectApplicantCount() {
        return projectApplicantCount;
    }

    public void setProjectApplicantCount(Integer projectApplicantCount) {
        this.projectApplicantCount = projectApplicantCount;
    }

    public Integer getProjectCount() {
        return projectCount;
    }

    public void setProjectCount(Integer projectCount) {
        this.projectCount = projectCount;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }
}
