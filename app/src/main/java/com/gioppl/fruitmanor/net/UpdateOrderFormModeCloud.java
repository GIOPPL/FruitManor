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

public class UpdateOrderFormModeCloud {//更新云端订单的模式//mode 0为未支付，1为已支付未发货，2为在配送中，3为完成的订单
    private Context mContext;
    private ArrayList<FruitSortBean> beanList;

    public UpdateOrderFormModeCloud(Context mContext,int mode, ArrayList<String> objectIdList){
        this.mContext=mContext;
        for (String s:objectIdList)
            updateOrderForm(s,mode);
    }

    public void updateOrderForm(String objectId,int mode){
        String sql="update OrderForm set mode = "+mode+" where objectId='"+objectId+"'";
        BaseActivity.Strawberry(mContext,sql);
        AVQuery.doCloudQueryInBackground(sql, new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (e!=null){
                    Log.i("Error","更新云端订单的模式失败："+e.getMessage()+"--"+e.getCode());
                }else {
                    Log.i("Success"+this.getClass().getName(),"更新云端订单的模式成功");
                }
            }
        });
    }
}
