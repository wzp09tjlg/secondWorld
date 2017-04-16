package com.example.wuzp.secondworld.swift;

import com.apkfuns.logutils.LogUtils;

/**
 * Created by wuzp on 2017/4/16.
 */
public class readme {
    //this file is to express some file to add some branch.
    //and just to test the git command code here

    public static void show(String msg){
        System.out.print("msg:" + msg);
    }

    public static void show(String tag,String msg){
        LogUtils.e(tag,msg);
    }
}
