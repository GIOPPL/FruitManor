package com.gioppl.fruitmanor;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    private static MyApplication instance;
    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
