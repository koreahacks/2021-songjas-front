package com.example.timmo_songjas.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//이력서 작성시 사용됨
//CreateTimgleData 하위 data
public class MemberPositions {

    @SerializedName("position")
    @Expose
    private String position;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public MemberPositions(String position) {
        this.position = position;
    }
}