package com.example.wuzp.secondworld.view.widget.SView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wuzp on 2017/6/27.
 * tint
 * 其他绘制的东西设置
 */
public class SView extends View {
    private Paint mPaint;
    private Bitmap mBitmap;

    public SView(Context context){
        super(context);
        init(context);
    }

    public SView(Context context, AttributeSet attr){
        super(context,attr);
        init(context);
    }

    public SView(Context context,AttributeSet attri,int flag){
       super(context,attri,flag);
        init(context);
    }

    private void init(Context context){

    }
}
