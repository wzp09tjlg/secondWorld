package com.example.wuzp.secondworld.view.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

import com.nineoldandroids.animation.ObjectAnimator;

/**
 * Created by wuzp on 2017/6/6.
 * 想做一个能够随着手指下拉距离旋转的imageView
 */

public class RotateImageView extends AppCompatImageView {

    private float LENGTH = 30;

    private int mMinimumVelocity;
    private int mMaximumVelocity;
    private VelocityTracker velocityTracker = null;

    //velocity 速率
    private int startX;
    private int startY;

    private int moveX;
    private int moveY;

    //坐标
    private int startPX;
    private int startPY;
    private int movePX;
    private int movePY;


    public RotateImageView(Context context){
        super(context);
        init(context);
    }

    public RotateImageView(Context context, AttributeSet attr){
        super(context,attr);
        init(context);
    }

    private void init(Context context){
        final ViewConfiguration configuration = ViewConfiguration.get(context);//fling操作参数需求
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
    }

    //触摸事件 一般是
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(velocityTracker != null){
            velocityTracker.addMovement(event);
        }
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(velocityTracker == null){
                    velocityTracker = VelocityTracker.obtain();
                }
                velocityTracker.computeCurrentVelocity(1000,mMaximumVelocity);//表示单位时间(毫秒) 速度大于max的都以max的速率显示
                velocityTracker.addMovement(event);

                startX = (int)velocityTracker.getXVelocity();
                startY = (int)velocityTracker.getYVelocity();

                startPX = (int)event.getX();
                startPY = (int)event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final VelocityTracker mTracker = velocityTracker;

                moveX = (int)mTracker.getXVelocity();
                moveY = (int)mTracker.getYVelocity();
                float offset1 = moveY - startY;//速率的计算

                movePX = (int)event.getX();
                movePY = (int)event.getY();

                float offset = movePY - startPY;

                setRotation(offset / LENGTH);
                invalidate();
                //pulldownImage.setRotation(180 * offset / PixelUtil.dp2px(140));
                break;
            case MotionEvent.ACTION_UP:
                if(velocityTracker != null){
                    velocityTracker.recycle();
                    velocityTracker = null;
                }
                //恢复原状
                int off = (movePY - startPY) % (int)LENGTH;
                float tempOff = off / LENGTH;
                if(tempOff != 0){
                    //ObjectAnimator animator = new ObjectAnimator(RotateImageView.this,"rotationY",(int)tempOff,0);
                    /*animator.setFloatValues(tempOff,0);
                    animator.setInterpolator(new AccelerateDecelerateInterpolator() {
                        @Override
                        public float getInterpolation(float input) {
                            setRotation(input);
                            invalidate();
                            return 0;
                        }
                    });
                    animator.setDuration(50);//设置最后的复原的动画的复原时间*/
                    ObjectAnimator animator = ObjectAnimator.ofFloat(this, "scaleY", 0, 3, 1);
                    animator.setDuration(50);
                    animator.start();//使用属性动画来 更改最后的变化
                }
                break;
        }
        return true;
    }
}
