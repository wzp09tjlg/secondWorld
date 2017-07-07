package com.example.wuzp.secondworld.view.SelfView.viewWidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by wuzp on 2017/7/7.
 * 自定义一个VIew 绘制一个Android的robot
 * 样子丑爆了 O(∩_∩)O哈哈~
 */
public class RobotView extends View {
    private Paint robotPaint;
    private Paint eyePaint;
    private Paint txtPaint;

    private int w = 1080;
    private int h = 1920;

    int tempHead = h * 4 / 15;
    int tempBody = h * 8 / 15;
    int tempFoot = h * 3 / 15;

    public RobotView(Context context){
        super(context);
        init(context);
    }

    public RobotView(Context context, AttributeSet attr){
        super(context,attr);
        init(context);
    }

    public RobotView(Context context,AttributeSet attr,int flag){
        super(context,attr,flag);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int modelWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modelHeight = MeasureSpec.getMode(heightMeasureSpec);

        //当然这里也可以针对mode进行判断 是精确 还是最大值 或者是其他值
        setMeasuredDimension(width,height);
    }

    //只有在这个方法中才能真正获得View的大小
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        w =getWidth();
        h = getHeight();

        tempHead = h * 4 / 15;
        tempBody = tempHead * 2;
        tempFoot = h * 3 / 15;
    }

    //一般自定义的View 都会在这里初始化基本属性 一些画笔 一些 自定义的属性 还有获取一些基本资源
    private void init(Context context){
        robotPaint = new Paint();
        robotPaint.setAntiAlias(true);//抗锯齿效果
        robotPaint.setColor(Color.GREEN);
        robotPaint.setStyle(Paint.Style.FILL);//绘制的是填充的效果 不是绘制的线

        eyePaint = new Paint();
        eyePaint.setColor(Color.WHITE);
        eyePaint.setAntiAlias(true);//抗锯齿
        eyePaint.setStyle(Paint.Style.FILL);//画笔是填充的样式
        eyePaint.setStrokeWidth(3f);//是因为没有设置 边的宽度么？？

        txtPaint = new Paint();
        txtPaint.setColor(Color.YELLOW);
        txtPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);
        canvas.save();
        //按照 竖直方向 头躯干脚的比例是 4:8:3的比例进行匹配

        int tempW = w / 2;
        //绘制头部
        canvas.drawCircle(tempW,tempW,tempW,robotPaint);


        //绘制躯干 和手
        canvas.drawRect(0,tempW,w,tempW + tempBody,robotPaint);
        //绘制脚
        canvas.drawRect(w/6,tempW + tempBody,w*2/6,tempW + tempBody + tempFoot,robotPaint);
        canvas.drawRect(w*4/6,tempW + tempBody,w*5/6,tempW + tempBody + tempFoot,robotPaint);
        canvas.restore();
    }
}
