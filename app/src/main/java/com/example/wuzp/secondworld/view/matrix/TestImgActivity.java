package com.example.wuzp.secondworld.view.matrix;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.view.widget.TestImageView;

/**
 * Created by wuzp on 2017/5/26.
 */
public class TestImgActivity extends AppCompatActivity {
    private Button btnTest;
    private TestImageView tImg;

    //资源类型的属性 应该在生命周期中 获取，不是常量 所以在 定义时 获取就报错了
    private Bitmap bitmap = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_img);
        initView();
    }

    //ViewCompat.postInvalidateOnAnimation(this); //验证这行代码是执行什么效果

    private void initView(){
        bitmap =  BitmapFactory.decodeResource(getResources(),R.drawable.icon_road);// getResources().getDrawable(R.drawable.icon_road);
        tImg = (TestImageView)findViewById(R.id.img_test);
        btnTest = (Button)findViewById(R.id.btn_test);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tImg.setBitmap(bitmap);
                tImg.setSx(100);//截取资源图的 开始位置
                tImg.setSx(300);
                tImg.setW(400);//截取的长宽
                tImg.setH(400);
                tImg.setBx(0); //展示在屏幕上的 位置
                tImg.setBy(0);
                tImg.postInvalidate();
            }
        });

    }

}
