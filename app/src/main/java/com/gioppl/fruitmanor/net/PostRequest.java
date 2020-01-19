package com.gioppl.fruitmanor.net;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by GIOPPL on 2017/12/2.
 */

public class PostRequest {
    private final int SUCCESS=0xffffffff;
    private final int ERROR=0xfffffffe;
    public static final String POST="POST";
    public static final String GET="GET";

    private RequestCallback callback;
    public PostRequest(final HashMap<String, Object> argMap, final String url, final String requestType){
        new Thread(new Runnable() {
            @Override
            public void run() {
                getDate(argMap,url,requestType);
            }
        }).start();

    }
    public PostRequest(final HashMap<String, Object> argMap, final String url, final String requestType, final RequestCallback callback){
        this.callback=callback;
        new Thread(new Runnable() {
            @Override
            public void run() {
                getDate(argMap,url,requestType);
            }
        }).start();

    }

    public void getDate(HashMap<String, Object> argMap, String address, String requestType){

        Message msg=new Message();
        try {
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod(requestType);
            String data= JSON.toJSONString(argMap);
            connection.setRequestProperty("Content-Type","application/json;charset=iso8859-1");
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(data.getBytes("utf-8"));
            int responseCode = connection.getResponseCode();
            if(responseCode ==200){
                //请求成功
                InputStream is = connection.getInputStream();
                BufferedReader br=new BufferedReader(new InputStreamReader(is));
                String line=null;
                StringBuffer sb=new StringBuffer();
                while ((line=br.readLine())!=null){
                    sb.append(line);
                }
                is.close();
                br.close();
                Log.i("成功",sb.toString());
                msg.arg1=SUCCESS;
                msg.obj=sb.toString();
                handler.sendMessage(msg);
            }else {
                msg.arg1=ERROR;
                msg.obj="请求失败";
                handler.sendMessage(msg);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            msg.arg1=ERROR;
            msg.obj=e.getMessage();
            handler.sendMessage(msg);
        } catch (ProtocolException e) {
            e.printStackTrace();
            msg.arg1=ERROR;
            msg.obj=e.getMessage();
            handler.sendMessage(msg);
        } catch (IOException e) {
            e.printStackTrace();
            msg.arg1=ERROR;
            msg.obj=e.getMessage();
            handler.sendMessage(msg);
        }
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1==SUCCESS){
                callback.success((String) msg.obj);
            }else if (msg.arg1==ERROR){
                callback.error((String) msg.obj);
            }
        }
    };

    public interface RequestCallback{
        void success(String back);
        void error(String back);
        void getBeanList(ArrayList<Object> bean);
    }


}
