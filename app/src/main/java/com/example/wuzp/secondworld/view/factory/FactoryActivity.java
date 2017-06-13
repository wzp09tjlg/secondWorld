package com.example.wuzp.secondworld.view.factory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.view.base.BaseActivity;

/**
 * Created by wuzp on 2017/6/13.
 * 这里主要是学习几个类 factory help callback  listener
 * 在构建一个比较复杂的类时  可以将一些东西进行拆分，然后将可以实现代码的分解
 * (不至于在一个类中存在大量的代码)
 */
public class FactoryActivity extends BaseActivity implements ChangeCallback, View.OnClickListener {

    private Button btnChange;
    private ChangeFactory factory;
    private ChangeHelper helper;

    private Object object;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factory);
        initView();
    }

    private void initView(){
        btnChange = (Button)findViewById(R.id.btn_change);

        try{
            initData();
        }catch (Exception e){}
    }

    @SuppressWarnings("all")
    private void initData() throws Exception {
        object = new Object();//这里定义了 自己的Object 类，在引用时 声明了object 是自己的定义的类
        String classLoader = object.getClass().getClassLoader().getClass().getName();
        object.show();
        Log.e("wzp","classloader: " + classLoader);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void callBackOne() {

    }

    @Override
    public void callBackTwo() {

    }

    @Override
    public void callBackThree() {

    }
}
