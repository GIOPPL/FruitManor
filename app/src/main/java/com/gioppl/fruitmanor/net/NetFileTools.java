package com.gioppl.fruitmanor.net;

import android.os.Environment;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.SaveCallback;
import com.gioppl.fruitmanor.view.BaseActivity;

import java.io.FileNotFoundException;

public class NetFileTools {
    private static NetFileTools netFileTools;
    private String filePath;
    private String fileName;
    private AVFile file;

    public static NetFileTools getInstance() {
        if (netFileTools == null) {
            netFileTools = new NetFileTools();
        }
        return netFileTools;
    }
    public void upload(final String filePath){
        try {
            final AVFile file = AVFile.withAbsoluteLocalPath("avatar.jpg", Environment.getExternalStorageDirectory().getAbsoluteFile() + "/GIOPPL/"+filePath+".png");
            file.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e==null){
                        BaseActivity.Strawberry(this.getClass().getName(),"成功上传文件",file.getObjectId());
                    }else {
                        BaseActivity.Strawberry(this.getClass().getName(),"上传文件失败",e.getCode()+":"+e.getMessage());
                    }
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void download(String url){

    }
}
