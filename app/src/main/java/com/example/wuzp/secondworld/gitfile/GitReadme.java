package com.example.wuzp.secondworld.gitfile;

import com.apkfuns.logutils.LogUtils;

/**
 * Created by wuzp on 2017/4/11.
 */
public class GitReadme {

    //this is method just to log msg to console
    public static void show(String msg){
        LogUtils.e("this is a show:" + msg);
    }

    //this method is carry a tag to console to defer different kind msg
    public static void show(String tag,String msg){
        LogUtils.e(tag,msg);
    }
}
