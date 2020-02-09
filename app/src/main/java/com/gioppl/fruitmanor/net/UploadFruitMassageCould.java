package com.gioppl.fruitmanor.net;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.gioppl.fruitmanor.bean.HomeFruitBean;
import com.gioppl.fruitmanor.bean.NetFruitBean;
import com.gioppl.fruitmanor.view.activity.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UploadFruitMassageCould {
    private NetData mCircleData;
    private Context mContext;
    private ArrayList<NetFruitBean> beanList;

    public UploadFruitMassageCould(Context mContext, NetData mCircleData) {
        this.mContext = mContext;
        this.mCircleData = mCircleData;
    }

    public void uploadMassage(HomeFruitBean bean) {
        final AVObject msg = new AVObject("Fruit");
        msg.put("title", bean.getTitle());
        msg.put("subtitle", bean.getSubtitle());
        msg.put("price", bean.getPrice());
        msg.put("discount", bean.getDiscount());
        msg.put("arriveTime", bean.getArriveTime());
        msg.put("imageUrl", bean.getImageUrl());
        msg.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                Message message = new Message();
                if (e == null) {
                    BaseActivity.Strawberry(this.getClass().getName(), "成功上传特价商品", msg.getObjectId());
                    message.arg1 = 0x01;
                    message.obj = "成功上传特价商品";
                } else {
                    BaseActivity.Strawberry(this.getClass().getName(), "上传特价商品失败", e.getCode() + "- -" + e.getMessage());
                    message.arg1 = 0x00;
                    message.obj = "上传特价商品失败";
                }
                mHandle.sendMessage(message);
            }
        });
    }

    private ArrayList<NetFruitBean> FormatNetDataBean(String S_circle) {
        ArrayList<NetFruitBean> list;
        Type listType = new TypeToken<List<NetFruitBean>>() {
        }.getType();
        Gson gson = new Gson();
        list = gson.fromJson(S_circle, listType);
        return list;
    }

    public interface NetData {
        void getData(String msg);
    }

    private Handler mHandle = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            mCircleData.getData((String) msg.obj);
            return false;
        }
    });
}
