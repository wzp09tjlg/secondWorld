package com.example.wuzp.secondworld.view.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.wuzp.secondworld.R;

/**
 * Created by wuzp on 2017/5/26.
 */
public class MatrixImageView extends View {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap mBitmap;
    private float[] array = new float[20];

    public MatrixImageView(Context context){
        super(context);
    }

    public MatrixImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mBitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.icon_sky);
        invalidate();
    }

    public void setValues(float[] a) {
        for (int i = 0; i < 9; i++) {
            array[i] = a[i];
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = mPaint;
        canvas.drawBitmap(mBitmap, 0, 0, paint);
        //new 一个坐标变换矩阵
        Matrix cm = new Matrix();
        //为坐标变换矩阵设置响应的值
        cm.setValues(array);
        //按照坐标变换矩阵的描述绘图
        canvas.drawBitmap(mBitmap, cm, paint);
        Log.i("CMatrix", "--------->onDraw");

//        Paint mPaint = new Paint();                     //新建画笔对象
//        canvas.drawBitmap(mBitmap, 0, 0, mPaint);       //描画（原始图片）
//       　　
//       　　    ColorMatrix mColorMatrix = new ColorMatrix();   //新建颜色矩阵对象
//       　　    mColorMatrix.set(array);                        //设置颜色矩阵的值
//       　　    mPaint.setColorFilter(new ColorMatrixColorFilter(mColorMatrix));        //设置画笔颜色过滤器
//       　　    canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        /**
         *         Paint paint = mPaint;
         canvas.drawBitmap(mBitmap, 0, 0, paint);
         ColorMatrix mColorMatrix = new ColorMatrix();   //新建颜色矩阵对象
         mColorMatrix.set(array);                        //设置颜色矩阵的值
         mPaint.setColorFilter(new ColorMatrixColorFilter(mColorMatrix));        //设置画笔颜色过滤器　　
         //按照坐标变换矩阵的描述绘图
         canvas.drawBitmap(mBitmap, 0,0, paint);
         Log.i("CMatrix", "--------->onDraw");
         * */
    }
}
