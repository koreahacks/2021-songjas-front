package com.example.timmo_songjas.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ProfileEditResponse {

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
    private com.example.timmo_songjas.data.ProfileEditData data;


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

    public com.example.timmo_songjas.data.ProfileEditData getData() {
        return data;
    }

    public void setData(com.example.timmo_songjas.data.ProfileEditData data) {
        this.data = data;
    }

}