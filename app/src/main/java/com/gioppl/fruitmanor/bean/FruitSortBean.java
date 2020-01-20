package com.gioppl.fruitmanor.bean;

import com.google.gson.annotations.SerializedName;

public class FruitSortBean {

    @SerializedName("@type")
    private String _$Type3; // FIXME check this code
    private String objectId;
    private String updatedAt;
    private String createdAt;
    private String className;
    private ServerDataBean serverData;

    public String get_$Type3() {
        return _$Type3;
    }

    public void set_$Type3(String _$Type3) {
        this._$Type3 = _$Type3;
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
        private String _$Type196; // FIXME check this code
        private int classifyNum;
        private String sort;

        public String get_$Type196() {
            return _$Type196;
        }

        public void set_$Type196(String _$Type196) {
            this._$Type196 = _$Type196;
        }

        public int getClassifyNum() {
            return classifyNum;
        }

        public void setClassifyNum(int classifyNum) {
            this.classifyNum = classifyNum;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }
    }
}
