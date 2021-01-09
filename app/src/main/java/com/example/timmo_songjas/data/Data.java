package com.example.timmo_songjas.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

}
