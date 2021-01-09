package com.example.timmo_songjas.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("accessToken")
    @Expose
    private String accessToken;

    @SerializedName("authNum")
    @Expose
    private String authNum;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("room")
    @Expose
    private String room;

    @SerializedName("UserId")
    @Expose
    private Integer userId;

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("img")
    @Expose
    private String img;


    //필터에 필요
    @SerializedName("limitUniv")
    @Expose
    private String limitUniv;
    @SerializedName("largeAddress")
    @Expose
    private String largeAddress;
    @SerializedName("smallAddress")
    @Expose
    private String smallAddress;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("endDate")
    @Expose
    private String endDate;

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("field")
    @Expose
    private String field;
    @SerializedName("ProjectPositions")
    @Expose
    private List<ProjectPositions> projectPositions = null;


    public Integer getId() {
        return id;
    }

    public String getAuthNum() {
        return authNum;
    }

    public String getTitle() {
        return title;
    }

    public String getRoom() {
        return room;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }


    public String getLimitUniv() {
        return limitUniv;
    }



    public String getLargeAddress() {
        return largeAddress;
    }



    public String getSmallAddress() {
        return smallAddress;
    }



    public String getStartDate() {
        return startDate;
    }



    public String getEndDate() {
        return endDate;
    }




    public String getType() {
        return type;
    }



    public String getField() {
        return field;
    }


    public List<ProjectPositions> getProjectPositions() {
        return projectPositions;
    }



}
