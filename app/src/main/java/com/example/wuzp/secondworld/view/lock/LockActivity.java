package com.example.wuzp.secondworld.view.lock;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.apkfuns.logutils.LogUtils;
import com.example.wuzp.secondworld.R;

/**
 * Created by wuzp on 2017/6/2.
 * 测试结果如下：
 * 在一个资源中使用多个锁，可以减小锁的粒度。能够实现分开查询 分开调用的业务需求
 * 也满足 锁住一个事务之后 其他的操作必须要等到锁被释放之后才能真正的 被执行。
 */
public class LockActivity extends AppCompatActivity implements View.OnClickListener {
   private Button btnOne;
   private Button btnTwo;
   private Button btnThree;
   private Button btnAll;

    private SourceUtil sourceUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        initView();
    }

    private void initView(){
        btnOne = $(R.id.btn_one);
        btnTwo = $(R.id.btn_two);
        btnThree = $(R.id.btn_three);
        btnAll = $(R.id.btn_all);

        btnOne.setOnClickListener(this);
        btnTwo.setOnClickListener(this);
        btnThree.setOnClickListener(this);
        btnAll.setOnClickListener(this);

        initData();
    }

    private void initData(){
        sourceUtil = SourceUtil.getInstance();
    }

    private <T> T $(int id){
        return (T)findViewById(id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_one:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sourceUtil.testOne();
                    }
                }).start();

                    break;
            case R.id.btn_two:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sourceUtil.testTwo();
                    }
                }).start();

                break;
            case R.id.btn_three:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sourceUtil.testThree();
                    }
                }).start();

                break;
            case R.id.btn_all:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        LogUtils.e("wzp","allllllllllllllllllllllllllllllllllllllllllll");
                        sourceUtil.testOne();
                        LogUtils.e("wzp","allllllllllllllllllllllllllllllllllllllllllll");
                    }
                }).start();

                break;
        }
    }
}
