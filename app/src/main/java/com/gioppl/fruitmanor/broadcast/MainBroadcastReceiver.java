package com.gioppl.fruitmanor.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import com.gioppl.fruitmanor.tool.NetUtil;
import com.gioppl.fruitmanor.view.activity.BaseActivity;

public class MainBroadcastReceiver extends BroadcastReceiver {
    public static final String BROADCAST_ACTION_LOGIN_STATUS = "BROADCAST_ACTION_LOGIN_STATUS";
    public static final String BROADCAST_ACTION_NET_STATUS = "android.net.conn.CONNECTIVITY_CHANGE";

    public static final int STATUS_CODE_0X00=0x00;
    public static final int STATUS_CODE_0X01=0x01;//已经登陆
    public BroadCastClassify broadCastClassify=BroadCastClassify.NET_WORK;

    public enum BroadCastClassify{
        NET_WORK,LOGIN
    }

    private NetChangeListener listener;

    public void setListener(NetChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // 如果相等的话就说明网络状态发生了变化
        String action=intent.getAction();
        switch (action){
            case ConnectivityManager.CONNECTIVITY_ACTION:
                Log.i("网络发生变化", "MainBroadcastReceiver changed");
                int netWorkState = NetUtil.getNetWorkState(context);
                // 当网络发生变化，判断当前网络状态，并通过NetEvent回调当前网络状态
                if (listener != null) {
                    listener.onChangeListener(BroadCastClassify.NET_WORK,netWorkState,null);
                }
                break;
            case BROADCAST_ACTION_LOGIN_STATUS:
                BaseActivity.Strawberry(this,"登陆状态发生变化");
                if (intent.getBooleanExtra("LOGIN_STATUS",false)){
                    listener.onChangeListener(BroadCastClassify.LOGIN,STATUS_CODE_0X01,null);
                }else {
                    listener.onChangeListener(BroadCastClassify.LOGIN,STATUS_CODE_0X00,null);
                }
                break;
        }
    }

    // 自定义接口
    public interface NetChangeListener {
        void onChangeListener(BroadCastClassify broadCastClassify,int statusCode,Object msg);
    }

}