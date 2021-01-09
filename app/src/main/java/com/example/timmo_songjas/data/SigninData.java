package com.example.timmo_songjas.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SigninData {

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("pwd")
    @Expose
    private String pwd;


    public SigninData(String email, String pwd) {
        this.email = email;
        this.pwd = pwd;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPwd() { return pwd; }

    public void setPwd(String pwd) { this.pwd = pwd; }

}