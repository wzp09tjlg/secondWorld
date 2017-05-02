package com.example.wuzp.secondworld.view.task;

import android.support.annotation.NonNull;

import com.example.wuzp.secondworld.databinding.ActivityTaskBinding;
import com.example.wuzp.secondworld.view.base.BaseViewWrapper;

/**
 * Created by wuzp on 2017/4/27.
 */

public class TaskViewWrapper extends BaseViewWrapper<ActivityTaskBinding> {
    public TaskViewWrapper(){}

    @Override
    public void addBinding(@NonNull ActivityTaskBinding binding) {
        super.addBinding(binding);
        initView();
    }

    private void initView(){

    }

    @Override
    public void release() {

    }
}
