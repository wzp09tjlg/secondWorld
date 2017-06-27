package com.example.wuzp.secondworld.view.Db;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.wuzp.secondworld.R;

/**
 * Created by wuzp on 2017/6/27.
 * 暂时搁浅一下这个测试 因为还是感觉耗费时间了 应该做更多其他的事情去
 */
public class DbActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnOne;
    private Button btnTwo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_db);

        initView();
    }

    private void initView(){
        btnOne = (Button) findViewById(R.id.btn_db_one);
        btnTwo = (Button) findViewById(R.id.btn_db_two);

        initData();
    }

    private void initData(){
        btnOne.setOnClickListener(this);
        btnTwo.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_db_one:
                break;
            case R.id.btn_db_two:
                break;
        }
    }
}
