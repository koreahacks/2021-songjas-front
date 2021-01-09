package com.example.timmo_songjas.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProjectDetailResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;


    @SerializedName("Projects")
    @Expose
    private Projects projects;


    @SerializedName("ProjectPositions")
    @Expose
    private List<ProjectPositions> projectPositions = null;


    @SerializedName("ProjectMembers")
    @Expose
    private List<ProjectMembers> projectMembers = null;
    @SerializedName("ProjectApplicant")
    @Expose
    private List<ProjectApplicants> projectApplicant = null;;
    @SerializedName("button")
    @Expose
    private Boolean button;

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

    public Projects getProjects() {
        return projects;
    }

    public List<ProjectPositions> getProjectPositions() {
        return projectPositions;
    }

    public List<ProjectMembers> getProjectMembers() {
        return projectMembers;
    }

    public List<ProjectApplicants> getProjectApplicant() {
        return projectApplicant;
    }

    public Boolean getButton() {
        return button;
    }

    public void setButton(Boolean button) {
        this.button = button;
    }

    public static class Projects {
        @SerializedName("UserId")
        @Expose
        private Integer userId;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("startDate")
        @Expose
        private String startDate;
        @SerializedName("endDate")
        @Expose
        private String endDate;
        @SerializedName("largeAddress")
        @Expose
        private String largeAddress;
        @SerializedName("smallAddress")
        @Expose
        private String smallAddress;
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
        @SerializedName("limitUniv")
        @Expose
        private Boolean limitUniv;

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

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

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
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

        public Boolean getLimitUniv() {
            return limitUniv;
        }

        public void setLimitUniv(Boolean limitUniv) {
            this.limitUniv = limitUniv;
        }

    }

    public static class ProjectPositions {
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("position")
        @Expose
        private String position;
        @SerializedName("headCount")
        @Expose
        private Integer headCount;
        @SerializedName("ProjectId")
        @Expose
        private Integer projectId;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public Integer getHeadCount() {
            return headCount;
        }

        public void setHeadCount(Integer headCount) {
            this.headCount = headCount;
        }

        public Integer getProjectId() {
            return projectId;
        }

        public void setProjectId(Integer projectId) {
            this.projectId = projectId;
        }
    }

    public static class ProjectMembers {
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("MemberId")
        @Expose
        private Integer memberId;
        @SerializedName("UserId")
        @Expose
        private Integer userId;
        @SerializedName("ProjectId")
        @Expose
        private Integer projectId;
        @SerializedName("User.img")
        @Expose
        private String userImg;
        @SerializedName("User.name")
        @Expose
        private String userName;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getMemberId() {
            return memberId;
        }

        public void setMemberId(Integer memberId) {
            this.memberId = memberId;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getProjectId() {
            return projectId;
        }

        public void setProjectId(Integer projectId) {
            this.projectId = projectId;
        }

        public String getUserImg() {
            return userImg;
        }

        public void setUserImg(String userImg) {
            this.userImg = userImg;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }

    public static class ProjectApplicants {
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("MemberId")
        @Expose
        private Integer memberId;
        @SerializedName("UserId")
        @Expose
        private Integer userId;
        @SerializedName("ProjectId")
        @Expose
        private Integer projectId;
        @SerializedName("User.img")
        @Expose
        private String userImg;
        @SerializedName("User.name")
        @Expose
        private String userName;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getMemberId() {
            return memberId;
        }

        public void setMemberId(Integer memberId) {
            this.memberId = memberId;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getProjectId() {
            return projectId;
        }

        public void setProjectId(Integer projectId) {
            this.projectId = projectId;
        }

        public String getUserImg() {
            return userImg;
        }

        public void setUserImg(String userImg) {
            this.userImg = userImg;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

    }
}
