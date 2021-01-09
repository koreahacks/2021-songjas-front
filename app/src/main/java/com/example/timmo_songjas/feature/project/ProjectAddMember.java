package com.example.timmo_songjas.feature.project;

public class ProjectAddMember {
    String imageUrl;
    String nickname;

    public ProjectAddMember(String imageUrl, String nickname) {
        this.imageUrl = imageUrl;
        this.nickname = nickname;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
