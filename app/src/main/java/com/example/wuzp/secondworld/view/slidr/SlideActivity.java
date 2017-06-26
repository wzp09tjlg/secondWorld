package com.example.wuzp.secondworld.view.slidr;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    private static final String TAG = SlideActivity.class.getSimpleName();
   private Button btnOther;
    SlidrConfig mSlidrConfig;
    SlidrConfig.Builder mBuilder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSlider();//先设置配置 再在各个activity中添加处理

        setContentView(R.layout.activity_slide);
        int primary = getResources().getColor(R.color.green);
        int secondary = getResources().getColor(R.color.blue);
        Slidr.attach(this);//, primary, secondary);
        initView();
        Log.e(TAG,"onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG,"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG,"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy");
    }

    private void initView(){
        btnOther = (Button)findViewById(R.id.btn_other);
        btnOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SlideActivity.this,SlideOtherActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initSlider(){
        int primary = getResources().getColor(R.color.red);
        int secondary = getResources().getColor(R.color.yellow);
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


    private String getRunningActivityName(){
        ActivityManager activityManager=(ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity=activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        return runningActivity;
    }
}
