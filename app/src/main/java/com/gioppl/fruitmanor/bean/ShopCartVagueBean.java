package com.gioppl.fruitmanor.bean;

import com.google.gson.annotations.SerializedName;

public class ShopCartVagueBean {
    @SerializedName("@type")
    private String _$Type175; // FIXME check this code
    private String objectId;
    private String updatedAt;
    private String createdAt;
    private String className;
    private ServerDataBean serverData;

    public String get_$Type175() {
        return _$Type175;
    }

    public void set_$Type175(String _$Type175) {
        this._$Type175 = _$Type175;
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
        private String _$Type168; // FIXME check this code
        private String fruit_id;
        private String user;

        public String get_$Type168() {
            return _$Type168;
        }

        public void set_$Type168(String _$Type168) {
            this._$Type168 = _$Type168;
        }

        public String getFruit_id() {
            return fruit_id;
        }

        public void setFruit_id(String fruit_id) {
            this.fruit_id = fruit_id;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }
    }
}
