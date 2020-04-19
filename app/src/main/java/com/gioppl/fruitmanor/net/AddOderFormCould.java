package com.gioppl.fruitmanor.net;

import android.content.Context;
import android.util.Log;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.gioppl.fruitmanor.bean.OrderFormBean;
import com.gioppl.fruitmanor.tool.SharedPreferencesUtils;
import com.gioppl.fruitmanor.view.activity.BaseActivity;

import java.util.ArrayList;
//添加订单到云端
public class AddOderFormCould {
    private Context mContext;
    private ArrayList<OrderFormBean> beanList;

    public AddOderFormCould(Context mContext,String fruitId,String imageUrl,int mode,float price,String title){
        this.mContext=mContext;
        addNetDate(fruitId,imageUrl,mode,price,title);
    }

    public  void addNetDate(String fruitId,String imageUrl,int mode,float price,String title){
        String user = (String) SharedPreferencesUtils.getInstance().getData("phoneNumber", "17695564750");
        String address= (String) SharedPreferencesUtils.getInstance().getData("address","");
        String cql="insert into OrderForm (fruitId,imageUrl,mode,price,title,phone,address) values ('"+fruitId+"','"+imageUrl+"',"+mode+","+price+",'"+title+"','"+user+"','"+address+"')";
        BaseActivity.Strawberry(this,cql);
        AVQuery.doCloudQueryInBackground(cql, new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (e!=null){
                    Log.i("Error","添加订单到云端："+e.getMessage()+"--"+e.getCode());
                }else {
                    Log.i("Success"+this.getClass().getName(),"获取添加订单到云端成功");
                }
            }
        });
    }
}
