package com.example.wuzp.secondworld.view.gt;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.example.wuzp.secondworld.HApplication;
import com.example.wuzp.secondworld.R;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.Tag;

import java.io.File;

/**
 * Created by wuzp on 2017/5/5.
 * 个推的activity
 * 这里说明一下 个推的推送分为普通的推送和穿透推送。普通推送是指本地添加了个推的sdk,在个推的后台进行推送，然后个推的sdk
 * 接收到消息然后传递到本地的应用中来。有本地的应用处理消息。穿透消息是指在个推的后台推送消息，将消息发送到本地应用添加的
 * 广播接受者，然后自己去控制消息的处理。普通的推送和穿透的推送本质区别是 普通的推送可能会被用户禁止掉
 * 但是穿透推送就是用户禁止不掉(如果应用不提供推送的开关的法) 普通的推送直接继承demo的服务，但是穿透的推送却需要自己去写
 * 消息接收
 * <p>
 * 个推版本中当前集成了一下几个功能：
 * 1.获取CID
 * 2.打开或者关闭个推
 * 3.设置标签
 * 4.设置别名
 * 5.设置静默时间
 * 6.获取版本号
 */
public class GtActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = GtActivity.class.getSimpleName();

    private TextView textLog;
    private Button btn_getCid; //获取CID
    private Button btn_TurnOn; //打开个推开关
    private Button btn_TurnOff; //关闭个推的开关
    private Button btn_setTags; //设置标签
    private Button btn_setAlis; //设置别名
    private Button btn_setTime; //设置静默时间
    private Button btn_getVersion; //获取个推的版本信息

    // SDK服务是否启动.
    private boolean isServiceRunning = true;
    private boolean isBinded = false;
    private Context context = this;

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

    public TextView getTextLog() {
        if (textLog != null)
            return textLog;
        else return null;
    }

    public void setTextLog(String text) {
        if (textLog != null)
            textLog.setText(text);
    }

    private <T extends View> T $(int id) {
        return (T) findViewById(id);
    }

    private void initView() {
        textLog = $(R.id.text_log);

        btn_getCid = $(R.id.btn_getCid);
        btn_TurnOn = $(R.id.btn_TurnOn);
        btn_TurnOff = $(R.id.btn_TurnOff);
        btn_setTags = $(R.id.btn_setTags);
        btn_setAlis = $(R.id.btn_setAlis);
        btn_setTime = $(R.id.btn_setTime);
        btn_getVersion = $(R.id.btn_getVersion);

        initData();
    }

    private void initData() {
        btn_getCid.setOnClickListener(this);
        btn_TurnOn.setOnClickListener(this);
        btn_TurnOff.setOnClickListener(this);
        btn_setTags.setOnClickListener(this);
        btn_setAlis.setOnClickListener(this);
        btn_setTime.setOnClickListener(this);
        btn_getVersion.setOnClickListener(this);

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
      //  PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), GTPushIntentService.class);
       //如果我把上边的注册的服务关掉了，那么 还是可以收到推送，但是推送就只是这样一个推动，附带的消息 怎么获取呢？不得而知
        // 应用未启动, 个推 service已经被唤醒,显示该时间段内离线消息
        if (HApplication.payloadData != null) {
            textLog.append(HApplication.payloadData);
        }

        // cpu 架构 一般cpu使用的指令集是arm64 的
      //  LogUtils.e(TAG, "cpu arch = " + (Build.VERSION.SDK_INT < 21 ? Build.CPU_ABI : Build.SUPPORTED_ABIS[0]));

        // 检查 so 是否存在 检查so的是否存在
        File file = new File(this.getApplicationInfo().nativeLibraryDir + File.separator + "libgetuiext2.so");
       // LogUtils.e(TAG, "libgetuiext2.so exist = " + file.exists());
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE},
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_getCid://获取CID
                String cid = PushManager.getInstance().getClientid(this);
                LogUtils.e("GT CID:" + cid);

                //发送一个自定义的广播
                Intent intent = new Intent();
                intent.setAction("com.example.wuzp.secondworld.HELLO");
                sendBroadcast(intent);

                break;
            case R.id.btn_TurnOn://打开个推的开关
                if (isServiceRunning) return;
                isServiceRunning = true;
                PushManager.getInstance().initialize(this.getApplicationContext(), userPushService); // 重新初始化sdk
                LogUtils.e("turn on GT ");
                break;
            case R.id.btn_TurnOff://关闭个推的开关
                if (!isServiceRunning) return;
                isServiceRunning = false;
                PushManager.getInstance().stopService(this.getApplicationContext());// 当前为运行状态，停止SDK服务
                LogUtils.e("turn off GT ");
                break;
            case R.id.btn_setTags://设置标签
                setTags();
                break;
            case R.id.btn_setAlis://设置别名
                setAlis();
                break;
            case R.id.btn_setTime://设置静默的时间
                setTime();
                break;
            case R.id.btn_getVersion://获取个推的版本号
                String version = PushManager.getInstance().getVersion(this);
                LogUtils.e("GT VERSION:" + version);
                break;
        }
    }

    private void setTags() {
        final View view = new EditText(this);
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("设置Tag").setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                TextView tagText = (TextView) view;

                Log.d(TAG, "setTag input tags = " + tagText.getText().toString());

                String[] tags = tagText.getText().toString().split(",");
                Tag[] tagParam = new Tag[tags.length];
                for (int i = 0; i < tags.length; i++) {
                    Tag t = new Tag();
                    t.setName(tags[i]);
                    tagParam[i] = t;
                }

                int i = PushManager.getInstance().setTag(context, tagParam, "" + System.currentTimeMillis());
                String text = "设置标签失败, 未知异常";

                // 这里的返回结果仅仅是接口调用是否成功, 不是真正成功, 真正结果见{
                // com.getui.demo.DemoIntentService.setTagResult 方法}
                switch (i) {
                    case PushConsts.SETTAG_SUCCESS:
                        text = "接口调用成功";
                        break;

                    case PushConsts.SETTAG_ERROR_COUNT:
                        text = "接口调用失败, tag数量过大, 最大不能超过200个";
                        break;

                    case PushConsts.SETTAG_ERROR_FREQUENCY:
                        text = "接口调用失败, 频率过快, 两次间隔应大于1s";
                        break;

                    case PushConsts.SETTAG_ERROR_NULL:
                        text = "接口调用失败, tag 为空";
                        break;

                    default:
                        break;
                }

                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }).setView(view);
        alertBuilder.create().show();
    }

    private void setAlis() {
        if (isBinded) {
            unBindAlias();
        } else {
            bindAlias();
        }
    }

    private void unBindAlias() {
        final EditText editText = new EditText(GtActivity.this);
        new AlertDialog.Builder(GtActivity.this).setTitle("解绑别名").setView(editText)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String alias = editText.getEditableText().toString();
                        if (alias.length() > 0) {
                            PushManager.getInstance().unBindAlias(GtActivity.this, alias, false);
                            LogUtils.d(TAG, "unbind alias = " + editText.getEditableText().toString());
                        }
                    }
                }).setNegativeButton(android.R.string.cancel, null).show();
    }

    private void bindAlias() {
        final EditText editText = new EditText(this);
        new AlertDialog.Builder(GtActivity.this).setTitle("绑定别名").setView(editText)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (editText.getEditableText() != null) {
                            String alias = editText.getEditableText().toString();
                            if (alias.length() > 0) {
                                PushManager.getInstance().bindAlias(GtActivity.this, alias);
                                LogUtils.d(TAG, "bind alias = " + editText.getEditableText().toString());
                            }
                        }
                    }
                }).setNegativeButton(android.R.string.cancel, null).show();
    }

    private void setTime() {
        final View view = LayoutInflater.from(this).inflate(R.layout.view_gt_silent_setting, null);
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("设置静默时间段").setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                TextView beginText = (TextView) view.findViewById(R.id.beginText);
                TextView durationText = (TextView) view.findViewById(R.id.durationText);

                try {
                    int beginHour = Integer.valueOf(String.valueOf(beginText.getText()));
                    int durationHour = Integer.valueOf(String.valueOf(durationText.getText()));

                    boolean result = PushManager.getInstance().setSilentTime(context, beginHour, durationHour);

                    if (result) {
                        Toast.makeText(context, "begin = " + beginHour + ", duration = " + durationHour, Toast.LENGTH_SHORT).show();
                        LogUtils.d(TAG, "setSilentime, begin = " + beginHour + ", duration = " + durationHour);
                    } else {
                        Toast.makeText(context, "setSilentime failed, value exceeding", Toast.LENGTH_SHORT).show();
                        LogUtils.d(TAG, "setSilentime failed, value exceeding");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                dialog.dismiss();
            }
        }).setView(view);
        alertBuilder.create().show();
    }
}
