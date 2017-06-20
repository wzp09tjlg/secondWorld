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
public class FlagThreeActivity extends AppCompatActivity {
    private Button btnBaseThree;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flag_three);

        initView();
    }

    private void initView(){
        btnBaseThree =(Button)findViewById(R.id.btn_flag_three);
        btnBaseThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlagThreeActivity.this,FlagBaseActivity.class);
                //这个flag 会把这个activity 单独放在一个任务栈当中，会出现点击回退时 直接退出到桌面的情况
                intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                startActivity(intent);
            }
        });
    }
}
