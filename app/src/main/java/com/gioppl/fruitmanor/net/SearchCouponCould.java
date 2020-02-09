package com.gioppl.fruitmanor.net;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.gioppl.fruitmanor.MyApplication;
import com.gioppl.fruitmanor.bean.CouponCouldBean;
import com.gioppl.fruitmanor.sql.MyDbHelper;
import com.gioppl.fruitmanor.sql.Table;
import com.gioppl.fruitmanor.tool.SharedPreferencesUtils;
import com.gioppl.fruitmanor.view.activity.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SearchCouponCould {
    private Context mContext;
    private MyDbHelper mDbHelper;
    public SearchCouponCould(Context mContext){
        this.mContext=mContext;
        mDbHelper= MyApplication.getInstance().getDbHelper();
        getData();
    }


    public void getData(){
        String user= (String) SharedPreferencesUtils.getInstance().getData("phoneNumber","");
        String cql="select * from Coupon where user ='"+user+"'";
        AVQuery.doCloudQueryInBackground(cql, new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (e!=null){
                    BaseActivity.PineApple(this,"获取优惠券信息失败："+e.getMessage()+"--"+e.getCode());
                }else {
                    BaseActivity.Strawberry(this,"获取优惠券信息成功");
                    String s=avCloudQueryResult.getResults().toString();
                    ArrayList<CouponCouldBean> beanList=formatCouponDatas(s);
                    saveToDb(beanList);
                }
            }
        });
    }

    private void saveToDb(ArrayList<CouponCouldBean> beanList){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.beginTransaction();
        for (CouponCouldBean bean:beanList){
            CouponCouldBean.ServerDataBean data=bean.getServerData();
            ContentValues values = new ContentValues();
            values.put(Table.CouponTable.GOODS_ID,bean.getObjectId());
            values.put(Table.CouponTable.REDUCE_MONEY,data.getReduce_money());
            values.put(Table.CouponTable.END_TIME,data.getEndTime());
            values.put(Table.CouponTable.TITLE,data.getTitle());
            values.put(Table.CouponTable.IMAGE_URL,data.getImageUrl());
            db.replace(Table.CouponTable.TABLE_NAME, null, values);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();



    }

    private ArrayList<CouponCouldBean> formatCouponDatas(String s) {
        Type listType = new TypeToken<List<CouponCouldBean>>() {}.getType();
        Gson gson=new Gson();
        return gson.fromJson(s, listType);
    }
}
