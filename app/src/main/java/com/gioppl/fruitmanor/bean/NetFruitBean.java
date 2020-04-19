package com.gioppl.fruitmanor.bean;

import com.google.gson.annotations.SerializedName;

public class NetFruitBean {


    @SerializedName("@type")
    private String _$Type163; // FIXME check this code
    private String objectId;
    private String updatedAt;
    private String createdAt;
    private String className;
    private ServerDataBean serverData;

    public String get_$Type163() {
        return _$Type163;
    }

    public void set_$Type163(String _$Type163) {
        this._$Type163 = _$Type163;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public ServerDataBean getServerData() {
        return serverData;
    }

    public void setServerData(ServerDataBean serverData) {
        this.serverData = serverData;
    }

    public static class ServerDataBean {
        @SerializedName("@type")
        private String _$Type11; // FIXME check this code
        private int classify;
        private String scalage;
        private String goodsImage2;
        private String goodsImage1;
        private String goodsImage3;
        private String goodsFreshDate;
        private String discount;
        private String title;
        private String goodsWeight;
        private String arriveTime;
        private String goodsProducingDate;
        private String goodsSendPattern;
        private float price;
        private String imageUrl;
        private String subtitle;
        private int id;
        private String producingArea;
        private int totalSale;
        private String shopId;//如果这个类是为了购物车，这个就有意义，这个定义的是ShopCart表中的objectId

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String get_$Type11() {
            return _$Type11;
        }

        public void set_$Type11(String _$Type11) {
            this._$Type11 = _$Type11;
        }

        public int getClassify() {
            return classify;
        }

        public void setClassify(int classify) {
            this.classify = classify;
        }

        public String getScalage() {
            return scalage;
        }

        public void setScalage(String scalage) {
            this.scalage = scalage;
        }

        public String getGoodsImage2() {
            return goodsImage2;
        }

        public void setGoodsImage2(String goodsImage2) {
            this.goodsImage2 = goodsImage2;
        }

        public String getGoodsImage1() {
            return goodsImage1;
        }

        public void setGoodsImage1(String goodsImage1) {
            this.goodsImage1 = goodsImage1;
        }

        public String getGoodsImage3() {
            return goodsImage3;
        }

        public void setGoodsImage3(String goodsImage3) {
            this.goodsImage3 = goodsImage3;
        }

        public String getGoodsFreshDate() {
            return goodsFreshDate;
        }

        public void setGoodsFreshDate(String goodsFreshDate) {
            this.goodsFreshDate = goodsFreshDate;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getGoodsWeight() {
            return goodsWeight;
        }

        public void setGoodsWeight(String goodsWeight) {
            this.goodsWeight = goodsWeight;
        }

        public String getArriveTime() {
            return arriveTime;
        }

        public void setArriveTime(String arriveTime) {
            this.arriveTime = arriveTime;
        }

        public String getGoodsProducingDate() {
            return goodsProducingDate;
        }

        public void setGoodsProducingDate(String goodsProducingDate) {
            this.goodsProducingDate = goodsProducingDate;
        }

        public String getGoodsSendPattern() {
            return goodsSendPattern;
        }

        public void setGoodsSendPattern(String goodsSendPattern) {
            this.goodsSendPattern = goodsSendPattern;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProducingArea() {
            return producingArea;
        }

        public void setProducingArea(String producingArea) {
            this.producingArea = producingArea;
        }

        public int getTotalSale() {
            return totalSale;
        }

        public void setTotalSale(int totalSale) {
            this.totalSale = totalSale;
        }
    }
}
