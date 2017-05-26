package com.example.wuzp.secondworld.view.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.example.wuzp.secondworld.R;

/**
 * Created by wuzp on 2017/5/26.
 */

public class TestImageView extends View {
    private Bitmap bitmap;
    private int sx,sy;
    private int w,h;
    private int bx,by;

    public TestImageView(Context context){
        super(context);
    }

    public TestImageView(Context context, AttributeSet attr){
        super(context,attr);
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_road);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getSx() {
        return sx;
    }

    public void setSx(int sx) {
        this.sx = sx;
    }

    public int getSy() {
        return sy;
    }

    public void setSy(int sy) {
        this.sy = sy;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getBx() {
        return bx;
    }

    public void setBx(int bx) {
        this.bx = bx;
    }

    public int getBy() {
        return by;
    }

    public void setBy(int by) {
        this.by = by;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(bitmap != null){
            drawImage(canvas,bitmap,sx,sy,w,h,bx,by);
        }
    }

    public static void drawImage(Canvas canvas, Bitmap blt, int x, int y, int w, int h, int bx, int by)
    {                                                       //x,y表示绘画的起点，
        Rect src = new Rect();// 图片
        Rect dst = new Rect();// 屏幕
        //src 这个是表示绘画图片的大小
        src.left = bx;   //0,0
        src.top = by;
        src.right = bx + w;// mBitDestTop.getWidth();,这个是桌面图的宽度，
        src.bottom = by + h;//mBitDestTop.getHeight()/2;// 这个是桌面图的高度的一半
        // 下面的 dst 是表示 绘画这个图片的位置
        dst.left = x;   //miDTX,//这个是可以改变的，也就是绘图的起点X位置
        dst.top = y;    //mBitQQ.getHeight();//这个是QQ图片的高度。 也就相当于 桌面图片绘画起点的Y坐标
        dst.right = x + w;  //miDTX + mBitDestTop.getWidth();// 表示需绘画的图片的右上角
        dst.bottom = y + h; // mBitQQ.getHeight() + mBitDestTop.getHeight();//表示需绘画的图片的右下角
        canvas.drawBitmap(blt, src, dst, null);//这个方法  第一个参数是图片，第二个参数是 绘画该图片需显示多少。也就是说你想绘画该图片的某一些地方，而不是全部图片，第三个参数表示该图片绘画的位置

        src = null;
        dst = null;
    }
}
