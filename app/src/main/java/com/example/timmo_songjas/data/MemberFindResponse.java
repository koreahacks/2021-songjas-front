package com.example.timmo_songjas.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MemberFindResponse {
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
    private List<Datum> data = null;

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

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Datum {
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("img")
        @Expose
        private String img;
        @SerializedName("largeAddress")
        @Expose
        private String largeAddress;
        @SerializedName("smallAddress")
        @Expose
        private String smallAddress;
        @SerializedName("Members")
        @Expose
        private List<Member> members = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
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

        public List<Member> getMembers() {
            return members;
        }

        public void setMembers(List<Member> members) {
            this.members = members;
        }


        public class Member {
            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("type")
            @Expose
            private String type;
            @SerializedName("field")
            @Expose
            private String field;
            @SerializedName("MemberPositions")
            @Expose
            private List<MemberPosition> memberPositions = null;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
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

            public List<MemberPosition> getMemberPositions() {
                return memberPositions;
            }

            public void setMemberPositions(List<MemberPosition> memberPositions) {
                this.memberPositions = memberPositions;
            }

            public class MemberPosition {
                @SerializedName("position")
                @Expose
                private String position;

                public String getPosition() {
                    return position;
                }

                public void setPosition(String position) {
                    this.position = position;
                }

            }
        }
    }
}
