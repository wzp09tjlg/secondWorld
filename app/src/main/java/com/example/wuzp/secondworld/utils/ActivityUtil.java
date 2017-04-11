package com.example.wuzp.secondworld.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by wuzp on 2017/4/5.
  用于管理Activity的启动
 */
public class ActivityUtil {

    public static void jumpActivity(Context packageContext, Class<?> cls){
        Intent intent = new Intent(packageContext,cls);
        packageContext.startActivity(intent);
    }

    public static void jumpActivityWithBundle(Context packageContext, Class<?> cls,Bundle bundle){
        Intent intent = new Intent(packageContext,cls);
        intent.putExtras(bundle);
        packageContext.startActivity(intent);
    }

    public static void jumpActivityWithBundleForResult(Activity activity, Class<?> cls, Bundle bundle, int requestCode){
        Intent intent = new Intent(activity,cls);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent,requestCode);
    }
}
