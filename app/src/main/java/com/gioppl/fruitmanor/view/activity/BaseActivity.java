package com.gioppl.fruitmanor.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.gioppl.fruitmanor.R;
import com.gioppl.fruitmanor.broadcast.MainBroadcastReceiver;
import com.gioppl.fruitmanor.tool.CocoDialog;
import com.gioppl.fruitmanor.tool.NetUtil;
import com.gyf.barlibrary.ImmersionBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.gioppl.fruitmanor.broadcast.MainBroadcastReceiver.BROADCAST_ACTION_LOGIN_STATUS;
import static com.gioppl.fruitmanor.broadcast.MainBroadcastReceiver.BROADCAST_ACTION_NET_STATUS;

public abstract class BaseActivity extends AppCompatActivity implements MainBroadcastReceiver.NetChangeListener {
    public MainBroadcastReceiver.NetChangeListener listener;
    private MainBroadcastReceiver mainBroadcastReceiver;
    private IntentFilter filter;
    private CocoDialog cocoDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).init();
        mainBroadcastReceiver = new MainBroadcastReceiver();
//        listener = this;
        mainBroadcastReceiver.setListener(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//全部禁止横屏
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//Android 7.0以上需要动态注册
            cocoDialog = new CocoDialog(this);
            filter = new IntentFilter();
            filter.addAction(BROADCAST_ACTION_NET_STATUS);
            filter.addAction(BROADCAST_ACTION_LOGIN_STATUS);
            registerReceiver(mainBroadcastReceiver, filter);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mainBroadcastReceiver);
    }

    @Override
    public void onChangeListener(MainBroadcastReceiver.BroadCastClassify broadCastClassify, int statusCode,Object msg) {
        if (broadCastClassify == MainBroadcastReceiver.BroadCastClassify.NET_WORK) {
            if (statusCode == NetUtil.NETWORK_NONE) {
                cocoDialog.setMessage("没有网络连接..")
                        .setImageResId(R.mipmap.ic_kiwi)
                        .setTitle("果果提示")
                        .setSingle(false)
                        .setText("设置网络", "取消")
                        .setOnClickBottomListener(new CocoDialog.OnClickBottomListener() {
                            @Override
                            public void onPositiveClick() {
                                Intent intent = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
                                startActivity(intent);
                            }

                            @Override
                            public void onNegtiveClick() {
                                cocoDialog.dismiss();
                            }
                        }).show();
            } else {
                cocoDialog.dismiss();
            }
        }
        receiveBroadCast(broadCastClassify, statusCode,msg);

    }

    abstract void receiveBroadCast(MainBroadcastReceiver.BroadCastClassify broadCastClassify, int statusCode,Object msg);


    public static void Strawberry(Object object, String tag, String massage) {//草莓🍓
        Log.i(object.getClass().getName() + "- -" + tag, massage);
    }

    public static void Strawberry(Object object, String massage) {//草莓🍓
        Log.i(object.getClass().getName(), massage);
    }

    public void strawberry(Object object, String tag, String massage) {
        Log.i(object.getClass().getName() + "- -" + tag, massage);
    }

    public void strawberry(Object object, String massage) {
        Log.i(object.getClass().getName(), massage);
    }

    public static void Mango(Context context, String mag) {
        Toast.makeText(context, mag, Toast.LENGTH_SHORT).show();
    }

    public static void mango(Context context, String mag) {
        Toast.makeText(context, mag, Toast.LENGTH_SHORT).show();
    }
}
