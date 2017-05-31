package com.example.wuzp.secondworld.view.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wuzp.secondworld.HApplication;
import com.example.wuzp.secondworld.R;

/**
 * Created by wuzp on 2017/4/26.
 */
public class ToastMsg {
    private String mTempStr = "";
    TextView tv = null;
    private Toast toast = null;
    public static ToastMsg i;

    public static ToastMsg getInsance() {
        if (i == null)
            i = new ToastMsg();
        return i;
    }

    private ToastMsg() {
    }

    @SuppressWarnings("all")
    public void show(String text) {
        if (TextUtils.isEmpty(text)) return;
        if (toast == null) {
            mTempStr = text;
            LayoutInflater inflate = (LayoutInflater)
                    HApplication.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflate.inflate(R.layout.view_msg,null);
            Animation animation = AnimationUtils.loadAnimation(HApplication.getContext().getApplicationContext(),R.anim.anim_msg);
            v.startAnimation(animation);
            tv = (TextView)v.findViewById(R.id.text_msg);
            toast = new Toast(HApplication.getContext());
            toast.setView(v);
        } else {
            toast.cancel();
            LayoutInflater inflate = (LayoutInflater)
                    HApplication.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflate.inflate(R.layout.view_msg,null);
            Animation animation = AnimationUtils.loadAnimation(HApplication.getContext().getApplicationContext(),R.anim.anim_msg);
            v.startAnimation(animation);
            tv = (TextView)v.findViewById(R.id.text_msg);
            toast = new Toast(HApplication.getContext());
            toast.setView(v);
            mTempStr = text;
        }
        tv.setText(text);
        toast.show();
    }
}
