package com.example.wuzp.secondworld;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.wuzp.secondworld.network.parse.User;
import com.example.wuzp.secondworld.utils.crashUtils.CrashHandler;
import com.example.wuzp.secondworld.view.gt.GtActivity;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by wuzp on 2017/3/18.
 */
public class HApplication extends Application {
    public static Context gContext;
    public static User gUser = new User();
    /** 个推接受离线的推送 */
    private static GTPushHandler handler;
    public static GtActivity gtActivity;
    /**应用未启动, 个推 service已经被唤醒,保存在该时间段内离线消息(此时 GetuiSdkDemoActivity.tLogView == null)*/
    public static StringBuilder payloadData = new StringBuilder();

    @Override
    public void onCreate() {
        super.onCreate();
        gContext = getApplicationContext();
        initGlobalVar();
    }

    private void initGlobalVar(){
        //检测内存泄漏的工具
        initLeakCanary();
        //个推的离线接受信息handler
        if (handler == null) {
            handler = new GTPushHandler();
        }
        //CrashHandler  注册应用的处理异常 (需要些本地的权限)
        Thread.setDefaultUncaughtExceptionHandler(CrashHandler.getInstance(gContext));
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

    /** 个推的消息处理 */
    public static void sendMessage(Message msg) {
        handler.sendMessage(msg);
    }

    /** 个推的接受离线消息的Handler */
    public class GTPushHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (gtActivity != null) {
                        payloadData.append((String) msg.obj);
                        payloadData.append("\n");
                        if (gtActivity.getTextLog() != null) {
                            gtActivity.getTextLog().append(msg.obj + "\n");
                        }
                    }
                    break;

                case 1:
                    if (gtActivity != null) {
                        if (gtActivity.getTextLog() != null) {
                            gtActivity.getTextLog().setText((String) msg.obj);
                        }
                    }
                    break;
            }
        }
    }
}
