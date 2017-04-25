package com.example.wuzp.secondworld;

import android.app.Application;
import android.content.Context;

import com.example.wuzp.secondworld.network.parse.User;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by wuzp on 2017/3/18.
 */
public class HApplication extends Application {
    public static Context gContext;
    public static User gUser = new User();

    @Override
    public void onCreate() {
        super.onCreate();
        gContext = getApplicationContext();
        initGlobalVar();
    }

    private void initGlobalVar(){
      initLeakCanary();
    }

    /**
     * 在debug版本下使用LeakCanary检测内存泄漏
     */
    private void initLeakCanary() {
        if (BuildConfig.DEBUG) {//在debug包中使用leakcanary
            LeakCanary.install(this);
        }
    }

    public static Context getContext(){
        return gContext;
    }

    public static User getUser(){
        if(gUser == null){
            gUser = new User();
        }
        return gUser;
    }
}
