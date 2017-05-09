package com.example.wuzp.secondworld;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.apkfuns.logutils.LogUtils;
import com.example.wuzp.secondworld.network.parse.User;
import com.example.wuzp.secondworld.view.gt.GtActivity;
import com.example.wuzp.secondworld.view.main.MainActivity;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by wuzp on 2017/3/18.
 */
public class HApplication extends Application {
    public static Context gContext;
    public static User gUser = new User();
    /**
     * 个推接受离线的推送
     */
    private static GTPushHandler handler;
    public static GtActivity gtActivity;
    /**
     * 应用未启动, 个推 service已经被唤醒,保存在该时间段内离线消息(此时 GetuiSdkDemoActivity.tLogView == null)
     */
    public static StringBuilder payloadData = new StringBuilder();

    @Override
    public void onCreate() {
        super.onCreate();
        gContext = getApplicationContext();
        initGlobalVar();
    }

    private void initGlobalVar() {
        //检测内存泄漏的工具
        initLeakCanary();
        //个推的离线接受信息handler
        if (handler == null) {
            handler = new GTPushHandler();
        }
        registerActivityLifeCallbacks();
    }

    /**
     * 在debug版本下使用LeakCanary检测内存泄漏
     */
    private void initLeakCanary() {
        if (BuildConfig.DEBUG) {//在debug包中使用leakcanary
            LeakCanary.install(this);
        }
    }

    public static Context getContext() {
        return gContext;
    }

    public static User getUser() {
        if (gUser == null) {
            gUser = new User();
        }
        return gUser;
    }

    /**
     * 个推的消息处理
     */
    public static void sendMessage(Message msg) {
        handler.sendMessage(msg);
    }

    /**
     * 个推的接受离线消息的Handler
     */
    public class GTPushHandler extends Handler {
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

    @SuppressWarnings("ALL")
    //忽略版本的警告，因为在运行的机器上的安卓源码都是在18以上，
    // 所以即使这里有警告 但是在运行时页没有关系
    //使用这个接口ActivityLifecycleCallbacks
    private void registerActivityLifeCallbacks() {
        //这个接口可以 获取任何的activity的生命周期
        // 注册这个接口 是可以检测整个应用的所有的activity的生命周期，
        // 但是所有的activity都在application中处理 是不是会有些重？
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                if (activity instanceof MainActivity) {
                    LogUtils.e("mainActivity is onActivityCreated");
                } else if (activity instanceof GtActivity) {
                    LogUtils.e("GtActivity is onActivityCreated");
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {
                if (activity instanceof MainActivity) {
                    LogUtils.e("mainActivity is onActivityStarted");
                } else if (activity instanceof GtActivity) {
                    LogUtils.e("GtActivity is onActivityStarted");
                }
            }

            @Override
            public void onActivityResumed(Activity activity) {
                if (activity instanceof MainActivity) {
                    LogUtils.e("mainActivity is onActivityResumed");
                } else if (activity instanceof GtActivity) {
                    LogUtils.e("GtActivity is onActivityResumed");
                }
            }

            @Override
            public void onActivityPaused(Activity activity) {
                if (activity instanceof MainActivity) {
                    LogUtils.e("mainActivity is onActivityPaused");
                } else if (activity instanceof GtActivity) {
                    LogUtils.e("GtActivity is onActivityPaused");
                }
            }

            @Override
            public void onActivityStopped(Activity activity) {
                if (activity instanceof MainActivity) {
                    LogUtils.e("mainActivity is onActivityStopped");
                } else if (activity instanceof GtActivity) {
                    LogUtils.e("GtActivity is onActivityStopped");
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                if (activity instanceof MainActivity) {
                    LogUtils.e("mainActivity is onActivitySaveInstanceState");
                } else if (activity instanceof GtActivity) {
                    LogUtils.e("GtActivity is onActivitySaveInstanceState");
                }
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if (activity instanceof MainActivity) {
                    LogUtils.e("mainActivity is onActivityDestroyed");
                } else if (activity instanceof GtActivity) {
                    LogUtils.e("GtActivity is onActivityDestroyed");
                }
            }
        });

        //这个注册的方法 不知道在什么时候被调用
//        registerComponentCallbacks(new ComponentCallbacks() { //ComponentCallbacks 被fragment 实现了
//            @Override
//            public void onConfigurationChanged(Configuration newConfig) {
//              LogUtils.e("ComponentCallbacks  onConfigurationChanged --- " );
//            }
//
//            @Override
//            public void onLowMemory() {
//               LogUtils.e("ComponentCallbacks  onLowMemory ---");
//            }
//        });

        //也不知道这个方法是在什么时候 被调用
//        registerOnProvideAssistDataListener(new OnProvideAssistDataListener(){
//            @Override
//            public void onProvideAssistData(Activity activity, Bundle data) {
//                LogUtils.e("OnProvideAssistDataListener  onProvideAssistData   -----  ");
//            }
//        });//API版本在18以上 但是当前的api最低是14 (4.0)

        //安卓中的广播 是可以再Application中获取得到的。简单的广播知识两种创建方式 动态注册和activity中的注册。
        //区分广播的intentFilter 可以携带多个Action，在清单文件中 一个receiver可以添加多个intentFilter。
        // 也就是说一个广播可以监听全应用的广播。当然这种广播是全局的，缺点就是比较耗费资源。毕竟全局的广播一直都靠
        //系统维护，但是局部的广播就是在使用时定义在销毁时注销
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.wuzp.secondworld.HELLO");
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

        //这个方法是一个广播的注册  莫非是这个注册方法
        // 可以在application中 监听到这个应用发的所有广播？到时候可以尝试一下
        // 经过尝试 这里可以监听到 应用中所有发送的广播，及系统的广播。
        // 实验证明，application中的广播监听 是先与broadcastreceiver
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                LogUtils.e("BroadcastReceiver   ");
                String action = intent.getAction();
                if(action.equals("com.example.wuzp.secondworld.HELLO")){
                    LogUtils.e("get receiver : " + "com.example.wuzp.secondworld.HELLO");
                }
            }
        }, filter);
//        registerReceiver(null,null,null,null);

        //这个注册是不是可以监听权限的问题
        //撤销Uri 的权限问题？待会可以尝试一下
//                revokeUriPermission(null,0);
    }
}
