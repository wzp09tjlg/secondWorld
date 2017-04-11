package com.example.wuzp.secondworld.view.bindingtest;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.databinding.ActivityBindingTestBinding;

/**
 * Created by wuzp on 2017/4/10.
 */
public class BindingTestActivity extends FragmentActivity {
    private ActivityBindingTestBinding bindingTestBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(this);
        bindingTestBinding = DataBindingUtil.inflate(inflater, R.layout.activity_binding_test,null,false);
        initView(bindingTestBinding);
    }

    private void initView(ActivityBindingTestBinding binding){
        binding.textName.setText("this is a test TextView");
        binding.btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BindingTestActivity.this,"databinding toast",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
