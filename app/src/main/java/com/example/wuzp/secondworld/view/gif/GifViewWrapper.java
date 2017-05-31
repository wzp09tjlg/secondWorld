package com.example.wuzp.secondworld.view.gif;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.databinding.ActivityGifBinding;
import com.example.wuzp.secondworld.view.base.BaseViewWrapper;

/**
 * Created by wuzp on 2017/5/4.
 */

public class GifViewWrapper extends BaseViewWrapper<ActivityGifBinding> {
   private Context mContext;

    public GifViewWrapper(){}

    @Override
    public void addBinding(@NonNull ActivityGifBinding binding) {
        super.addBinding(binding);
        mContext = binding.getRoot().getContext();
        initView();
    }

    private void initView(){
        //测试支持gif的动图处理
        binding.vGif.setImageResource(R.drawable.gif_1);//正常显示
        binding.btnGif.setImageResource(R.drawable.gif_2);//正常显示 但是会有btn的边
        //binding.textGif.setBackground(mContext.getResources().getDrawable(R.drawable.gif_3)); //这里只是显示一张静图 不显示动态的图

        //测试支持gif的资源来源处理
        binding.vGif.setImageDrawable(mContext.getResources().getDrawable(R.drawable.gif_3));//这里只是显示一张静态的图 不显示动态的图
        // 可以加载本地的资源 URI


    }

    @Override
    public void release() {

    }
}
