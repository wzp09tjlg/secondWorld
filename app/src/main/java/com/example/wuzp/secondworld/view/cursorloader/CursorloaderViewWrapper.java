package com.example.wuzp.secondworld.view.cursorloader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.example.wuzp.secondworld.databinding.ActivityCursorloaderBinding;
import com.example.wuzp.secondworld.view.base.BaseViewWrapper;

/**
 * Created by wuzp on 2017/4/28.
 */

public class CursorloaderViewWrapper extends BaseViewWrapper<ActivityCursorloaderBinding> {
    private Context mContext;
    private CursorloaderContract.OnItemClick clickListener;
    private CursorLoaderAdapter adapter;

    public CursorloaderViewWrapper(){}

    @Override
    public void addBinding(@NonNull ActivityCursorloaderBinding binding) {
        super.addBinding(binding);
        mContext = binding.getRoot().getContext();
        initView();
    }

    private void initView(){
       binding.btnGet.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

           }
       });
//        adapter = new CursorLoaderAdapter(mContext,)
    }

    @Override
    public void release() {

    }

    public void addClickListener(CursorloaderContract.OnItemClick click) {
        this.clickListener = click;
    }
}
