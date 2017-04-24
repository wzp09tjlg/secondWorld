package com.example.hlibrary.utils;

import com.apkfuns.logutils.LogUtils;

/**
 * Created by wuzp on 2017/4/24.
 */
public class ToolsUtil {

    public static void show(String msg){
        LogUtils.e("msg:" + msg);
    }

    public static void add(int num1,int num2){
        LogUtils.e("num1:" + num1 + "  num2:" + num2);
    }

    public static void minus(int num1,int num2){
        LogUtils.e("num1:" + num1 + "  num2:" + num2);
    }

}
