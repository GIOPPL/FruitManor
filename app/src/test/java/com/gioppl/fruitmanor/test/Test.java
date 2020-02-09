package com.gioppl.fruitmanor.test;

import android.util.Log;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;

public class Test {
    @org.junit.Test
    void update() {
        String cql="select * from ShopCart when id =";
        AVQuery.doCloudQueryInBackground(cql, new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (e!=null){
                    Log.i("Error","获取购物车失败："+e.getMessage()+"--"+e.getCode());
                }else {
                    Log.i("Success"+this.getClass().getName(),"获取购物车成功");
                    String s=avCloudQueryResult.getResults().toString();
                }
            }
        });
    }
}
