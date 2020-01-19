package com.gioppl.fruitmanor.bean;

import com.google.gson.annotations.SerializedName;

public class NetFruitBean {
    @SerializedName("@type")
    private String _$Type58; // FIXME check this code
    private String objectId;
    private String updatedAt;
    private String createdAt;
    private String className;
    private ServerDataBean serverData;

    public String get_$Type58() {
        return _$Type58;
    }

    public void set_$Type58(String _$Type58) {
        this._$Type58 = _$Type58;
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
        private String _$Type82; // FIXME check this code
        private String arriveTime;
        private float price;
        private String imageUrl;
        private String subtitle;
        private String discount;
        private String title;

        public String get_$Type82() {
            return _$Type82;
        }

        public void set_$Type82(String _$Type82) {
            this._$Type82 = _$Type82;
        }

        public String getArriveTime() {
            return arriveTime;
        }

        public void setArriveTime(String arriveTime) {
            this.arriveTime = arriveTime;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
