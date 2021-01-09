package com.example.timmo_songjas.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MemberDetailResponse {
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
    private Users users;
    @SerializedName("Members")
    @Expose
    private Members members;
    @SerializedName("MemberPositions")
    @Expose
    private List<MemberPositions> memberPositions = null;
    @SerializedName("MemberActivities")
    @Expose
    private List<MemberActivityies> memberActivities = null;

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

    public Users getUsers(){ return users; }

    public Members getMembers(){ return members;}

    public List<MemberPositions> getMemberPositions() {return  memberPositions;}

    public List<MemberActivityies> getMemberActivities() { return memberActivities;}

    public static class Users {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("name")
        @Expose
        private String name;
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

        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
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

    }

    public static class Members{
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("title")
        @Expose
        private String title;
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
        @SerializedName("UserId")
        @Expose
        private Integer userId;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
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

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }
    }

    public static class MemberPositions{
        @SerializedName("position")
        @Expose
        private String position;


        public String getPosition() {
            return position;
        }
        public void setPosition(String position) {
            this.position = position;
        }
    }

    public static class MemberActivityies {
        @SerializedName("content")
        @Expose
        private String content;

        @SerializedName("date")
        @Expose
        private String date;

        public String getContent() {
            return content;
        }
        public void setContent(String content) {
            this.content = content;
        }
        public String getDate() {
            return date;
        }
        public void setDate(){this.date = date;}
    }
}
