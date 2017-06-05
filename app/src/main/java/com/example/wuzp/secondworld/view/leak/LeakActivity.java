package com.example.wuzp.secondworld.view.leak;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.wuzp.secondworld.R;

/**
 * Created by wuzp on 2017/6/5.
 * 经过测验 发现静态的单利中 在不使用单利后 可以通过置null 来销毁这个对象(可以不针对单利中 的其他引用置空操作)
 * 当然在销毁时 尽量保证单利对外持有的引用 一一置空，最后再针对单利置空
 * 有一种奇怪是 activity都已经执行了destory 方法，但是还是占用内存空间 手动的GC才能让占用的内存空间被释放掉
 */
public class LeakActivity extends AppCompatActivity implements View.OnClickListener,LeakImp {
   private Button btnLeak;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak);
        initView();
    }

    private void initView(){
        btnLeak = (Button)findViewById(R.id.btn_leak);
        btnLeak.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_leak:
                InstanceUtil.getInstance().setLeakImp(this);
                InstanceUtil.getInstance().testOne();
                InstanceUtil.getInstance().testTwo();
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("wzp","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("wzp","onDestroy");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(InstanceUtil.getInstance() != null){
            //消除静态单利中的外界浅引用
            InstanceUtil.getInstance().onDestory();
        }
    }

    @Override
    public void testMethodOne() {
        Log.e("wzp","One");
    }

    @Override
    public void testMethodTwo() {
        Log.e("wzp","Two");
    }
}
