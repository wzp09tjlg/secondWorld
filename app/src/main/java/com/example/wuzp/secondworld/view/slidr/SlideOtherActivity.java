package com.example.wuzp.secondworld.view.slidr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.wuzp.secondworld.R;
import com.r0adkll.slidr.Slidr;

/**
 * Created by wuzp on 2017/6/14.
 */

public class SlideOtherActivity extends AppCompatActivity {
   private Button btnAdd;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_slide_other);
        Slidr.attach(this);
        initView();
    }

    private void initView(){
        btnAdd = (Button)findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        FragmentManager fm = getSupportFragmentManager();
        SlideFragment fragment = new SlideFragment();
        FragmentTransaction transaction  = fm.beginTransaction();
        transaction.add(R.id.slidable_content,fragment);
        transaction.commit();
    }
}
