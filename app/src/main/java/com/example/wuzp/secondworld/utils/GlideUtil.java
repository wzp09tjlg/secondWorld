package com.example.wuzp.secondworld.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by wuzp on 2017/4/7.
  //简单封装了Glide的使用 绘制圆形图片
 */
public class GlideUtil {

    public static void setCircleImage(Context context,
                                      String url, int placeHolder,
                                      int errorHolder, ImageView imageView){
        Glide.with(context).load(url)
                .placeholder(placeHolder)
                .error(errorHolder)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .crossFade()
                .into(imageView);
    }

    public static void setCircleImage(Context context,
                                      int file,int placeHolder,
                                      int errorId,ImageView imageView){
        //Glide.with(context).load(file).asBitmap().into(imageView);
        Glide.with(context).load(file).crossFade().into(imageView);
    }
}
