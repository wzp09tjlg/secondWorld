package com.example.wuzp.secondworld.view.flag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.wuzp.secondworld.R;

/**
 * Created by wuzp on 2017/6/20.
 */

public class FlagOneActivity extends AppCompatActivity {
    private Button btnBaseOne;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flag_one);

        initView();
    }

    private void initView(){
        btnBaseOne =(Button)findViewById(R.id.btn_flag_one);
        btnBaseOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlagOneActivity.this,FlagTwoActivity.class);
                startActivity(intent);
            }
        });
    }
}
