package com.example.wuzp.secondworld.view.databinding;

import android.view.View;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;

/**
 * Created by wuzp on 2017/4/10.
 */

public class HandleClick {
    public void onClick(View view){
        Toast.makeText(view.getContext(), "click this view:" , Toast.LENGTH_SHORT).show();
        LogUtils.e("onClickL");
    }
}
