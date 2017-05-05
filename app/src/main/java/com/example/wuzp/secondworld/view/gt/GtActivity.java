package com.example.wuzp.secondworld.view.gt;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.example.wuzp.secondworld.HApplication;
import com.example.wuzp.secondworld.R;
import com.igexin.sdk.PushManager;

import java.io.File;

/**
 * Created by wuzp on 2017/5/5.
 * 个推的activity
 */
public class GtActivity extends AppCompatActivity {
    private static final String TAG = GtActivity.class.getSimpleName();

    private TextView textLog;

    // SDK服务是否启动.
    private boolean isServiceRunning = true;
    private Context context;

    private String appkey = "";
    private String appsecret = "";
    private String appid = "";

    private static final int REQUEST_PERMISSION = 0;

    private Class userPushService = GTPushService.class;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gt);
        initView();
    }

    public TextView getTextLog(){
        if(textLog != null)
            return textLog;
        else return null;
    }

    public void setTextLog(String text){
        if(textLog != null)
            textLog.setText(text);
    }

    private <T extends View> T $(int id){
      return (T) findViewById(id);
    }

    private void initView(){
        textLog = $(R.id.text_log);

        initData();
    }

    private void initData(){
        PackageManager pkgManager = getPackageManager();

        // 读写 sd card 权限非常重要, android6.0默认禁止的, 建议初始化之前就弹窗让用户赋予该权限
        boolean sdCardWritePermission =
                pkgManager.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, getPackageName()) == PackageManager.PERMISSION_GRANTED;

        // read phone state用于获取 imei 设备信息
        boolean phoneSatePermission =
                pkgManager.checkPermission(Manifest.permission.READ_PHONE_STATE, getPackageName()) == PackageManager.PERMISSION_GRANTED;

        if (Build.VERSION.SDK_INT >= 23 && !sdCardWritePermission || !phoneSatePermission) {
            requestPermission();
        } else {
            PushManager.getInstance().initialize(this.getApplicationContext(), userPushService);
        }

        // 注册 intentService 后 PushDemoReceiver 无效, sdk 会使用 DemoIntentService 传递数据,
        // AndroidManifest 对应保留一个即可(如果注册 GTPushIntentService, 可以去掉 PushDemoReceiver, 如果注册了
        // IntentService, 必须在 AndroidManifest 中声明)
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), GTPushIntentService.class);

        // 应用未启动, 个推 service已经被唤醒,显示该时间段内离线消息
        if (HApplication.payloadData != null) {
            textLog.append(HApplication.payloadData);
        }

        // cpu 架构
        LogUtils.e(TAG, "cpu arch = " + (Build.VERSION.SDK_INT < 21 ? Build.CPU_ABI : Build.SUPPORTED_ABIS[0]));

        // 检查 so 是否存在
        File file = new File(this.getApplicationInfo().nativeLibraryDir + File.separator + "libgetuiext2.so");
        LogUtils.e(TAG, "libgetuiext2.so exist = " + file.exists());
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE},
                REQUEST_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            if ((grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                PushManager.getInstance().initialize(this.getApplicationContext(), userPushService);
            } else {
                LogUtils.e(TAG, "We highly recommend that you need to grant the special permissions before initializing the SDK, otherwise some "
                        + "functions will not work");
                PushManager.getInstance().initialize(this.getApplicationContext(), userPushService);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
