package com.example.timmo_songjas.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProjectAddData {

    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("room")
    @Expose
    private String room;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("field")
    @Expose
    private String field;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("link")
    @Expose
    private String link;
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
    @SerializedName("largeAddress")
    @Expose
    private String largeAddress;
    @SerializedName("smallAddress")
    @Expose
    private String smallAddress;
    @SerializedName("limitUniv")
    @Expose
    private Boolean limitUniv;
    @SerializedName("ProjectMembers")
    @Expose
    private List<ProjectMembers> projectMembers = null;
    @SerializedName("ProjectPositions")
    @Expose
    private List<ProjectPositions> projectPositions = null;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public Boolean getLimitUniv() {
        return limitUniv;
    }

    public void setLimitUniv(Boolean limitUniv) {
        this.limitUniv = limitUniv;
    }

    public List<ProjectMembers> getProjectMembers() {
        return projectMembers;
    }

    public void setProjectMembers(List<ProjectMembers> projectMembers) {
        this.projectMembers = projectMembers;
    }

    public List<ProjectPositions> getProjectPositions() {
        return projectPositions;
    }

    public void setProjectPositions(List<ProjectPositions> projectPositions) {
        this.projectPositions = projectPositions;
    }

    public ProjectAddData(String img, String room, String title, String type, String field, String startDate, String endDate, String content, String link, Boolean morning, Boolean night, Boolean dawn, Boolean plan, Boolean cramming, Boolean leader, Boolean follower, Boolean challenge, Boolean realistic, String largeAddress, String smallAddress, Boolean limitUniv, List<ProjectMembers> projectMembers, List<ProjectPositions> projectPositions) {
        this.img = img;
        this.room = room;
        this.title = title;
        this.type = type;
        this.field = field;
        this.startDate = startDate;
        this.endDate = endDate;
        this.content = content;
        this.link = link;
        this.morning = morning;
        this.night = night;
        this.dawn = dawn;
        this.plan = plan;
        this.cramming = cramming;
        this.leader = leader;
        this.follower = follower;
        this.challenge = challenge;
        this.realistic = realistic;
        this.largeAddress = largeAddress;
        this.smallAddress = smallAddress;
        this.limitUniv = limitUniv;
        this.projectMembers = projectMembers;
        this.projectPositions = projectPositions;
    }
}
