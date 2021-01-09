package com.example.timmo_songjas.data;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimgleListResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Data> data = null;

    public Integer getStatus() {
        return status;
    }


    public Boolean getSuccess() {
        return success;
    }


    public String getMessage() {
        return message;
    }


    public List<Data> getData() {
        return data;
    }


}