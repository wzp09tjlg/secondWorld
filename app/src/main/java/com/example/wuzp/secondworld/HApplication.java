package com.example.wuzp.secondworld;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.wuzp.secondworld.network.parse.User;
import com.example.wuzp.secondworld.utils.crashUtils.CrashHandler;
import com.example.wuzp.secondworld.view.VarDb.greenDao.auto.DaoMaster;
import com.example.wuzp.secondworld.view.VarDb.greenDao.auto.DaoSession;
import com.example.wuzp.secondworld.view.gt.GtActivity;
import com.orm.SugarContext;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by wuzp on 2017/3/18.
 */
public class HApplication extends Application { //SugarApp  也是继承了Application的context 只是在oncreate方法中 和 onterinate方法中对 sugar做了操作
    public static Context gContext;
    public static User gUser = new User();
    /** 个推接受离线的推送 */
    private static GTPushHandler handler;
    public static GtActivity gtActivity;
    /**应用未启动, 个推 service已经被唤醒,保存在该时间段内离线消息(此时 GetuiSdkDemoActivity.tLogView == null)*/
    public static StringBuilder payloadData = new StringBuilder();

    //GreenDao 自动生成的数据
    public static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        gContext = getApplicationContext();
        initGlobalVar();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        terminateGlobalVar();
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

        //初始化GreenDao
        initGreenDao();

        //初始化Sugar
        initSugarDb();

        //初始化Realm
        initRealm();
    }

    //初始化GreenDao的基本操作
    private void initGreenDao(){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "GreenDao.db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    /** 初始化Sugar 数据库*/
    private void initSugarDb(){
        SugarContext.init(this);
    }

    /*** 初始化Realm的操作 */
    private final String REALM_DB_NAME = "RealmDb.realm"; //数据库的名字 其实也是一种文件而已，虽然和sqlite 保存的文件后缀名不一致，但最终还是文件 ，区别关键是针对文件中的操作 是如何实现的
    private void initRealm(){
        /*// The Realm file will be located in package's "files" directory.
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).name(REALM_DB_NAME).build();

        Realm.setDefaultConfiguration(realmConfig);*/
    }

    private void terminateGlobalVar(){
        SugarContext.terminate();
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

    private void testGlobalAction(){
        registerComponentCallbacks(componentCallbacks);
        registerActivityLifecycleCallbacks(actLifecallbacks);
    }

    ComponentCallbacks componentCallbacks = new ComponentCallbacks() {
        @Override
        public void onConfigurationChanged(Configuration newConfig) {

        }

        @Override
        public void onLowMemory() {

        }
    };

    Application.ActivityLifecycleCallbacks actLifecallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };
}
