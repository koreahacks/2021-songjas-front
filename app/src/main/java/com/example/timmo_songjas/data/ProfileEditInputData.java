package com.example.timmo_songjas.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileEditInputData {
    @SerializedName("largeAddress")
    @Expose
    private String largeAddress;
    @SerializedName("smallAddress")
    @Expose
    private String smallAddress;
    @SerializedName("univ")
    @Expose
    private String univ;
    @SerializedName("major")
    @Expose
    private String major;
    @SerializedName("grade")
    @Expose
    private Integer grade;
    @SerializedName("img")
    @Expose
    private Object img;
    @SerializedName("morning")
    @Expose
    private Boolean morning;
    @SerializedName("night")
    @Expose
    private Boolean night;
    @SerializedName("dawn")
    @Expose
    private Boolean dawn;
    @SerializedName("plan")
    @Expose
    private Boolean plan;
    @SerializedName("cramming")
    @Expose
    private Boolean cramming;
    @SerializedName("leader")
    @Expose
    private Boolean leader;
    @SerializedName("follower")
    @Expose
    private Boolean follower;
    @SerializedName("challenge")
    @Expose
    private Boolean challenge;
    @SerializedName("realistic")
    @Expose
    private Boolean realistic;

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

    public String getUniv() {
        return univ;
    }

    public void setUniv(String univ) {
        this.univ = univ;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Object getImg() {
        return img;
    }

    public void setImg(Object img) {
        this.img = img;
    }

    public Boolean getMorning() {
        return morning;
    }

    public void setMorning(Boolean morning) {
        this.morning = morning;
    }

    public Boolean getNight() {
        return night;
    }

    public void setNight(Boolean night) {
        this.night = night;
    }

    public Boolean getDawn() {
        return dawn;
    }

    public void setDawn(Boolean dawn) {
        this.dawn = dawn;
    }

    public Boolean getPlan() {
        return plan;
    }

    public void setPlan(Boolean plan) {
        this.plan = plan;
    }

    public Boolean getCramming() {
        return cramming;
    }

    public void setCramming(Boolean cramming) {
        this.cramming = cramming;
    }

    public Boolean getLeader() {
        return leader;
    }

    public void setLeader(Boolean leader) {
        this.leader = leader;
    }

    public Boolean getFollower() {
        return follower;
    }

    public void setFollower(Boolean follower) {
        this.follower = follower;
    }

    public Boolean getChallenge() {
        return challenge;
    }

    public void setChallenge(Boolean challenge) {
        this.challenge = challenge;
    }

    public Boolean getRealistic() {
        return realistic;
    }

    public void setRealistic(Boolean realistic) {
        this.realistic = realistic;
    }

    public ProfileEditInputData(String largeAddress, String smallAddress, String univ, String major, Integer grade, Object img, Boolean morning, Boolean night, Boolean dawn, Boolean plan, Boolean cramming, Boolean leader, Boolean follower, Boolean challenge, Boolean realistic) {
        this.largeAddress = largeAddress;
        this.smallAddress = smallAddress;
        this.univ = univ;
        this.major = major;
        this.grade = grade;
        this.img = img;
        this.morning = morning;
        this.night = night;
        this.dawn = dawn;
        this.plan = plan;
        this.cramming = cramming;
        this.leader = leader;
        this.follower = follower;
        this.challenge = challenge;
        this.realistic = realistic;
    }
}
