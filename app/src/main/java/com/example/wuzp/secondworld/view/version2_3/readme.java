package com.example.wuzp.secondworld.view.version2_3;

import com.apkfuns.logutils.LogUtils;

/**
 * Created by wuzp on 2017/4/11.
 */
public class readme {
    private static final String TAG = "WZP";

    // to create this file just to check the different of the version control
    public static void show(){
        LogUtils.e(TAG,"this is a test show");
    }

    public static void show(String msg){
        LogUtils.e(TAG,"this test msg is:" + msg);
    }

    public static void show(String tag,String msg){
         LogUtils.e(tag,msg);
    }
}
