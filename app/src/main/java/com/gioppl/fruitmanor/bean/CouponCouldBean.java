package com.gioppl.fruitmanor.bean;

import com.google.gson.annotations.SerializedName;

/**
 [{ "@type":"com.avos.avoscloud.AVObject","objectId":"5e3c14f2844bb4008e6b0dec","updatedAt":"2020-02-06T13:31:51.633Z","createdAt":"2020-02-06T13:30:26.225Z","className":"Coupon","serverData":{"@type":"java.util.concurrent.ConcurrentHashMap","reduce_money":10,"goods_id":"5e245cdd43c257006f39a829","endTime":new Date(1581004800000),"user":"17695564750"}}]
 */
public class CouponCouldBean {
    @SerializedName("@type")
    private String _$Type5; // FIXME check this code
    private String objectId;
    private String updatedAt;
    private String createdAt;
    private String className;
    private ServerDataBean serverData;

    public String get_$Type5() {
        return _$Type5;
    }

    public void set_$Type5(String _$Type5) {
        this._$Type5 = _$Type5;
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
        private String _$Type32; // FIXME check this code
        private int reduce_money;
        private String goods_id;
        private String endTime;
        private String user;
        private String imageUrl;
        private String title;

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

        public String get_$Type32() {
            return _$Type32;
        }

        public void set_$Type32(String _$Type32) {
            this._$Type32 = _$Type32;
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
}
