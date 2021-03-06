package com.gioppl.fruitmanor.net;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.gioppl.fruitmanor.bean.OrderFormBean;
import com.gioppl.fruitmanor.tool.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SearchAllOderFormCould {
    private NetData mCircleData;
    private Context mContext;
    private ArrayList<OrderFormBean> beanList;

    public SearchAllOderFormCould(Context mContext,int mode, NetData mCircleData){
        this.mContext=mContext;
        this.mCircleData=mCircleData;
        getNetDate(mode);
    }

    public  void getNetDate(int mode){
        String user = (String) SharedPreferencesUtils.getInstance().getData("phoneNumber", "17695564750");
        final String cql="select * from OrderForm where phone = '"+user+"' and mode = "+mode;
        AVQuery.doCloudQueryInBackground(cql, new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (e!=null){
                    Log.i("Error","获取订单失败："+cql+"，"+e.getMessage()+"--"+e.getCode());
                }else {
                    Log.i("Success"+this.getClass().getName(),"获取订单信息成功");
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

    private ArrayList<OrderFormBean> FormatNetDataBean(String S_circle) {
        ArrayList<OrderFormBean> list;
        Type listType = new TypeToken<List<OrderFormBean>>() {}.getType();
        Gson gson=new Gson();
        list=gson.fromJson(S_circle, listType);
        return list;
    }

    public interface NetData{
        void getData(ArrayList<OrderFormBean> beanList);
    }
    private Handler mHandle=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.arg1==0x9){
                mCircleData.getData((ArrayList<OrderFormBean>) msg.obj);
            }
            return false;
        }
    });
}
