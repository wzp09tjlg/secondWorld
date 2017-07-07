package com.example.wuzp.secondworld.view.SelfView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.wuzp.secondworld.R;

/**
 * Created by wuzp on 2017/7/6.
 * 这里想测试下自定义View的测试
 *  继承  组合  自己写(继承ViewGroup 或者是View)
 */
public class SelfViewActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSelf1;
    private Button btnSelf2;
    private Button btnSelf3;
    private Button btnSelf4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_view);

        initView();
    }

    private void initView() {
        btnSelf1 = (Button) findViewById(R.id.btn_self1);
        btnSelf2 = (Button) findViewById(R.id.btn_self2);
        btnSelf3 = (Button) findViewById(R.id.btn_self3);
        btnSelf4 = (Button) findViewById(R.id.btn_self4);

        initData();
    }

    private void initData() {
        btnSelf1.setOnClickListener(this);
        btnSelf2.setOnClickListener(this);
        btnSelf3.setOnClickListener(this);
        btnSelf4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.btn_self1:
               break;
           case R.id.btn_self2:
               break;
           case R.id.btn_self3:
               break;
           case R.id.btn_self4:
               break;
       }
    }
}
