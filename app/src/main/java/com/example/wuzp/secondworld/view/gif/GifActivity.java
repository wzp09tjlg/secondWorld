package com.example.wuzp.secondworld.view.gif;

import android.os.Bundle;
import android.view.View;

import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.databinding.ActivityGifBinding;
import com.example.wuzp.secondworld.view.base.BindingActivity;

/**
 * Created by wuzp on 2017/5/4.
 */
public class GifActivity extends BindingActivity<ActivityGifBinding,GifPresenter> implements GifContract.IView {
    private GifViewWrapper viewWrapper = new GifViewWrapper();
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewWrapper.addBinding(binding);
        iniView();
    }

    @Override
    protected GifPresenter createPresenter() {
        return new GifPresenter(this);
    }

    @Override
    protected int createLayoutId() {
        return R.layout.activity_gif;
    }

    private void iniView(){
        binding.btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count == 0){
                    binding.vGif.setImageResource(R.drawable.gif_newuser_open);
                }else
                if(count == 1){
                    binding.vGif.setImageResource(R.drawable.gif_newuser_success);
                }else
                if(count == 2){
                    binding.vGif.setImageResource(R.drawable.gif_newuser_show);
                }else
                if(count == 3){
                    binding.vGif.setImageResource(R.drawable.gif_newuser_close);
                }

                count = (count + 1) % 4;
            }
        });
    }
}
