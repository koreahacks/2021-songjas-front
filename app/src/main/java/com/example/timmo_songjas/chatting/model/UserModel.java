package com.example.timmo_songjas.chatting.model;

//채팅 유저
public class UserModel {
    public String userName;
    public String profileImageUrl;
    public String userid; // 서버에서 받아오는 id, firebase의 user.keyvalue값과는 다름
    //string으로 변환한값이다. 그니까 서버에서 받은 id 값을 string으로 변환해서 firebase에 넣어서 그 값 받아온것


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}

