package com.example.wuzp.secondworld.view.wangdou.utils;


import android.content.Context;

import com.example.wuzp.secondworld.HApplication;

/**
 * Created by zyb on 2016/9/27.
 */
public class UserUtils {
    public static String getUid() {
        return HApplication.gContext.getSharedPreferences(FinalUtil.PREFERENCES_NAME, Context.MODE_APPEND).getString(FinalUtil.KEY_UID, "");
    }

    public static String getToken() {
        return HApplication.gContext.getSharedPreferences(FinalUtil.PREFERENCES_NAME, Context.MODE_APPEND).getString(FinalUtil.KEY_ACCESS_TOKEN, "");
    }
}
