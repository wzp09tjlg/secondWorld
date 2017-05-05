package com.example.wuzp.secondworld.view.gt;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.apkfuns.logutils.LogUtils;
import com.igexin.sdk.GTServiceManager;

/**
 * Created by wuzp on 2017/5/5.
 * 个推本地创建的一个服务
 */
public class GTPushService extends Service {
    public static final String TAG = GTPushService.class.getName();

    @Override
    public void onCreate() {
        // 该行日志在 release 版本去掉
        LogUtils.d(TAG, TAG + " call -> onCreate -------");

        super.onCreate();
        GTServiceManager.getInstance().onCreate(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 该行日志在 release 版本去掉
        LogUtils.d(TAG, TAG + " call -> onStartCommand -------");

        super.onStartCommand(intent, flags, startId);
        return GTServiceManager.getInstance().onStartCommand(this, intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // 该行日志在 release 版本去掉
        LogUtils.d(TAG, "onBind -------");
        return GTServiceManager.getInstance().onBind(intent);
    }

    @Override
    public void onDestroy() {
        // 该行日志在 release 版本去掉
        LogUtils.d(TAG, "onDestroy -------");

        super.onDestroy();
        GTServiceManager.getInstance().onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        GTServiceManager.getInstance().onLowMemory();
    }
}
