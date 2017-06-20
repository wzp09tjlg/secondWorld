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

public class FlagTwoActivity extends AppCompatActivity {
    private Button btnBaseTwo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flag_two);

        initView();
    }

    private void initView(){
        btnBaseTwo =(Button)findViewById(R.id.btn_flag_two);
        btnBaseTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlagTwoActivity.this,FlagThreeActivity.class);
                startActivity(intent);
            }
        });
    }
}
