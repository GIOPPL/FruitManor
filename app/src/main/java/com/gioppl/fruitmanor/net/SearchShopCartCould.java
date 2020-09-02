package com.gioppl.fruitmanor.net;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.gioppl.fruitmanor.MyApplication;
import com.gioppl.fruitmanor.bean.NetFruitBean;
import com.gioppl.fruitmanor.bean.ShopCartVagueBean;
import com.gioppl.fruitmanor.sql.MyDbHelper;
import com.gioppl.fruitmanor.sql.Table;
import com.gioppl.fruitmanor.tool.SharedPreferencesUtils;
import com.gioppl.fruitmanor.view.activity.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SearchShopCartCould {
    private Context mContext;
    private ArrayList<ShopCartVagueBean> beanList;
    private ArrayList<NetFruitBean> describeList = new ArrayList<>();
    private MyDbHelper mDbHelper;
    private SQLiteDatabase db;
    private ShopCartInfoBack shopCartInfoBack;
    private ShopCartInfoBackAboutData shopCartInfoBackAboutData;
    private int updateNum=0;
    public SearchShopCartCould(Context mContext,ShopCartInfoBack shopCartInfoBack){
        this.mContext=mContext;
        mDbHelper= MyApplication.getInstance().getDbHelper();
        this.shopCartInfoBack=shopCartInfoBack;
        getVagueDate();
    }

    public SearchShopCartCould(Context mContext, ShopCartInfoBackAboutData shopCartInfoBackAboutData) {
        this.mContext = mContext;
        mDbHelper = MyApplication.getInstance().getDbHelper();
        this.shopCartInfoBackAboutData = shopCartInfoBackAboutData;
        getVagueDate();
    }


    public void getVagueDate(){
        String user= (String) SharedPreferencesUtils.getInstance().getData("phoneNumber","");
        String cql="select * from ShopCart where user ='"+user+"'";
        AVQuery.doCloudQueryInBackground(cql, new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (e!=null){
                    Log.i("Error","获取购物车基本信息失败："+e.getMessage()+"--"+e.getCode());
                    shopCartInfoBack.status(false);
                }else {
                    Log.i("Success"+this.getClass().getName(),"获取购物车基本信息成功");
                    String s=avCloudQueryResult.getResults().toString();
                    beanList=formatVagueNetDataBean(s);
                    clearDb(db);
                    int redPointNum = 0;
                    updateNum=beanList.size();
                    if(beanList.size()==0){
                        if (shopCartInfoBackAboutData != null)
                            shopCartInfoBackAboutData.shopCartInfoBackAboutData(describeList);
                    }
                    for (ShopCartVagueBean bean:beanList){
                        getDescriptionDate(bean.getServerData().getFruit_id(),bean.getObjectId());
                        redPointNum++;
                    }
                    SharedPreferencesUtils.getInstance().saveData("redPointNum", redPointNum);
                }
            }
        });
    }

    public void getDescriptionDate(String id, final String shopId){
        String cql="select * from Fruit where objectId ='"+id+"'";
        AVQuery.doCloudQueryInBackground(cql, new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (e!=null){
                    BaseActivity.Strawberry(this,"Error:获取购物车细节信息失败："+e.getMessage()+"--"+e.getCode());
                }else {
                    BaseActivity.Strawberry(this,"Success:获取购物车细节信息成功");
                    String s=avCloudQueryResult.getResults().toString();
                    NetFruitBean bean=formatDescriptionNetDataBean(s);
                    describeList.add(bean);
                    bean.getServerData().setShopId(shopId);
                    saveToDb(bean,shopId);
                    updateNum--;
                    if (shopCartInfoBackAboutData != null&&updateNum==0)
                        shopCartInfoBackAboutData.shopCartInfoBackAboutData(describeList);
                }
            }
        });
    }
    private void saveToDb(NetFruitBean bean,String shopId){
        NetFruitBean.ServerDataBean data=bean.getServerData();
        db = mDbHelper.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(Table.ShopCartTable.SHOP_ID,shopId);
        values.put(Table.ShopCartTable.GOODS_ID,bean.getObjectId());
        values.put(Table.ShopCartTable.CLASSIFY,data.getClassify());
        values.put(Table.ShopCartTable.PRICE,data.getPrice());
        values.put(Table.ShopCartTable.IMAGE_URL,data.getImageUrl());
        values.put(Table.ShopCartTable.SUBTITLE,data.getSubtitle());
        values.put(Table.ShopCartTable.DISCOUNT,data.getDiscount());
        values.put(Table.ShopCartTable.TITLE,data.getTitle());
        values.put(Table.ShopCartTable.TOTAL_SALE,data.getTotalSale());
        db.replace(Table.ShopCartTable.TABLE_NAME, null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        if (shopCartInfoBack != null) {
            shopCartInfoBack.status(true);
        }

    }
    private void clearDb(SQLiteDatabase db){
        db = mDbHelper.getWritableDatabase();
        String sql="delete from "+Table.ShopCartTable.TABLE_NAME;
        db.execSQL(sql);
    }

    private ArrayList<ShopCartVagueBean> formatVagueNetDataBean(String s) {
        Type listType = new TypeToken<List<ShopCartVagueBean>>() {}.getType();
        Gson gson=new Gson();
        return gson.fromJson(s, listType);
    }
    private NetFruitBean formatDescriptionNetDataBean(String s) {
        s=s.substring(1,s.length()-1);
        Gson gs = new Gson();
        NetFruitBean bean = gs.fromJson(s, NetFruitBean.class);
        return bean;
    }
    public interface ShopCartInfoBack{
        void status(boolean isDownLoad);
    }

    public interface ShopCartInfoBackAboutData {
        void shopCartInfoBackAboutData(ArrayList<NetFruitBean> beans);
    }
}
