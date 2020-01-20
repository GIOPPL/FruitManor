package com.gioppl.fruitmanor.bean;

import com.google.gson.annotations.SerializedName;

public class NetFruitBean {

    @SerializedName("@type")
    private String _$Type141; // FIXME check this code
    private String objectId;
    private String updatedAt;
    private String createdAt;
    private String className;
    private ServerDataBean serverData;

    public String get_$Type141() {
        return _$Type141;
    }

    public void set_$Type141(String _$Type141) {
        this._$Type141 = _$Type141;
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
        private String _$Type95; // FIXME check this code
        private String arriveTime;
        private int classify;
        private float price;
        private String imageUrl;
        private String subtitle;
        private String discount;
        private int id;
        private String title;
        private int totalSale;

        public String get_$Type95() {
            return _$Type95;
        }

        public void set_$Type95(String _$Type95) {
            this._$Type95 = _$Type95;
        }

        public String getArriveTime() {
            return arriveTime;
        }

        public void setArriveTime(String arriveTime) {
            this.arriveTime = arriveTime;
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

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getTotalSale() {
            return totalSale;
        }

        public void setTotalSale(int totalSale) {
            this.totalSale = totalSale;
        }
    }
}
