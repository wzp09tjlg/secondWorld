package com.example.wuzp.secondworld.view.glide;

import android.support.annotation.NonNull;
import android.view.View;

import com.example.wuzp.secondworld.databinding.ActivityGlideBinding;
import com.example.wuzp.secondworld.view.base.BaseViewWrapper;

/**
 * Created by wuzp on 2017/4/24.
 */

public class GlideViewWrapper extends BaseViewWrapper<ActivityGlideBinding> {
   private GlideContract.OnItemClick itemClickListener;

    @Override
    public void addBinding(@NonNull ActivityGlideBinding binding) {
        super.addBinding(binding);
        initView();
    }

    @Override
    public void release() {

    }

    private void initView(){
        binding.btnGetUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener != null){
                    itemClickListener.onItemClick(v);
                }
            }
        });
    }

    public void setItemClickListener(GlideContract.OnItemClick itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
