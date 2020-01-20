package com.gioppl.fruitmanor.net;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.gioppl.fruitmanor.bean.FruitSortBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GIOPPL on 2017/4/30.
 */

public class SearchFruitSortCould {
    private NetData mCircleData;
    private Context mContext;
    private ArrayList<FruitSortBean> beanList;

    public SearchFruitSortCould(Context mContext, NetData mCircleData){
        this.mContext=mContext;
        this.mCircleData=mCircleData;
    }

    public  void getNetDate(){
        String cql="select * from FruitClassify";
        AVQuery.doCloudQueryInBackground(cql, new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (e!=null){
                    Log.i("Error","获取商品种类失败："+e.getMessage()+"--"+e.getCode());
                }else {
                    Log.i("Success"+this.getClass().getName(),"获取商品种类成功");
                    String s=avCloudQueryResult.getResults().toString();
                    beanList=FormatNetDataBean(s);
                    Message msg=new Message();
                    msg.arg1=0x9;
                    msg.obj=beanList;
                    mHandle.sendMessage(msg);
                }
            }
        });
    }

    private ArrayList<FruitSortBean> FormatNetDataBean(String S_circle) {
        ArrayList<FruitSortBean> list;
        Type listType = new TypeToken<List<FruitSortBean>>() {}.getType();
        Gson gson=new Gson();
        list=gson.fromJson(S_circle, listType);
        return list;
    }

    public interface NetData{
        void getData(ArrayList<FruitSortBean> beanList);
    }
    private Handler mHandle=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.arg1==0x9){
                mCircleData.getData((ArrayList<FruitSortBean>) msg.obj);
            }
            return false;
        }
    });
}
