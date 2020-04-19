package com.gioppl.fruitmanor.net;

import android.content.Context;
import android.util.Log;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.gioppl.fruitmanor.bean.FruitSortBean;
import com.gioppl.fruitmanor.view.activity.BaseActivity;

import java.util.ArrayList;

public class DeleteOrderFormCloud {//删除在云端订单中的商品
    private Context mContext;
    private ArrayList<FruitSortBean> beanList;

    public DeleteOrderFormCloud(Context mContext, ArrayList<String> objectIdList){
        this.mContext=mContext;
        for (String s:objectIdList)
            deleteOrderForm(s);
    }

    public void deleteOrderForm(String objectId){
        String sql="delete from OrderForm where objectId='"+objectId+"'";
        BaseActivity.Strawberry(mContext,sql);
        AVQuery.doCloudQueryInBackground(sql, new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (e!=null){
                    Log.i("Error","删除订单中数据失败："+e.getMessage()+"--"+e.getCode());
                }else {
                    Log.i("Success"+this.getClass().getName(),"删除订单中数据成功");
                }
            }
        });
    }
}
