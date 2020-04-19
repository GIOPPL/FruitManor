package com.gioppl.fruitmanor.net;

import android.content.Context;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.gioppl.fruitmanor.bean.FruitSortBean;
import com.gioppl.fruitmanor.view.activity.BaseActivity;

import java.util.ArrayList;

//修改用户余额
public class UpdateUserMoneyCloud {
    private Context mContext;
    private ArrayList<FruitSortBean> beanList;

    public UpdateUserMoneyCloud(Context mContext, String objectId, float money) {
        this.mContext = mContext;
        update(objectId,money);
    }

    public void update(String objectId, float money) {
        String sql = "update UserInfor set money = " + money + " where objectId='" + objectId + "'";
        AVQuery.doCloudQueryInBackground(sql, new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (e != null) {
                    BaseActivity.Strawberry("Error", "更新用户余额失败：" + e.getMessage() + "--" + e.getCode());
                } else {
                    BaseActivity.Strawberry("Success" + this.getClass().getName(), "更新用户余额成功");
                }
            }
        });
    }
}
