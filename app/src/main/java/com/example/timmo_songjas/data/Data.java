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



    public Integer getId() {
        return id;
    }

    public String getAuthNum() {
        return authNum;
    }

    public String getAccessToken() {
        return accessToken;
    }


}
