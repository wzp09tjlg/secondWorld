package com.example.wuzp.secondworld.view.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.apkfuns.logutils.LogUtils;

/**
 * Created by wuzp on 2017/5/9.
 */

public class GlobalReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtils.e("GlobalReceiver  --");
        String action = intent.getAction();//一个Intent中可以有几个action
        if(action.equals("com.example.wuzp.secondworld.HELLO")){
            LogUtils.e(" GlobalReceiver  get receiver:  " + " com.example.wuzp.secondworld.HELLO  ");
        }else if(action.equals("android.net.conn.CONNECTIVITY_CHANGE")){
            LogUtils.e(" GlobalReceiver  get receiver:  " + " android.net.conn.CONNECTIVITY_CHANGE  ");
        }
    }
}
