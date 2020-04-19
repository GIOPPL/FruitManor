package com.gioppl.fruitmanor.bean;

import com.google.gson.annotations.SerializedName;

public class OrderFormBean {
    @SerializedName("@type")
    private String _$Type109; // FIXME check this code
    private String objectId;
    private String updatedAt;
    private String createdAt;
    private String className;
    private ServerDataBean serverData;

    public String get_$Type109() {
        return _$Type109;
    }

    public void set_$Type109(String _$Type109) {
        this._$Type109 = _$Type109;
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
        private String _$Type281; // FIXME check this code
        private float price;
        private String fruitId;
        private String title;
        private String imageUrl;
        private int mode;
        private String shopId;
        private String phone;
        public ServerDataBean() {
        }

        public ServerDataBean(float price, String fruitId, String title, String imageUrl, int mode, String shopId) {
            this.price = price;
            this.fruitId = fruitId;
            this.title = title;
            this.imageUrl = imageUrl;
            this.mode = mode;
            this.shopId = shopId;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public int getMode() {
            return mode;
        }

        public void setMode(int mode) {
            this.mode = mode;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String get_$Type281() {
            return _$Type281;
        }

        public void set_$Type281(String _$Type281) {
            this._$Type281 = _$Type281;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public String getFruitId() {
            return fruitId;
        }

        public void setFruitId(String fruitId) {
            this.fruitId = fruitId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
