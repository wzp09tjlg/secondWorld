package com.example.wuzp.secondworld.view.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.wuzp.secondworld.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzp on 2017/7/3.
 */
public class SelectItem extends LinearLayout implements View.OnClickListener {

    private List<ImageView> imageViews;
    private int count = 5; //默认是5个数据
    private int defualtClickPosition = 0;

    public SelectItem(Context context){
        super(context);
        init(context);
    }

    public SelectItem(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        init(context);
    }

    public SelectItem(Context context,AttributeSet attributeSet,int flag){
        super(context,attributeSet,flag);
        init(context);
    }

    private void init(Context context){
        setOrientation(LinearLayout.HORIZONTAL);
        getImageViews(context);

        /*ImageView imageView = new ImageView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.width =100;
        params.height = 100;
        imageView.setLayoutParams(params);
        imageView.setImageResource(R.drawable.icon_road);
        addView(imageView);*/
        for(int i=0;i<count;i++){
         addView(imageViews.get(i));
        }
    }

    private List<ImageView> getImageViews(Context context){
       Drawable drawable = context.getResources().getDrawable(R.drawable.shape_item);
        if(imageViews == null){
            imageViews = new ArrayList<>();
        }else{
            imageViews.clear();
        }
        for(int i=0;i<count;i++){
            ImageView imageView = new ImageView(context);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.width = 100;
            params.height = 100;
            imageView.setSelected(false);
            imageView.setLayoutParams(params);
            imageView.setImageDrawable(drawable);
            imageView.setTag(i);
            imageView.setOnClickListener(this);
            imageViews.add(imageView);
        }
        return imageViews;
    }

    @Override
    public void onClick(View v) {
        defualtClickPosition = (int)v.getTag();
        for(int i=0;i<count;i++){
            if(i<defualtClickPosition)
                 imageViews.get(i).setSelected(true);
            else
                imageViews.get(i).setSelected(false);
        }
    }

    /*************************************************/
    //暴露的方法

    public void setCount(int count) {
        if(count < 5) return;
        this.count = count;
        invalidate();
    }
}
