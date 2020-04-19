package com.gioppl.fruitmanor.net;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.gioppl.fruitmanor.bean.UserInfoBean;
import com.gioppl.fruitmanor.tool.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserLoginCould {
    private NetData mCircleData;
    private Context mContext;
    private ArrayList<UserInfoBean> beanList;
    private UserInfoBack userInfoBack;

    public UserLoginCould(Context mContext, NetData mCircleData, String user, String password) {
        this.mContext = mContext;
        this.mCircleData = mCircleData;
        getNetDate(user, password);
    }

    public UserLoginCould(Context mContext, String user, String password, UserInfoBack userInfoBack) {
        this.mContext = mContext;
        this.userInfoBack = userInfoBack;
        getNetDate(user, password);
    }

    public void getNetDate(String user, String password) {
        String cql = "select * from UserInfor where phoneNumber='" + user + "' and password='" + password + "'";
        AVQuery.doCloudQueryInBackground(cql, new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (e != null) {
                    Log.i("Error,登陆失败：", e.getMessage() + "--" + e.getCode());
                } else {
                    String s = avCloudQueryResult.getResults().toString();
                    Log.i("Success", "登陆成功：" + s);

                    beanList = FormatNetDataBean(s);
                    Message msg = new Message();
                    msg.arg1 = 0x9;
                    msg.obj = beanList;
                    mHandle.sendMessage(msg);
                }
            }
        });
    }

    private ArrayList<UserInfoBean> FormatNetDataBean(String s) {
        ArrayList<UserInfoBean> list;
        Type listType = new TypeToken<List<UserInfoBean>>() {
        }.getType();
        Gson gson = new Gson();
        list = gson.fromJson(s, listType);
        return list;
    }

    public interface NetData {
        void getData(UserInfoBean beanList);
    }

    private Handler mHandle = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.arg1 == 0x9) {
                ArrayList<UserInfoBean> beans = ((ArrayList<UserInfoBean>) msg.obj);
                if (beans == null || beans.size() == 0) {
                    if (mCircleData != null)
                        mCircleData.getData(null);
                } else {
                    updateLocalUserInfoData(beans.get(0).getServerData(), beans.get(0).getObjectId());
                    if (beans.get(0) != null && mCircleData != null)
                        mCircleData.getData(beans.get(0));
                }

            }
            return false;
        }
    });

    private void updateLocalUserInfoData(UserInfoBean.ServerDataBean user, String objectId) {
        SharedPreferencesUtils.getInstance().saveData("nickName", user.getNickName());
        SharedPreferencesUtils.getInstance().saveData("imageUrl", user.getImageUrl());
        SharedPreferencesUtils.getInstance().saveData("objectId", objectId);
        SharedPreferencesUtils.getInstance().saveData("phoneNumber", user.getPhoneNumber());
        SharedPreferencesUtils.getInstance().saveData("loginStatus", true);
        SharedPreferencesUtils.getInstance().saveData("money", user.getMoney());
//        SharedPreferencesUtils.getInstance().saveData("couponNum", user.getCouponNum());
        SharedPreferencesUtils.getInstance().saveData("address", user.getAddress());
        SharedPreferencesUtils.getInstance().saveData("password", user.getPassword());
        if (userInfoBack != null)
            userInfoBack.statusUserInfo(true);
    }

    public interface UserInfoBack {
        void statusUserInfo(boolean isDownLoad);
    }
}
