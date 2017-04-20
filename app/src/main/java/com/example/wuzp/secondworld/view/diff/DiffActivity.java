package com.example.wuzp.secondworld.view.diff;

import android.os.Bundle;

import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.databinding.ActivityDiffBinding;
import com.example.wuzp.secondworld.view.base.BindingActivity;

/**
 * Created by wuzp on 2017/4/20.
 */
public class DiffActivity extends BindingActivity<ActivityDiffBinding,DiffPresenter> implements DiffContract.IView {
    private DiffViewWrapper viewWrapper = new DiffViewWrapper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewWrapper.addBinding(binding);
    }

    @Override
    protected DiffPresenter createPresenter() {
        return new DiffPresenter(this);
    }

    @Override
    protected int createLayoutId() {
        return R.layout.activity_diff;
    }


}
