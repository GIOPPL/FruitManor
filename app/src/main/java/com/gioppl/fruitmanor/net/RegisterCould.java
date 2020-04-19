package com.gioppl.fruitmanor.net;

import android.content.Context;
import android.util.Log;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.gioppl.fruitmanor.bean.OrderFormBean;
import com.gioppl.fruitmanor.view.activity.BaseActivity;

import java.util.ArrayList;

//注册
public class RegisterCould {
    private Context mContext;
    private ArrayList<OrderFormBean> beanList;

    public RegisterCould(Context mContext, String phoneNum, String password){
        this.mContext=mContext;
        addNetDate(phoneNum,password);
    }

    public  void addNetDate(String phoneNum, String password){
        String cql="insert into UserInfor (phoneNumber,password) values ('"+phoneNum+"','"+password+"')";
        BaseActivity.Strawberry(this,cql);
        AVQuery.doCloudQueryInBackground(cql, new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (e!=null){
                    Log.i("Error","注册失败："+e.getMessage()+"--"+e.getCode());
                }else {
                    Log.i("Success"+this.getClass().getName(),"注册成功");
                }
            }
        });
    }
}
