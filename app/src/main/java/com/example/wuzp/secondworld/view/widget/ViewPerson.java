package com.example.wuzp.secondworld.view.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by wuzp on 2017/7/3.
 * 自定义的控件
 * 在layout方法中 先测量在onlayout
 * 最后再draw
 */
public class ViewPerson extends ViewGroup {
    private Drawable head;
    private Drawable body;
    private Drawable foot;
    private ImageView imgHead;
    private ImageView imgBody;
    private ImageView imgFoot;

    public ViewPerson(Context context){
        super(context);
        init(context);
    }

    public ViewPerson(Context context, AttributeSet attrs){
        super(context,attrs);
        init(context);
    }

    public ViewPerson(Context context,AttributeSet attrs,int flag){
        super(context,attrs,flag);
        init(context);
    }

    private void init(Context context){
//       head = context.getResources().getDrawable(R.drawable.drawable_head);
//       body = context.getResources().getDrawable(R.drawable.drawable_body);
//       foot = context.getResources().getDrawable(R.drawable.drawable_foot);
//
//        imgHead = new ImageView(context);
//        imgHead.setImageDrawable(head);
//        ViewGroup.LayoutParams layoutParamsHead = new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//        imgBody = new ImageView(context);
//        imgBody.setImageDrawable(body);
//        ViewGroup.LayoutParams layoutParamsBody = new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//        imgFoot = new ImageView(context);
//        imgFoot.setImageDrawable(foot);
//        ViewGroup.LayoutParams layoutParamsFoot = new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        //如果不设置layoutParam会是怎样 呢？
        //addView(head,layoutParamsHead);
    }

    //自定义的ViewGroup需要重写这个方法 进行排版，如果是集成LinerLayout 只需要自己设置方向就可以了 布局自己还给你排版
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        try{
            //就是让子View一个一个的摆在ViewGroup中
            //现在就是想绘制一个小人，所以这里就只是一溜的向下摆就可以了
            int childCount = getChildCount();
            int left = 0;
            int top = 0;
            for(int i=0;i<childCount;i++){
                View child = getChildAt(i);
                left = 0;//因为只涉及到垂直方向 所以这里讲水平方向的值 置为0 只针对垂直方向的值做累计
                if (child.getVisibility() == View.GONE)
                {
                    continue;
                }
                ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) child
                        .getLayoutParams();

                //计算childView的left,top,right,bottom
                int lc = left;
                int tc = top;
                int rc =lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                child.layout(lc,tc,rc,bc);

                //摆放的是view的左上角位置
                left += child.getMeasuredWidth() ;
                top += child.getMeasuredHeight() ;
            }
        }catch (Exception e){
            Log.e("wzp","layout error:" + e.getMessage());
        }
    }

    //在自定义的类中 可以自己计算控件的宽高 也可以使用系统计算控件的宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        try{
            // 获得它的父容器为它设置的测量模式和大小
            int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
            int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
            int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
            int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

            int width = 0;
            int height = 0;

            //区分是什么模式 精确模式还是最大模式 只要是有一个方向不是 精确的，那么就要计算整个view的宽高
            if((modeWidth != MeasureSpec.EXACTLY) || modeHeight != MeasureSpec.EXACTLY){
                int totalChildCount = getChildCount();
                for(int i=0;i<totalChildCount;i++){
                    View child = getChildAt(i);
                    measureChild(child,widthMeasureSpec,heightMeasureSpec);//计算子View的宽和高
                    // 得到child的lp
                    ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) child
                            .getLayoutParams();
                    // 当前子空间实际占据的宽度
                    int childWidth = child.getMeasuredWidth() ;
                    // 当前子空间实际占据的高度
                    int childHeight = child.getMeasuredHeight() ;
                    height += childHeight;

                    if(childWidth > width || width == 0){
                        width = childWidth;
                    }
                }
            }
            setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY) ? sizeWidth
                    : width, (modeHeight == MeasureSpec.EXACTLY) ? sizeHeight
                    : height);
        }catch (Exception e){
            Log.e("wzp","measure error:" + e.getMessage());
        }
    }
}

