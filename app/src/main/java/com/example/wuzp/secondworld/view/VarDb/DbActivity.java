package com.example.wuzp.secondworld.view.VarDb;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.view.VarDb.ormlite.OrmLiteActivity;

/**
 * Created by wuzp on 2017/6/29.
 * 比较常用的几种数据库框架
 * OrmLite
 * sugar
 * greenDAO
 * ActiveAndroid
 * sqlbrite
 * realm
 */
public class DbActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnStart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        initView();
    }

    private void initView(){
        btnStart = (Button)findViewById(R.id.btn_start);

        initData();
    }

    private void initData(){
        btnStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start:
                Intent intent = new Intent(this,OrmLiteActivity.class);
                startActivity(intent);
                break;
        }
    }
}
