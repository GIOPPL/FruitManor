package com.gioppl.fruitmanor.net;

import android.content.Context;
import android.util.Log;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.gioppl.fruitmanor.bean.FruitSortBean;

import java.util.ArrayList;

public class AddGoodsToShopCartCloud {
    private Context mContext;
    private ArrayList<FruitSortBean> beanList;

    public AddGoodsToShopCartCloud(Context mContext,String user,String fruit_id){
        this.mContext=mContext;
        addGoods(fruit_id,user);
    }

    public  void addGoods(String fruit_id,String user){
        String cql="insert into ShopCart(user,fruit_id) values ('"+user+"','"+fruit_id+"')";
        AVQuery.doCloudQueryInBackground(cql, new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (e!=null){
                    Log.i("Error","添加商品到购物车失败："+e.getMessage()+"--"+e.getCode());
                }else {
                    Log.i("Success"+this.getClass().getName(),"添加商品到购物车成功");
                }
            }
        });
    }
}
