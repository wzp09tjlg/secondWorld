package com.example.wuzp.secondworld.view.glide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.wuzp.secondworld.databinding.ActivityGlideBinding;
import com.example.wuzp.secondworld.view.base.BaseViewWrapper;

/**
 * Created by wuzp on 2017/4/24.
 */

public class GlideViewWrapper extends BaseViewWrapper<ActivityGlideBinding> {
   private GlideContract.OnItemClick itemClickListener;
   private Context mContext ;

    @Override
    public void addBinding(@NonNull ActivityGlideBinding binding) {
        super.addBinding(binding);
        mContext = binding.getRoot().getContext();
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

        //使用多个layoutmanager 来管理布局进行展示
        LinearLayoutManager linearLayoutManager1 =
                new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        LinearLayoutManager linearLayoutManager2 =
                new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(mContext,2);
        StaggeredGridLayoutManager staggeredGridLayoutManager1 =
                new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
    }

    public void setItemClickListener(GlideContract.OnItemClick itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
