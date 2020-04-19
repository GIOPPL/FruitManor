package com.gioppl.fruitmanor.net;

import android.content.Context;
import android.util.Log;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.gioppl.fruitmanor.bean.FruitSortBean;

import java.util.ArrayList;

//修改用户信息
public class UpdateUserInfoCloud {
    private Context mContext;
    private ArrayList<FruitSortBean> beanList;

    public UpdateUserInfoCloud(Context mContext, String objectId, String nickname, String address, String password) {
        this.mContext = mContext;
        update(objectId, nickname, address, password);
    }

    public void update(String objectId, String nickname, String address, String password) {
        String sql = "update UserInfor set nickName = '" + nickname + "', address ='" + address + "', password ='" + password + "' where objectId='" + objectId + "'";
        AVQuery.doCloudQueryInBackground(sql, new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (e != null) {
                    Log.i("Error", "更新用户信息失败：" + e.getMessage() + "--" + e.getCode());
                } else {
                    Log.i("Success" + this.getClass().getName(), "更新用户信息成功");
                }
            }
        });
    }
}
