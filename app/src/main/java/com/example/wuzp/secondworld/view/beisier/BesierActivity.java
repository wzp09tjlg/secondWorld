package com.example.wuzp.secondworld.view.beisier;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.view.widget.Besier.BesierO;
import com.example.wuzp.secondworld.view.widget.Besier.BesierT;
import com.example.wuzp.secondworld.view.widget.Besier.BesierTT;

/**
 * Created by wuzp on 2017/5/26.
 *
 */

public class BesierActivity extends AppCompatActivity implements View.OnClickListener {
   private BesierO besierO;
   private BesierT besierT;
    private Button btnLeft,btnRight;
   private BesierTT besierTT;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_besier);
        initView();
    }

    private void initView(){
       besierO = $(R.id.besierO);
       besierT = $(R.id.besierT);
        besierTT = $(R.id.besierTT);
        btnLeft = $(R.id.btn_left);
        btnRight = $(R.id.btn_right);

        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);

        initData();
    }

    private void initData(){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_left:
                besierT.setMode(true);
                break;
            case R.id.btn_right:
                besierT.setMode(false);
                break;
        }
    }

    private <T> T $(int id){
        return (T)findViewById(id);
    }

    private void test(){ //打开系统打开系统的设置页面
        Intent intent = new Intent(Settings.ACTION_DISPLAY_SETTINGS);
        startActivity(intent);
    }
}
