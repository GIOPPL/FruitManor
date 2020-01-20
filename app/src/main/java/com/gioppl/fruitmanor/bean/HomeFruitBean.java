package com.gioppl.fruitmanor.bean;

public class HomeFruitBean {
    private String title;
    private String subtitle;
    private float price;
    private String arriveTime;
    private String discount;
    private String imageUrl;

    private int classify;
    private int totalSale;

    public HomeFruitBean(String title, String subtitle, float price, String arriveTime, String discount, String imageUrl, int classify, int totalSale) {
        this.title = title;
        this.subtitle = subtitle;
        this.price = price;
        this.arriveTime = arriveTime;
        this.discount = discount;
        this.imageUrl = imageUrl;
        this.classify = classify;
        this.totalSale = totalSale;
    }

    public int getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(int totalSale) {
        this.totalSale = totalSale;
    }

    public int getClassify() {
        return classify;
    }

    public void setClassify(int classify) {
        this.classify = classify;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    
}
