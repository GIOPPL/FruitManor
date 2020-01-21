package com.gioppl.fruitmanor;

import android.app.Application;
import android.content.Context;

import com.avos.avoscloud.AVOSCloud;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.gioppl.fruitmanor.tool.SharedPreferencesUtils;

public class MyApplication extends Application {

    private static MyApplication instance;
    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Fresco.initialize(this);
        AVOSCloud.initialize(this,"9AEi5aIplU8mQaf5wzGoRGvc-gzGzoHsz", "LV8ajDJSoymOmV4DEjUgfl5C");
        SharedPreferencesUtils.init(this);
        SharedPreferencesUtils.getInstance().saveData("loginStatus",false);
    }

    public MyApplication getInstance(){
        if (instance==null)
            instance=new MyApplication();
        return instance;
    }
}
