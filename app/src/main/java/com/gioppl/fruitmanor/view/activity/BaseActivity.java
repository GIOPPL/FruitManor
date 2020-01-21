package com.gioppl.fruitmanor.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).init();
    }

    public static void Strawberry(String className,String tag,String massage){
        Log.i(className+"- -"+tag,massage);
    }
    public void strawberry(Object object,String tag,String massage){
        Log.i(object.getClass().getName()+"- -"+tag,massage);
    }
    public void strawberry(Object object,String massage){
        Log.i(object.getClass().getName(),massage);
    }
    public static void Mango(Context context,String mag){
        Toast.makeText(context,mag,Toast.LENGTH_SHORT).show();
    }
    public static void mogo(Context context,String mag){
        Toast.makeText(context,mag,Toast.LENGTH_SHORT).show();
    }
}
