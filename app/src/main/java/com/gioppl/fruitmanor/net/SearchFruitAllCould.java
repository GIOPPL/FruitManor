package com.gioppl.fruitmanor.net;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.gioppl.fruitmanor.bean.NetFruitBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SearchFruitAllCould {
    private NetData mCircleData;
    private Context mContext;
    private ArrayList<NetFruitBean> beanList;

    public SearchFruitAllCould(Context mContext, NetData mCircleData){
        this.mContext=mContext;
        this.mCircleData=mCircleData;
    }

    public  void getNetDate(){
        String cql="select * from Fruit";
        AVQuery.doCloudQueryInBackground(cql, new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (e!=null){
                    Log.i("Error",e.getMessage()+"--"+e.getCode());
                }else {
                    Log.i("Success"+this.getClass().getName(),"获取首页特价商品成功");
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

    private ArrayList<NetFruitBean> FormatNetDataBean(String S_circle) {
        ArrayList<NetFruitBean> list;
        Type listType = new TypeToken<List<NetFruitBean>>() {}.getType();
        Gson gson=new Gson();
        list=gson.fromJson(S_circle, listType);
        return list;
    }

    public interface NetData{
        void getData(ArrayList<NetFruitBean> beanList);
    }
    private Handler mHandle=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.arg1==0x9){
                mCircleData.getData((ArrayList<NetFruitBean>) msg.obj);
            }
            return false;
        }
    });
}
