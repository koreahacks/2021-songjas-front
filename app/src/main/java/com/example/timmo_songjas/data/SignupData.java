package com.example.timmo_songjas.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignupData {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("pwd")
    @Expose
    private String pwd;
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


    public SignupData(String email, String pwd, String name, String largeAddress, String smallAddress, String univ, String major, Integer grade) {
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.largeAddress = largeAddress;
        this.smallAddress = smallAddress;
        this.univ = univ;
        this.major = major;
        this.grade = grade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
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


}
