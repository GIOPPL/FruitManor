package com.gioppl.fruitmanor.net;

import android.content.Context;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.gioppl.fruitmanor.tool.SharedPreferencesUtils;
import com.gioppl.fruitmanor.view.activity.BaseActivity;

//修改优惠券状态，把优惠券中的user 由0改为用户的手机号码，实现获取优惠券操作
public class UpdateCouponStatusCloud {
    private Context mContext;

    public UpdateCouponStatusCloud(Context mContext, String objectId, int mode) {
        this.mContext = mContext;
        update(objectId, mode);
    }

    //mode 为0则是把获取优惠券，为1则是购买完东西消费优惠券
    public void update(String objectId, int mode) {
        String phoneNum= (String) SharedPreferencesUtils.getInstance().getData("phoneNumber","");
        String sql;
        if (mode==0){
            sql = "update Coupon set user = '" + phoneNum  + "' where objectId='" + objectId + "'";
        }else {
            sql = "update Coupon set user = '-1' where objectId='" + objectId + "'";
        }
        AVQuery.doCloudQueryInBackground(sql, new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (e != null) {
                    BaseActivity.Strawberry("Error", "更新用户信息失败：" + e.getMessage() + "--" + e.getCode());
                } else {
                    BaseActivity.Strawberry("Success" + this.getClass().getName(), "更新用户信息成功");
                }
            }
        });
    }
}
