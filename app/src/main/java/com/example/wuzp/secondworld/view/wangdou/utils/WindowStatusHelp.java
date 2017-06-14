package com.example.wuzp.secondworld.view.wangdou.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;

import com.sina.book.base.BaseApp;
import com.sina.book.utils.SRPreferences;

/**
 * 页面亮度帮助类
 * 有缺陷  暂时只是在阅读页面使用 做基本封装  没有考虑多页面都有明暗度改变时数据获取差异
 * 考虑每个静态方法传入context  使用context做页面区分
 * Created by zyb on 2017/2/28.
 */
public class WindowStatusHelp {

    /*****************************
     * 屏幕尺寸状态
     *************************************/
    public static void noLimitScreen(Activity activity) {
        if (SRPreferences.getInstance().getBoolean(SRPreferences.READ_FULL_ALL, true)) {
            WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
            attrs.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
            activity.getWindow().setAttributes(attrs);
        }
    }

    public static void limitScreen(Activity activity) {
        if (SRPreferences.getInstance().getBoolean(SRPreferences.READ_FULL_ALL, true)) {
            WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
            attrs.flags &= ~WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
            activity.getWindow().setAttributes(attrs);
        }
    }

    public static void showStatusBar(Activity activity) {
        if (SRPreferences.getInstance().getBoolean(SRPreferences.READ_FULL_ALL, true)) {
            WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
            attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
            attrs.flags &= ~WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
            activity.getWindow().setAttributes(attrs);
        }
    }

    public static void hideStatusBar(Activity activity) {
        if (SRPreferences.getInstance().getBoolean(SRPreferences.READ_FULL_ALL, true)) {
            WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
            attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            attrs.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
            activity.getWindow().setAttributes(attrs);
        }
    }

    public static void statusbarChange(Activity activity) {
        if (SRPreferences.getInstance().getBoolean(SRPreferences.READ_FULL_ALL, true)) {
            WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
            attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            attrs.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                attrs.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            }
            activity.getWindow().setAttributes(attrs);
        } else {
            WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                attrs.flags &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            }
            attrs.flags &= ~WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
            attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
            activity.getWindow().setAttributes(attrs);
        }
    }

    /*****************************
     * 屏幕亮度状态
     *************************************/
    public static void changeAppBrightness(Activity context) {
        Window window = context.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (isAutomatic()) {
            lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        } else {
            lp.screenBrightness = (getScreenLightVolue() <= 0 ? 1 : getScreenLightVolue()) / 255f;
        }
        window.setAttributes(lp);
    }

    public static int getScreenLightVolue() {
        return SRPreferences.getInstance().getInt(SRPreferences.READ_LIGHT_VOLUE, 122);
    }

    public static void setScreenLightVolue(Activity activity, int screenLightVolue) {
        SRPreferences.getInstance().setInt(SRPreferences.READ_LIGHT_VOLUE, screenLightVolue);
        changeAppBrightness(activity);
    }

    public static int getScreenLightMode() {
        return SRPreferences.getInstance().getInt(SRPreferences.READ_LIGHT_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
    }

    public static void setScreenLightMode(Activity activity, int screenLightMode) {
        SRPreferences.getInstance().setInt(SRPreferences.READ_LIGHT_MODE, screenLightMode);
        changeAppBrightness(activity);
    }

    public static boolean isAutomatic() {
        return getScreenLightMode() == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
    }

    /*****************************
     * 屏幕透明度状态
     *************************************/
    public static void setWindowAlpha(Activity activity, float f) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = f; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }

    public static void getSystemScreenTimeout(boolean save){
        try {
            if (save){
                SRPreferences.getInstance().setInt(SRPreferences.READ_LIGHT_TIME_SYSTER,
                        Settings.System.getInt(BaseApp.gContext.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT));
            }else {
                if (getWakeLock().isHeld()) {
                    mWakeLock.release();
                }
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * value 0 记录系统时间   设置用户时间
     * value 5，10，
     */
    public static void setWindowLightTime(int value) {
        SRPreferences.getInstance().setInt(SRPreferences.READ_LIGHT_TIME, value);
        switch (value) {
            case 0:
                setWakeLock(SRPreferences.getInstance().getInt(SRPreferences.READ_LIGHT_TIME_SYSTER,10*1000*60));
                break;
            case 5:
                setWakeLock(5 * 60 * 1000);
                break;
            case 10:
                setWakeLock(10 * 60 * 1000);
                break;
            case -1:
                setWakeLock(-1);
                break;
            default:
                break;
        }
    }

    static PowerManager.WakeLock mWakeLock;
    public static final String wl_tag = "sina";

    public static void setWakeLock(long timeout) {
        if (getWakeLock().isHeld()) {
            mWakeLock.release();
        }
        if (timeout != -1) {
            getWakeLock().acquire(timeout);
        } else {
            getWakeLock().acquire();
        }
    }

    public static PowerManager.WakeLock getWakeLock() {
        if (mWakeLock == null){
            synchronized (WindowStatusHelp.class){
                if (mWakeLock == null){
                    PowerManager pm = (PowerManager) BaseApp.gContext.getSystemService(Context.POWER_SERVICE);
                    mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, wl_tag);
                    mWakeLock.setReferenceCounted(false);
                }
            }
        }
        return mWakeLock;
    }
}
