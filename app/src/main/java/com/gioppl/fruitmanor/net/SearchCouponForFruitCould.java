package com.gioppl.fruitmanor.net;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.gioppl.fruitmanor.MyApplication;
import com.gioppl.fruitmanor.bean.CouponCouldBean;
import com.gioppl.fruitmanor.sql.MyDbHelper;
import com.gioppl.fruitmanor.tool.SharedPreferencesUtils;
import com.gioppl.fruitmanor.view.activity.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SearchCouponForFruitCould {
    private Context mContext;
    private MyDbHelper mDbHelper;
    private CouponsBack couponsBack;
    public SearchCouponForFruitCould(Context mContext,String goods_id,CouponsBack couponsBack){
        this.mContext=mContext;
        this.couponsBack=couponsBack;
        mDbHelper= MyApplication.getInstance().getDbHelper();
        getData(goods_id);
    }
    public void getData(String goods_id){
        String cql="select * from Coupon where goods_id ='"+goods_id+"' and user = '0'";
        AVQuery.doCloudQueryInBackground(cql, new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (e!=null){
                    BaseActivity.PineApple(this,"获取单个商品优惠券信息失败："+e.getMessage()+"--"+e.getCode());
                }else {
                    SQLiteDatabase db =mDbHelper.getWritableDatabase();
                    BaseActivity.Strawberry(this,"获取单个商品优惠券信息成功");
                    String s=avCloudQueryResult.getResults().toString();
                    ArrayList<CouponCouldBean> beanList=formatCouponDatas(s);
                    SharedPreferencesUtils.getInstance().saveData("couponNum",beanList.size());
                    couponsBack.couponsBack(beanList);
                }
            }
        });
    }

    private ArrayList<CouponCouldBean> formatCouponDatas(String s) {
        Type listType = new TypeToken<List<CouponCouldBean>>() {}.getType();
        Gson gson=new Gson();
        return gson.fromJson(s, listType);
    }
    public interface CouponsBack{
        void couponsBack(ArrayList<CouponCouldBean> coupons);
    }
}
