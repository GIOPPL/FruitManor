package com.gioppl.fruitmanor.net;

import android.content.Context;
import android.util.Log;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.gioppl.fruitmanor.tool.LoginStatusChangedTools;
import com.gioppl.fruitmanor.view.activity.BaseActivity;

import java.util.ArrayList;

public class DeleteGoodsFromShopCartCloud {//删除在云端购物车中的商品
    private Context mContext;

    public DeleteGoodsFromShopCartCloud(Context mContext, ArrayList<String> objectIdList){
        this.mContext=mContext;
        for (String s:objectIdList)
            deleteGoods(s);
        new LoginStatusChangedTools(mContext);
    }

    public void deleteGoods(String objectId){
        String sql="delete from ShopCart where objectId='"+objectId+"'";
        BaseActivity.Strawberry(mContext,sql);
        AVQuery.doCloudQueryInBackground(sql, new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (e!=null){
                    Log.i("Error","删除购物车中数据失败："+e.getMessage()+"--"+e.getCode());
                }else {
                    Log.i("Success"+this.getClass().getName(),"删除购物车中数据成功");
                }
            }
        });
    }
}
