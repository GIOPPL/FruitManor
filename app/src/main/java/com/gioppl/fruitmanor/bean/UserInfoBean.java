package com.gioppl.fruitmanor.bean;

public class UserInfoBean {
    private String nickName;
    private String phoneNumber;
    private String imageUrl;

    public UserInfoBean(String nickName, String phoneNumber, String imageUrl) {
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.imageUrl = imageUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
