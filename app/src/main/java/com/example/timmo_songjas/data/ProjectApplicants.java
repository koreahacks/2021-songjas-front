package com.example.timmo_songjas.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProjectApplicants {
    @SerializedName("id")
    @Expose
    private Integer id;
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
    @SerializedName("title")
    @Expose
    private String title;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLimitUniv() {
        return limitUniv;
    }

    public void setLimitUniv(String limitUniv) {
        this.limitUniv = limitUniv;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public List<ProjectPositions> getProjectPositions() {
        return projectPositions;
    }

    public void setProjectPositions(List<ProjectPositions> projectPositions) {
        this.projectPositions = projectPositions;
    }
}
