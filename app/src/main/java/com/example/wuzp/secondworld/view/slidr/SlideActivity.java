package com.example.wuzp.secondworld.view.slidr;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.wuzp.secondworld.R;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

/**
 * Created by wuzp on 2017/6/14.
 */
public class SlideActivity extends AppCompatActivity {
   private Button btnOther;
    SlidrConfig mSlidrConfig;
    SlidrConfig.Builder mBuilder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSlider();//先设置配置 再在各个activity中添加处理

        setContentView(R.layout.activity_slide);
        int primary = getResources().getColor(R.color.primaryDark);
        int secondary = getResources().getColor(R.color.primaryLight);
        Slidr.attach(this, primary, secondary);
        initView();
    }

    private void initView(){
        btnOther = (Button)findViewById(R.id.btn_other);
        btnOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SlideActivity.this,SlideOtherActivity.class);
                startActivity(intent);
                Handler handler = new Handler();
                Message msg = null;
                handler.sendMessage(msg);//看loop中 处理消息时 当消息时是null 就直接退出
            }
        });
    }


    private void initSlider(){
        int primary = getResources().getColor(R.color.primaryDark);
        int secondary = getResources().getColor(R.color.accent);
        mBuilder = new SlidrConfig.Builder().primaryColor(primary)
                .secondaryColor(secondary)
                .scrimColor(Color.BLACK)
                .position(SlidrPosition.LEFT)
                .scrimStartAlpha(0.8f)
                .scrimEndAlpha(0f)
                .velocityThreshold(5f)
                .distanceThreshold(.35f);
        mSlidrConfig = mBuilder.build();
        Slidr.attach(this, mSlidrConfig);

        /**
         * .primaryColor(primary)//滑动时状态栏的渐变结束的颜色
         .secondaryColor(secondary)//滑动时状态栏的渐变开始的颜色
         .scrimColor(Color.BLACK)//滑动时Activity之间的颜色
         . position(SlidrPosition.LEFT)//从左边滑动
         .scrimStartAlpha(0.8f)//滑动开始时两个Activity之间的透明度
         .scrimEndAlpha(0f)//滑动结束时两个Activity之间的透明度
         .velocityThreshold(5f)//超过这个滑动速度，忽略位移限定值就切换Activity
         .distanceThreshold(.35f);//滑动位移占屏幕的百分比，超过这个间距就切换Activity
         * */
    }

}
