package com.example.wuzp.secondworld.view.SelfView.viewWidget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.example.wuzp.secondworld.R;

import static android.graphics.Canvas.CLIP_SAVE_FLAG;

/**
 * Created by wuzp on 2017/7/6.
 * 自定义View的第二步  继承已经存在的View
 * 绘制一个圆圈的imageView
 */
public class CircleImageView extends AppCompatImageView {

    private Bitmap src;
    private Bitmap mask;

    private Paint mPaint;//绘制robot的画笔
    private Paint paintEye;//绘制眼睛的画笔
    private Paint tempPaint;//绘制文字的画笔

    private int centerW;
    private int centerH;

    public CircleImageView(Context context){
        super(context);
        init(context);
    }

    public CircleImageView(Context context, AttributeSet attr){
        super(context,attr);
        init(context);
    }

    public CircleImageView(Context context,AttributeSet attr,int flag){
        super(context,attr,flag);
        init(context);
    }

    private void init(Context context){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//抗锯齿效果
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);//绘制的是填充的效果 不是绘制的线


        paintEye = new Paint();
        paintEye.setColor(Color.WHITE);
        paintEye.setAntiAlias(true);//抗锯齿
        paintEye.setStyle(Paint.Style.FILL);//画笔是填充的样式
        paintEye.setStrokeWidth(3f);//是因为没有设置 边的宽度么？？

        tempPaint = new Paint(Color.RED);
        tempPaint.setAntiAlias(true);
        src =BitmapFactory.decodeResource(context.getResources(),R.drawable.icon_sky);
    }

    //只有在这个方法中 才能真正的获取长宽
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerW = getMeasuredWidth();
        centerH = getMeasuredHeight();
        mask = Bitmap.createBitmap(getMeasuredWidth(),getMeasuredHeight(), Bitmap.Config.ARGB_4444);
    }

    private static final int LAYER_FLAGS = Canvas.MATRIX_SAVE_FLAG |
            CLIP_SAVE_FLAG| Canvas.HAS_ALPHA_LAYER_SAVE_FLAG
            | Canvas.FULL_COLOR_LAYER_SAVE_FLAG
            | Canvas.CLIP_TO_LAYER_SAVE_FLAG;

    /***
     * 如何理解画布 其实就可以理解成画画的台子，就是画布。
     * 你可以在画布上进行绘制，也可以通过画布来绘制你希望创建的东西
     * 譬如想要创建的一张bitmap 图，经过画布就可以绘制出 这样一张图。
     * */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);

        /*Canvas cc = new Canvas(mask);
        cc.drawCircle(getMeasuredWidth() / 2,getMeasuredHeight() / 2,getMeasuredWidth() / 2,tempPaint);

        //创建了一个新的透明层 然后绘制Dest
        canvas.saveLayer(0,0,getMeasuredHeight(),getMeasuredHeight(),null,Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(mask,0,0,mPaint);

        //绘制在画布上的SRC
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(src,0,0,mPaint);
        mPaint.setXfermode(null);

        canvas.restore();//将两层合为一层*/

        /*//这里画布移动之后 对于之后的出现的绘制 还有新创建的图层有影响
        canvas.translate(10, 10);
        mPaint.setColor(Color.RED);
        canvas.drawCircle(75, 75, 75, mPaint);
        canvas.saveLayerAlpha(0, 0, 200, 200, 0x88, LAYER_FLAGS);
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(125, 125, 75, mPaint);//如果之前的移动对于新绘制的层没有影响 那么这里就应该绘制靠边
        //看效果是 画布移动 或者偏移 或者 处理之后对于之后的 所有无论是 在原来的基础上还是画布新创建的画布上 都是有影响的
        canvas.restore();*/

        //准备绘制一个android的logo 生气的Android log
        canvas.translate(centerW / 2,centerH / 2);//在中间开始绘制 绘制的view 在于右下角
        //绘制一个圆弧
        /**
         * public void drawArc(float left, float top, float right, float bottom, float startAngle,
         float sweepAngle, boolean useCenter, @NonNull Paint paint) {
         native_drawArc(mNativeCanvasWrapper, left, top, right, bottom, startAngle, sweepAngle,
         useCenter, paint.getNativeInstance());
         }
         * */
        //画头部
        canvas.save();//保存之前绘制的东西 保证接下来绘制的东西 不会影响到之前的绘制上
        //canvas.drawArc(0f,0f,80f,40f, (float) Math.PI*1f / 2f,(float)Math.PI*1f / 2f,false,mPaint);
        //drawArc(RectF oval, float startAngle, float sweepAngle, boolean useCenter, Paint paint)//画弧
        //绘制一个圆
        //RectF rectF = new RectF(0,0,80,40);
        //canvas.drawArc(rectF,-1.65f,1.65f,false,mPaint);
        canvas.drawCircle(40f,0f,40f,mPaint);
        //绘制眼睛  在这里进行绘制 坐标计算是对的 但是效果却是很糟糕的表现
        canvas.drawCircle(20f,-10f,5,paintEye);
        canvas.drawCircle(50f,-10f,5,paintEye);

        //画躯干
        canvas.drawRect(0f,40f,80f,110f,mPaint);
        //画脚
        canvas.drawRect(11f,110f,19f,150f,mPaint);
        canvas.drawRect(61f,110f,69f,150f,mPaint);

        //画手
        canvas.drawRect(-19f,45f,-11f,80f,mPaint);
        canvas.drawRect(91f,45f,98f,80f,mPaint);

        //写字 在绘制的过程中 改变画笔的颜色 同时也会改变之前使用这个画笔绘制的东西？？？是不是有些奇怪 (#‵′)凸
        canvas.drawText("我是Android Robot",0f,180f,tempPaint);
        canvas.restore();//将绘制的东西 与之前的绘制的东西 进行合并

        /**
         // 设置画布的颜色为透明
         canvas.drawColor(Color.TRANSPARENT);
         // 划出你要显示的圆
         Canvas cc = new Canvas(mask);
         cc.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, getMeasuredHeight() / 2, mPaint);
         // 这个方法相当于PS新建图层，下面你要做的事就在这个图层上操作
         canvas.saveLayer(0, 0, getMeasuredWidth(), getMeasuredHeight(), null, Canvas.ALL_SAVE_FLAG);
         // 先绘制遮罩层
         canvas.drawBitmap(mask, 0, 0, mPaint);
         // 设置混合模式
         mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
         // 再绘制src源图
         canvas.drawBitmap(src, 0, 0, mPaint);
         // 还原混合模式
         mPaint.setXfermode(null);
         // 还原画布，相当于Ps的合并图层
         canvas.restore();
         * */
    }

    /**
     * function drawScreen () {
     * // x,y => 圆心坐标点
     * // r => 圆弧半径
     * var arc = {
     * x: myCanvas.width / 2,
     * y: myCanvas.height / 2,
     * r: 100 },
     * w = myCanvas.width,
     * h = myCanvas.height;
     * ctx.save();
     * ctx.lineWidth = 10;
     * ctx.strokeStyle = '#e3f';
     * // startRad => getRads(-45)
     * // endRad => getRads(45)
     * // 顺时针旋转
     * ctx.beginPath();
     * ctx.arc(arc.x, arc.y, arc.r,getRads(-45),getRads(45));
     * ctx.stroke();
     * // startRad => getRads(-135)
     * // endRad => getRads(135)
     * // 逆时针旋转
     * ctx.beginPath();
     * ctx.strokeStyle = "#f36";
     * ctx.arc(arc.x, arc.y, arc.r,getRads(-135),getRads(135),true);
     * ctx.stroke();
     * ctx.restore();
     * }
     * */

    /***
     *  将要学习的注解的知识
    @IntDef(flag = true,
            value = {
                    MATRIX_SAVE_FLAG,
                    CLIP_SAVE_FLAG,
                    HAS_ALPHA_LAYER_SAVE_FLAG,
                    FULL_COLOR_LAYER_SAVE_FLAG,
                    CLIP_TO_LAYER_SAVE_FLAG,
                    ALL_SAVE_FLAG
            })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Saveflags {}
     * */
}
