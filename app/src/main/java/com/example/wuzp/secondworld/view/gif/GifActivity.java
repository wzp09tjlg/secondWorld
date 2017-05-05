package com.example.wuzp.secondworld.view.gif;

import android.os.Bundle;

import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.databinding.ActivityGifBinding;
import com.example.wuzp.secondworld.view.base.BindingActivity;

/**
 * Created by wuzp on 2017/5/4.
 */
public class GifActivity extends BindingActivity<ActivityGifBinding,GifPresenter> implements GifContract.IView {
    private GifViewWrapper viewWrapper = new GifViewWrapper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewWrapper.addBinding(binding);
    }

    @Override
    protected GifPresenter createPresenter() {
        return new GifPresenter(this);
    }

    @Override
    protected int createLayoutId() {
        return R.layout.activity_gif;
    }
}
