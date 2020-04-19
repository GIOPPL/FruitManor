package com.gioppl.fruitmanor.bean;

public class CouponBean {
    private int reduce_money;
    private String goods_id;
    private String endTime;
    private String user;
    private String imageUrl;
    private String title;
    private String objectId;


    public CouponBean() {
    }

    public CouponBean(int reduce_money, String goods_id, String endTime, String user, String imageUrl, String title) {
        this.reduce_money = reduce_money;
        this.goods_id = goods_id;
        this.endTime = endTime;
        this.user = user;
        this.imageUrl = imageUrl;
        this.title = title;
    }

    public CouponBean(int reduce_money, String goods_id, String endTime, String user, String imageUrl, String title, String objectId) {
        this.reduce_money = reduce_money;
        this.goods_id = goods_id;
        this.endTime = endTime;
        this.user = user;
        this.imageUrl = imageUrl;
        this.title = title;
        this.objectId = objectId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public int getReduce_money() {
        return reduce_money;
    }

    public void setReduce_money(int reduce_money) {
        this.reduce_money = reduce_money;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
