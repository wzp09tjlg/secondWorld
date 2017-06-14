package com.example.wuzp.secondworld.view.wangdou.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

import com.example.wuzp.secondworld.view.wangdou.interf.PageViewListener;
import com.example.wuzp.secondworld.view.wangdou.model.parse.book.BookSummary;
import com.example.wuzp.secondworld.view.wangdou.read.PageFactory;
import com.example.wuzp.secondworld.view.wangdou.utils.DrawUtils;
import com.example.wuzp.secondworld.view.wangdou.utils.PageGesture;
import com.example.wuzp.secondworld.view.wangdou.utils.PixelUtil;
import com.example.wuzp.secondworld.view.wangdou.utils.ThemeManage;

import java.util.Map;

/**
 * 主要处理翻页功能
 * 以及代理pagefactory处理阅读状态的变化，会有更好的处理方式，后续处理。。。。
 * Created by zyb on 2016/10/19.
 */
public class PageView extends View {
    /**
     * 页面状态
     */
    boolean isTouchCentral = false;//点击位置判断
    boolean isSummaryExist = false;//是否存在划线
    boolean isEditState = false;//头部尾部控制是否显示
    boolean isTTSSetExist = false;//tts窗体存在
    boolean canSlide = true;//是否能滑动
    boolean isScroolMark = true; //是否可以滑动加载书签
    boolean isSlideing = true;//是否正在滑动
    boolean isLongClickState = false;//是否是长按状态
    boolean isVaildSlide = false;//是否是有效操作
    boolean isAddMarkStart = false;//是否处于添加书签状态
    boolean isMoveUpdateStart = true;//move时只做一次翻页操作
    boolean isLongClickListener = false;//是否处于长按监听
    boolean isTouchSummary = false;//是否点击在划线区域
    boolean mIsRTandLB; // 是否属于右上左下
    boolean isCache;//是否正在缓存图片
    boolean isStartAnim = false;//是否执行动画
    boolean isCancelTurnPage = false;//判断是否取消翻页
    boolean isFirstPage;
    boolean isLastPage;
    boolean skipEnd = false;
    int isTouchArea;
    /**
     * 翻页模式
     */
    public static final int BROWSE_MODE_EMULATION = 0; // 仿真翻页
    public static final int BROWSE_MODE_SCROOL_V = 1; // 垂直滑动翻页
    public static final int BROWSE_MODE_SCROOL_H = 2; // 左右切换翻页
    private int browseMode = BROWSE_MODE_SCROOL_H;
    /**
     * 触摸时间
     */
    private long touchStartTime;

    /**
     * 当长按事件发生时，要触发的任务
     */
    private LongPressRunnable longPressRunnable = new LongPressRunnable();
    private int startPos;
    private int endPos;
    private float minY;
    private float maxY;
    /**
     * 滑动翻页相关
     */
    //上下滑动需要计算值
    int lastTonchY;//最后一点触摸位置Y坐标
    float downTouchX;//点击的位置的X坐标
    int offsetheight = 0;//绘制需求偏移量
    int offset = 0;
    private int mMinimumVelocity;
    private int mMaximumVelocity;
    private VelocityTracker mVelocityTracker;
    /**
     * 防抖动需要参数
     */
    float downX;
    float downY;

    private int mWidth;
    private int mHeight;
    private int contentHeight;
    private PageFactory pageFactory;
    private Bitmap curBitmap, nextBitmap, cacheBitmap, totalBitmap, titleBgBitmap;//当前页、下一页、滑动翻页的第三页、滑动翻页合成图、滑动翻页title部分背景图。
    private Canvas curCanvas, nextCanvas, cacheCanvas, titleBgCanvas;

    /**
     * 仿真翻页需求
     */
    private int mCornerX = 0; // 拖拽点对应的页脚
    private int mCornerY = 0;
    private Path mPath0;
    private Path mPath1;

    PointF mTouch = new PointF(); // 拖拽点
    PointF mBezierStart1 = new PointF(); // 贝塞尔曲线起始点
    PointF mBezierControl1 = new PointF(); // 贝塞尔曲线控制点
    PointF mBeziervertex1 = new PointF(); // 贝塞尔曲线顶点
    PointF mBezierEnd1 = new PointF(); // 贝塞尔曲线结束点

    PointF mBezierStart2 = new PointF(); // 另一条贝塞尔曲线
    PointF mBezierControl2 = new PointF();
    PointF mBeziervertex2 = new PointF();
    PointF mBezierEnd2 = new PointF();

    /**
     * 阴影的默认宽度
     */
    private static final int SHADOW_DEF_DP = 20;
    float mMiddleX;
    float mMiddleY;
    float mDegrees;
    float mTouchToCornerDis;
    ColorMatrixColorFilter mColorMatrixFilter;
    Matrix mMatrix;
    float[] mMatrixArray = {0, 0, 0, 0, 0, 0, 0, 0, 1.0f};
    int bgColor;

    float mMaxLength = (float) Math.hypot(mWidth, mHeight);
    int[] mBackShadowColors;
    int[] mFrontShadowColors;
    GradientDrawable mBackShadowDrawableLR;
    GradientDrawable mBackShadowDrawableRL;
    GradientDrawable mFolderShadowDrawableLR;
    GradientDrawable mFolderShadowDrawableRL;

    GradientDrawable mFrontShadowDrawableHBT;
    GradientDrawable mFrontShadowDrawableHTB;
    GradientDrawable mFrontShadowDrawableVLR;
    GradientDrawable mFrontShadowDrawableVRL;
    GradientDrawable mSilideShadowDrawable;

    Scroller mScroller;
    Paint mPaint;

    Bitmap mCurPageBitmap = null; // 当前页
    Bitmap mNextPageBitmap = null; // 下一页
    Bitmap cachePageBitmap = null;
    private float mDefaultShadow;
    PageGesture pageGesture;
    PageViewListener pageViewListener;

    public PageView(Context context) {
        super(context);
    }

    public PageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(Context context, PageFactory pageFactory) {
        mPath0 = new Path();
        mPath1 = new Path();
        createDrawable();
        mDefaultShadow = PixelUtil.dp2px(SHADOW_DEF_DP);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);

        ColorMatrix cm = new ColorMatrix();
        float array[] = {0.55f, 0, 0, 0, 80.0f, 0, 0.55f, 0, 0, 80.0f, 0, 0,
                0.55f, 0, 80.0f, 0, 0, 0, 0.2f, 0};
        cm.set(array);

        mColorMatrixFilter = new ColorMatrixColorFilter(cm);
        mMatrix = new Matrix();
        mScroller = new Scroller(context, new LinearInterpolator());

        mTouch.x = 0.01f; // 不让x,y为0,否则在点计算时会有问题
        mTouch.y = 0.01f;

        this.pageFactory = pageFactory;
        contentHeight = pageFactory.getContentHeight();//针对上下滑动需求参数
        this.mHeight = pageFactory.getScreenHeight();
        this.mWidth = pageFactory.getScreenWidth();
        bgColor = pageFactory.getBgColor();
        curBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.RGB_565);
        nextBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.RGB_565);


        curCanvas = new Canvas(curBitmap);
        nextCanvas = new Canvas(nextBitmap);

        final ViewConfiguration configuration = ViewConfiguration.get(context);//fling操作参数需求
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();

        pageGesture = new PageGesture(mWidth, mHeight);//点击区域判断类
    }

    private void createDrawable() {
        int[] color = {0x222222, 0x60222222};
        mFolderShadowDrawableRL = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, color);
        mFolderShadowDrawableRL.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        mFolderShadowDrawableLR = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, color);
        mFolderShadowDrawableLR.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        mBackShadowColors = new int[]{0x80111111, 0x111111};
        mBackShadowDrawableRL = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, mBackShadowColors);
        mBackShadowDrawableRL.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        mBackShadowDrawableLR = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, mBackShadowColors);
        mBackShadowDrawableLR.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        mFrontShadowColors = new int[]{0x60222222, 0x222222};
        mFrontShadowDrawableVLR = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, mFrontShadowColors);
        mFrontShadowDrawableVLR.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        mFrontShadowDrawableVRL = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, mFrontShadowColors);
        mFrontShadowDrawableVRL.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        mFrontShadowDrawableHTB = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, mFrontShadowColors);
        mFrontShadowDrawableHTB.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        mFrontShadowDrawableHBT = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, mFrontShadowColors);
        mFrontShadowDrawableHBT.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        int[] silideColors = new int[]{0x60222222, 0x222222};
        mSilideShadowDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, silideColors);
        mSilideShadowDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
    }

    private void setBitmaps(Bitmap curBitmap, Bitmap nextBitmap, Bitmap cacheBitmap) {
        cachePageBitmap = cacheBitmap;
        mCurPageBitmap = curBitmap;
        mNextPageBitmap = nextBitmap;
        if (browseMode == BROWSE_MODE_SCROOL_V) {
            final Bitmap memBm = Bitmap.createBitmap(mWidth, contentHeight / 3 * 7, Bitmap.Config.RGB_565);
            final Canvas c = new Canvas(memBm);
            Paint paint = new Paint();
            c.drawRect(0, 0, mWidth, contentHeight / 3 * 7, paint);
            if (cachePageBitmap != null) {
                c.drawBitmap(cachePageBitmap, new Rect(0, contentHeight / 3, mWidth, contentHeight), new RectF(0, 0, mWidth, contentHeight / 3 * 2), null);
            }
            if (mCurPageBitmap != null) {
                c.drawBitmap(mCurPageBitmap, 0, contentHeight / 3 * 2, null);
            }
            if (mNextPageBitmap != null) {
                c.drawBitmap(mNextPageBitmap, new Rect(0, 0, mWidth, contentHeight / 3 * 2), new RectF(0, contentHeight / 3 * 2 + contentHeight, mWidth, contentHeight / 3 * 7), null);
            }
            totalBitmap = Bitmap.createBitmap(memBm);
            memBm.recycle();
        }
    }

    private void setBitmaps(Bitmap curBitmap, Bitmap nextBitmap) {
        setBitmaps(curBitmap, nextBitmap, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(bgColor);
        if (browseMode == BROWSE_MODE_EMULATION) {
            if (pageFactory.getPageNum() != 0 && isFirstPage) {
                isFirstPage = false;
            }
            calcPoints();
            drawCurrentPageArea(canvas, mCurPageBitmap, mPath0);
            drawNextPageAreaAndShadow(canvas, mNextPageBitmap);
            drawCurrentBackArea(canvas, mCurPageBitmap);
        } else if (browseMode == BROWSE_MODE_SCROOL_V) {
            if (isFirstPage && !isCache) {
                if (offset > 0) {
                    if (offsetheight >= 0) {
                        offsetheight = 0;
                    }
                }
            }
            if (pageFactory.getCurpagePageName() != 0 && isFirstPage && offset < 0) {
                isFirstPage = false;
            }
            if (isLastPage && !isCache) {
                if (offset < 0) {
                    if (offsetheight <= 0) {
                        offsetheight = 0;
                    }
                }
            }
            if (pageFactory.getCurpagePageName() != pageFactory.getCurpageTotalPage() && isLastPage && offset > 0) {
                isLastPage = false;
            }
            int offsetTotal = offsetheight + (mHeight - contentHeight) / 2;
            canvas.save();
            canvas.clipRect(0, (mHeight - contentHeight) / 2, mWidth, contentHeight + (mHeight - contentHeight) / 2);
            if (totalBitmap != null && !totalBitmap.isRecycled()) {
                canvas.drawBitmap(totalBitmap, 0, offsetTotal - contentHeight / 3 * 2, null);
            }
            canvas.restore();
            drawTitleBar(canvas);
            drawBottom(canvas);
        } else {
            if (pageFactory.getPageNum() != 0 && isFirstPage) {
                isFirstPage = false;
            }
            drawCurrentPage(canvas, mCurPageBitmap);
            drawNextPage(canvas, mNextPageBitmap);
            drawCurPageShadow(canvas);
        }
    }

    private void drawBottom(Canvas canvas) {
        canvas.drawBitmap(titleBgBitmap, 0, mHeight - PixelUtil.dp2px(48), pageFactory.getPaintOther());
        DrawUtils.drawCurpage(canvas, pageFactory.getCurpagePageName(), pageFactory.getCurpageTotalPage(), mWidth - PixelUtil.dp2px(16), mHeight - PixelUtil.dp2px(16), pageFactory.getPaintOther());
        DrawUtils.drawTime(canvas, PixelUtil.dp2px(16) + 60, mHeight - PixelUtil.dp2px(16), pageFactory.getPaintOther());
        DrawUtils.drawElectric(canvas, PixelUtil.dp2px(16), mHeight - PixelUtil.dp2px(16), pageFactory.getElectric(), pageFactory.getTextColor());
    }

    private void drawTitleBar(Canvas canvas) {
        String title = pageFactory.getCurTitle();
        if (title != null) {
            canvas.drawBitmap(titleBgBitmap, 0, 0, pageFactory.getPaintOther());
            Paint.FontMetrics fontMetrics = pageFactory.getPaintOther().getFontMetrics();
            DrawUtils.drawTitle(canvas, title, PixelUtil.dp2px(16), PixelUtil.dp2px(16) + (fontMetrics.descent - fontMetrics.ascent), pageFactory.getPaintOther());
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (longPressRunnable != null) {
            if (event.getAction() == MotionEvent.ACTION_DOWN
                    && event.getPointerCount() == 1) {
                removeCallbacks(longPressRunnable);
                downX = event.getX();
                downY = event.getY();
                postCheckForLongTouch(downX, downY);
                isLongClickListener = true;
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                if (pageGesture.isaVaildMove(event, downX, downY)) {
                    removeCallbacks(longPressRunnable);
                    isLongClickListener = false;
                }
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                removeCallbacks(longPressRunnable);
                isLongClickListener = false;
            }
        }
        return super.dispatchTouchEvent(event);
    }

    private void postCheckForLongTouch(float x, float y) {
        longPressRunnable.setPressLocation(x, y);
        postDelayed(longPressRunnable, 500);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        {
            //上下滚动翻页时，上抛监听使用
            if (browseMode == BROWSE_MODE_SCROOL_V) {
                if (mVelocityTracker == null) {
                    mVelocityTracker = VelocityTracker.obtain();
                }
                mVelocityTracker.addMovement(event);
            }
            //down事件
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                //当页面处于正在划线状态时，移除长按监听，移除划线状态并复原pageview状态，拦截down事件
                if (isSummaryExist) {
                    pageViewListener.dismissSummaryPopup();
                    removeCallbacks(longPressRunnable);
                    return false;
                }
                //防止频繁点击，当点击间隔小于200毫秒时，拦截事件
                if (System.currentTimeMillis() - touchStartTime < 200) {
                    removeCallbacks(longPressRunnable);
                    return false;
                }
                // pagefactory识别手势标志位
                pageFactory.setHandSkipPage(true);
                //当页面处于编辑状态时，移除长按监听，移除编辑状态，复原控件状态，拦截down事件
                if (isEditState) {
                    pageViewListener.dismissTitleBarWithBottomBar();
                    isEditState = false;
                    canSlide = true;
                    removeCallbacks(longPressRunnable);
                    pageFactory.setHandSkipPage(false);
                    return false;
                }
                //处于长按状态时，直接传递事件
                if (isLongClickState) {
                    return true;
                }
                //当翻页模式不是上下翻页时，通过点击位置计算是否是划线，并记录isTouchSummary
                if (browseMode != BROWSE_MODE_SCROOL_V) {
                    if (pageFactory.mathSummaryByCoordinate(event.getX(), event.getY()) != null) {
                        isTouchSummary = true;
                        minY = (int) (pageFactory.mathSummaryByCoordinate(event.getX(), event.getY()).get("minY"));
                        maxY = (int) (pageFactory.mathSummaryByCoordinate(event.getX(), event.getY()).get("maxY"));
                    }
                }
                //在每次点击时将是否取消翻页默认为false
                isCancelTurnPage = false;
                mTouch.x = event.getX();
                mTouch.y = event.getY();
                lastTonchY = (int) (event.getY());
                downTouchX = event.getX();
                touchStartTime = System.currentTimeMillis();
                abortAnimation();//结束动画
                //防止下一次点击时 页面出现的闪屏
                if (browseMode != BROWSE_MODE_SCROOL_V) {
                    setBitmaps(nextBitmap, nextBitmap);
                }
                isTouchArea = pageGesture.judgeTouchArea(event);
                isTouchCentral = isTouchArea == PageGesture.TOUCH_TOOLBAR_AREA;
                calcCornerXY(event);//计算页脚位置，如果有手势滑动在move事件中重置x轴页脚，y轴不再变化，没有滑动则在up事件中直接使用，做到优先判断手势再判断点击位置
                return true;
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                //判断是否是有效操作
                isVaildSlide = pageGesture.isaVaildMove(event, mTouch.x, mTouch.y);
                //当手指抖动时候会触发move事件，当认为是无效操作，所以当前有可能处于长按监听状态
                if (isLongClickListener) {
                    return true;
                } else {
                    //当处于可滑动添加书签且手势符合添加书签条件并且未触发添加书签操作时，触发添加书签，清空正在添加的划线
                    if (isScroolMark && pageGesture.judgeMarkTouchType(event, mTouch.x, mTouch.y) && !isAddMarkStart) {
                        isAddMarkStart = true;
                        pageFactory.restoreAddSummary();
                    } else {
                        isScroolMark = false;
                    }
                    //添加书签操作
                    if (isAddMarkStart) {
                        pageViewListener.addBookMark(event.getRawY() - mTouch.y > 0 ? event.getRawY() - mTouch.y : 0.01F);
                        return true;
                    }
                    if (browseMode != BROWSE_MODE_SCROOL_V) {
                        //当滑动为有效滑动时，移除对划线区域和中心控制区域的判断，并移除可能存在的正在划线内容
                        if (isVaildSlide) {
                            isTouchSummary = false;
                            isTouchCentral = false;
                            pageFactory.restoreAddSummary();
                        }
                        if (isLongClickState || isTouchCentral || isTouchSummary) {
                            return true;
                        } else {
                            //通过手势判断翻页状态
                            if (isMoveUpdateStart) {
                                if (event.getX() > downTouchX) {
                                    mCornerX = 0;
                                    isStartAnim = updatePageForNotScroolV(true);
                                } else {
                                    mCornerX = mWidth;
                                    isStartAnim = updatePageForNotScroolV(false);
                                }
                                isMoveUpdateStart = false;
                            }
                        }
                    } else {
                        if (isVaildSlide) {
                            isTouchSummary = false;
                            isTouchCentral = false;
                        }
                        offset = (int) (event.getY()) - lastTonchY;
                        if (isLastPage && !isCache && offsetheight == 0 && offset < 0) {
                            skipEnd = true;
                        }
                        lastTonchY = (int) event.getY();
                        updatePageForScroolV(offset);
                    }
                    mTouch.x = event.getX();
                    mTouch.y = event.getY();
                    if (browseMode == BROWSE_MODE_SCROOL_V || isStartAnim) {
                        invalidate();
                    }
                    return true;
                }
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                //处在添加书签状态时 ，恢复页面状态，移除手势标识
                if (isAddMarkStart) {
                    isAddMarkStart = false;
                    pageViewListener.addBookMark(0);
                    isScroolMark = true;
                    pageFactory.setHandSkipPage(false);
                    return true;
                }
                if (browseMode != BROWSE_MODE_SCROOL_V) {
                    isScroolMark = true;
                    if (isLongClickState) {
                        if (pageFactory.getAddSummary() != null) {
                            pageViewListener.callSummaryPopup(false, minY, maxY);
                        } else {
                            endLongState();
                        }
                    } else if (isTouchSummary) {
                        mTouch.x = 0.01f; // 不让x,y为0,否则在点计算时会有问题
                        mTouch.y = 0.01f;
                        mCornerX = 0; // 拖拽点对应的页脚
                        mCornerY = 0;
                        pageViewListener.callSummaryPopup(true, minY, maxY);
                    } else {
                        if (isTouchCentral) {
                            pageViewListener.callTitleBarWithBottomBar();
                            isEditState = true;
                            canSlide = false;
                            mTouch.x = 0.01f; // 不让x,y为0,否则在点计算时会有问题
                            mTouch.y = 0.01f;
                            mCornerX = 0; // 拖拽点对应的页脚
                            mCornerY = 0;
                            pageFactory.setHandSkipPage(false);
                            invalidate();
                            return true;
                        }
                        if (isSlideing) {
                            if (isTouchArea == PageGesture.TOUCH_LEFT_AREA) {
                                isStartAnim = updatePageForNotScroolV(true);
                            } else if (isTouchArea == PageGesture.TOUCH_RIGHT_AREA) {
                                isStartAnim = updatePageForNotScroolV(false);
                            }
                        }
                        if (!isMoveUpdateStart) {
                            if (event.getX() > downTouchX && mCornerX == mWidth) {
                                isSlideing = true;
                                isCancelTurnPage = true;
                                isStartAnim = updatePageForNotScroolV(true);
                            } else if (event.getX() < downTouchX && mCornerX == 0) {
                                isSlideing = true;
                                isCancelTurnPage = true;
                                isStartAnim = updatePageForNotScroolV(false);
                            }
                        }
                        isSlideing = true;
                        isMoveUpdateStart = true;
                        if (isStartAnim) {
                            startAnimation(400);//动画速率控制
                        } else {
                            mTouch.x = 0.01f; // 不让x,y为0,否则在点计算时会有问题
                            mTouch.y = 0.01f;
                            mCornerX = 0; // 拖拽点对应的页脚
                            mCornerY = 0;
                        }
                        invalidate();
                        isStartAnim = false;
                        pageFactory.setHandSkipPage(false);
                        return true;
                    }
                } else {
                    if (skipEnd) {
                        skipEnd = false;
                        pageViewListener.readEnd();
                    }
                    lastTonchY = (int) (event.getY());
                    if (isLongClickState) {
                        isLongClickState = false;
                        pageFactory.setHandSkipPage(false);
                        return true;
                    }
                    if (isTouchCentral && !isVaildSlide) {
                        pageViewListener.callTitleBarWithBottomBar();
                        isEditState = true;
                        canSlide = false;
                        pageFactory.setHandSkipPage(false);
                        return true;
                    }
                    isVaildSlide = false;
                    final VelocityTracker velocityTracker = mVelocityTracker;
                    velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                    int initialVelocity = (int) velocityTracker.getYVelocity();
                    if (Math.abs(initialVelocity) > mMinimumVelocity) {
                        fling(initialVelocity);
                    } else {
                        pageFactory.setHandSkipPage(false);
                    }
                    if (mVelocityTracker != null) {
                        mVelocityTracker.recycle();
                        mVelocityTracker = null;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 针对上下翻页动画设置
     *
     * @param velocityY velocityY
     */
    private void fling(int velocityY) {
        velocityY = velocityY * 3 / 4;
        if (velocityY > 4 * mHeight) {
            velocityY = 4 * mHeight;
        } else if (velocityY < -4 * mHeight) {
            velocityY = -4 * mHeight;
        }
        int curY = lastTonchY;
        int minY = Math.min(curY + velocityY, 0);
        int maxY = Math.max(curY + velocityY, mHeight);
        mScroller.fling(0, curY, 0, velocityY, 0, 0, minY, maxY);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    /**
     * 计算拖拽点对应的拖拽脚
     */
    public void calcCornerXY(MotionEvent event) {
        if (pageGesture.judgeTouchArea(event) == PageGesture.TOUCH_LEFT_AREA) {
            mCornerX = 0;
        } else {
            mCornerX = mWidth;
        }
        if (event.getY() <= mHeight / 2) {
            mCornerY = 0;
        } else {
            mCornerY = mHeight;
        }
        mIsRTandLB = (mCornerX == 0 && mCornerY == mHeight) || (mCornerX == mWidth && mCornerY == 0);
    }

    public void calcCornerXY(float x, float y) {
        if (pageGesture.judgeTouchArea(x, y) == PageGesture.TOUCH_LEFT_AREA) {
            mCornerX = 0;
        } else {
            mCornerX = mWidth;
        }
        if (y <= mHeight / 2) {
            mCornerY = 0;
        } else {
            mCornerY = mHeight;
        }
    }

    public void abortAnimation() {
        if (!mScroller.isFinished()) {
            mScroller.abortAnimation();
        }
    }

    private void startAnimation(int delayMillis) {
        int dx = 0, dy;
        if (mCornerX > 0) {
            if (browseMode == BROWSE_MODE_SCROOL_H) {
                dx = -(int) (mTouch.x + mDefaultShadow);
                if (isCancelTurnPage) {
                    dx = (int) (mWidth - mTouch.x);
                }
            } else if (browseMode == BROWSE_MODE_EMULATION) {
                dx = -(int) (mWidth + mTouch.x);
                if (isCancelTurnPage) {
                    dx = (int) (mWidth + mWidth - mTouch.x);
                }
            }
        } else {
            if (browseMode == BROWSE_MODE_SCROOL_H) {
                dx = (int) (mWidth - mTouch.x);
                if (isCancelTurnPage) {
                    dx = -(int) (mTouch.x + mDefaultShadow);
                }
            } else if (browseMode == BROWSE_MODE_EMULATION) {
                dx = (int) (mWidth + mWidth - mTouch.x);
                if (isCancelTurnPage) {
                    dx = -(int) (mWidth + mTouch.x);
                }
            }
        }
        if (mCornerY > 0) {
            dy = (int) (mHeight - mTouch.y);
            if (isCancelTurnPage) {
                dy = (int) (-mTouch.y);
            }
        } else {
            dy = (int) (-mTouch.y);
            if (isCancelTurnPage) {
                dy = (int) (mHeight - mTouch.y);
            }
        }
        mScroller.startScroll((int) mTouch.x, (int) mTouch.y, dx, dy,
                delayMillis);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller != null && mScroller.computeScrollOffset()) {
            float x = mScroller.getCurrX();
            float y = mScroller.getCurrY();
            if (browseMode == BROWSE_MODE_SCROOL_V) {
                int deltaY = (int) y - lastTonchY;
                updatePageForScroolV(deltaY);
                lastTonchY = (int) y;
                ViewCompat.postInvalidateOnAnimation(this);
            } else {
                if (y < 0.1f) {
                    y = 0.1f;
                } else if (y > mHeight - 0.1f) {
                    y = mHeight - 0.1f;
                }
                mTouch.x = x;
                mTouch.y = y;
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }
    }

    /**
     * 求解直线P1P2和直线P3P4的交点坐标
     */
    public PointF getCross(PointF P1, PointF P2, PointF P3, PointF P4) {
        PointF CrossP = new PointF();
        // 二元函数通式： y=ax+b
        float a1 = (P2.y - P1.y) / (P2.x - P1.x);
        float b1 = ((P1.x * P2.y) - (P2.x * P1.y)) / (P1.x - P2.x);

        float a2 = (P4.y - P3.y) / (P4.x - P3.x);
        float b2 = ((P3.x * P4.y) - (P4.x * P3.y)) / (P3.x - P4.x);
        CrossP.x = (b2 - b1) / (a1 - a2);
        CrossP.y = a1 * CrossP.x + b1;
        return CrossP;
    }

    private void calcPoints() {
        mMiddleX = (mTouch.x + mCornerX) / 2;
        mMiddleY = (mTouch.y + mCornerY) / 2;
        mBezierControl1.x = mMiddleX - (mCornerY - mMiddleY)
                * (mCornerY - mMiddleY) / (mCornerX - mMiddleX);
        mBezierControl1.y = mCornerY;
        mBezierControl2.x = mCornerX;
        mBezierControl2.y = mMiddleY - (mCornerX - mMiddleX)
                * (mCornerX - mMiddleX) / (mCornerY - mMiddleY);

        mBezierStart1.x = mBezierControl1.x - (mCornerX - mBezierControl1.x)
                / 2;
        mBezierStart1.y = mCornerY;

        // 当mBezierStart1.x < 0或者mBezierStart1.x > 480时
        // 如果继续翻页，会出现BUG故在此限制
        if (mTouch.x > 0 && mTouch.x < mWidth) {
            if (mBezierStart1.x < 0 || mBezierStart1.x > mWidth) {
                if (mBezierStart1.x < 0)
                    mBezierStart1.x = mWidth - mBezierStart1.x;

                float f1 = Math.abs(mCornerX - mTouch.x);
                float f2 = mWidth * f1 / mBezierStart1.x;
                mTouch.x = Math.abs(mCornerX - f2);

                float f3 = Math.abs(mCornerX - mTouch.x)
                        * Math.abs(mCornerY - mTouch.y) / f1;
                mTouch.y = Math.abs(mCornerY - f3);

                mMiddleX = (mTouch.x + mCornerX) / 2;
                mMiddleY = (mTouch.y + mCornerY) / 2;

                mBezierControl1.x = mMiddleX - (mCornerY - mMiddleY)
                        * (mCornerY - mMiddleY) / (mCornerX - mMiddleX);
                mBezierControl1.y = mCornerY;

                mBezierControl2.x = mCornerX;
                mBezierControl2.y = mMiddleY - (mCornerX - mMiddleX)
                        * (mCornerX - mMiddleX) / (mCornerY - mMiddleY);
                mBezierStart1.x = mBezierControl1.x
                        - (mCornerX - mBezierControl1.x) / 2;
            }
        }
        mBezierStart2.x = mCornerX;
        mBezierStart2.y = mBezierControl2.y - (mCornerY - mBezierControl2.y)
                / 2;

        mTouchToCornerDis = (float) Math.hypot((mTouch.x - mCornerX),
                (mTouch.y - mCornerY));

        mBezierEnd1 = getCross(mTouch, mBezierControl1, mBezierStart1,
                mBezierStart2);
        mBezierEnd2 = getCross(mTouch, mBezierControl2, mBezierStart1,
                mBezierStart2);

        mBeziervertex1.x = (mBezierStart1.x + 2 * mBezierControl1.x + mBezierEnd1.x) / 4;
        mBeziervertex1.y = (2 * mBezierControl1.y + mBezierStart1.y + mBezierEnd1.y) / 4;
        mBeziervertex2.x = (mBezierStart2.x + 2 * mBezierControl2.x + mBezierEnd2.x) / 4;
        mBeziervertex2.y = (2 * mBezierControl2.y + mBezierStart2.y + mBezierEnd2.y) / 4;
    }

    private void drawCurrentPageArea(Canvas canvas, Bitmap bitmap, Path path) {
        mPath0.reset();
        mPath0.moveTo(mBezierStart1.x, mBezierStart1.y);
        mPath0.quadTo(mBezierControl1.x, mBezierControl1.y, mBezierEnd1.x,
                mBezierEnd1.y);
        mPath0.lineTo(mTouch.x, mTouch.y);
        mPath0.lineTo(mBezierEnd2.x, mBezierEnd2.y);
        mPath0.quadTo(mBezierControl2.x, mBezierControl2.y, mBezierStart2.x,
                mBezierStart2.y);
        mPath0.lineTo(mCornerX, mCornerY);
        mPath0.close();

        canvas.save();
        canvas.clipPath(path, Region.Op.XOR);
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.restore();
    }

    private void drawNextPageAreaAndShadow(Canvas canvas, Bitmap bitmap) {
        mPath1.reset();
        mPath1.moveTo(mBezierStart1.x, mBezierStart1.y);
        mPath1.lineTo(mBeziervertex1.x, mBeziervertex1.y);
        mPath1.lineTo(mBeziervertex2.x, mBeziervertex2.y);
        mPath1.lineTo(mBezierStart2.x, mBezierStart2.y);
        mPath1.lineTo(mCornerX, mCornerY);
        mPath1.close();

        mDegrees = (float) Math.toDegrees(Math.atan2(mBezierControl1.x
                - mCornerX, mBezierControl2.y - mCornerY));
        int leftx;
        int rightx;
        GradientDrawable mBackShadowDrawable;
        if (mIsRTandLB) {
            leftx = (int) (mBezierStart1.x);
            rightx = (int) (mBezierStart1.x + mTouchToCornerDis / 4);
            mBackShadowDrawable = mBackShadowDrawableLR;
        } else {
            leftx = (int) (mBezierStart1.x - mTouchToCornerDis / 4);
            rightx = (int) mBezierStart1.x;
            mBackShadowDrawable = mBackShadowDrawableRL;
        }
        canvas.save();
        canvas.clipPath(mPath0);
        canvas.clipPath(mPath1, Region.Op.INTERSECT);
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.rotate(mDegrees, mBezierStart1.x, mBezierStart1.y);
        mBackShadowDrawable.setBounds(leftx, (int) mBezierStart1.y, rightx,
                (int) (mMaxLength + mBezierStart1.y));
        mBackShadowDrawable.draw(canvas);
        canvas.restore();
    }

    //    /**
    //     * 绘制翻起页的阴影
    //     */
    //    public void drawCurrentPageShadow(Canvas canvas) {
    //        double degree;
    //        if (mIsRTandLB) {
    //            degree = Math.PI
    //                    / 4
    //                    - Math.atan2(mBezierControl1.y - mTouch.y, mTouch.x
    //                    - mBezierControl1.x);
    //        } else {
    //            degree = Math.PI
    //                    / 4
    //                    - Math.atan2(mTouch.y - mBezierControl1.y, mTouch.x
    //                    - mBezierControl1.x);
    //        }
    //        // 翻起页阴影顶点与touch点的距离
    //        double d1 = (float) 25 * 1.414 * Math.cos(degree);
    //        double d2 = (float) 25 * 1.414 * Math.sin(degree);
    //        float x = (float) (mTouch.x + d1);
    //        float y;
    //        if (mIsRTandLB) {
    //            y = (float) (mTouch.y + d2);
    //        } else {
    //            y = (float) (mTouch.y - d2);
    //        }
    //        mPath1.reset();
    //        mPath1.moveTo(x, y);
    //        mPath1.lineTo(mTouch.x, mTouch.y);
    //        mPath1.lineTo(mBezierControl1.x, mBezierControl1.y);
    //        mPath1.lineTo(mBezierStart1.x, mBezierStart1.y);
    //        mPath1.close();
    //        float rotateDegrees;
    //        canvas.save();
    //
    //        canvas.clipPath(mPath0, Region.Op.XOR);
    //        canvas.clipPath(mPath1, Region.Op.INTERSECT);
    //        int leftx;
    //        int rightx;
    //        GradientDrawable mCurrentPageShadow;
    //        if (mIsRTandLB) {
    //            leftx = (int) (mBezierControl1.x);
    //            rightx = (int) mBezierControl1.x + 25;
    //            mCurrentPageShadow = mFrontShadowDrawableVLR;
    //        } else {
    //            leftx = (int) (mBezierControl1.x - 25);
    //            rightx = (int) mBezierControl1.x + 1;
    //            mCurrentPageShadow = mFrontShadowDrawableVRL;
    //        }
    //
    //        rotateDegrees = (float) Math.toDegrees(Math.atan2(mTouch.x
    //                - mBezierControl1.x, mBezierControl1.y - mTouch.y));
    //        canvas.rotate(rotateDegrees, mBezierControl1.x, mBezierControl1.y);
    //        mCurrentPageShadow.setBounds(leftx,
    //                (int) (mBezierControl1.y - mMaxLength), rightx,
    //                (int) (mBezierControl1.y));
    //        mCurrentPageShadow.draw(canvas);
    //        canvas.restore();
    //
    //        mPath1.reset();
    //        mPath1.moveTo(x, y);
    //        mPath1.lineTo(mTouch.x, mTouch.y);
    //        mPath1.lineTo(mBezierControl2.x, mBezierControl2.y);
    //        mPath1.lineTo(mBezierStart2.x, mBezierStart2.y);
    //        mPath1.close();
    //        canvas.save();
    //        canvas.clipPath(mPath0, Region.Op.XOR);
    //        canvas.clipPath(mPath1, Region.Op.INTERSECT);
    //        if (mIsRTandLB) {
    //            leftx = (int) (mBezierControl2.y);
    //            rightx = (int) (mBezierControl2.y + 25);
    //            mCurrentPageShadow = mFrontShadowDrawableHTB;
    //        } else {
    //            leftx = (int) (mBezierControl2.y - 25);
    //            rightx = (int) (mBezierControl2.y + 1);
    //            mCurrentPageShadow = mFrontShadowDrawableHBT;
    //        }
    //        rotateDegrees = (float) Math.toDegrees(Math.atan2(mBezierControl2.y
    //                - mTouch.y, mBezierControl2.x - mTouch.x));
    //        canvas.rotate(rotateDegrees, mBezierControl2.x, mBezierControl2.y);
    //        float temp;
    //        if (mBezierControl2.y < 0)
    //            temp = mBezierControl2.y - mHeight;
    //        else
    //            temp = mBezierControl2.y;
    //
    //        int hmg = (int) Math.hypot(mBezierControl2.x, temp);
    //        if (hmg > mMaxLength)
    //            mCurrentPageShadow
    //                    .setBounds((int) (mBezierControl2.x - 25) - hmg, leftx,
    //                            (int) (mBezierControl2.x + mMaxLength) - hmg,
    //                            rightx);
    //        else
    //            mCurrentPageShadow.setBounds(
    //                    (int) (mBezierControl2.x - mMaxLength), leftx,
    //                    (int) (mBezierControl2.x), rightx);
    //
    //        mCurrentPageShadow.draw(canvas);
    //        canvas.restore();
    //    }

    /**
     * 绘制翻起页背面
     */
    private void drawCurrentBackArea(Canvas canvas, Bitmap bitmap) {
        int i = (int) (mBezierStart1.x + mBezierControl1.x) / 2;
        float f1 = Math.abs(i - mBezierControl1.x);
        int i1 = (int) (mBezierStart2.y + mBezierControl2.y) / 2;
        float f2 = Math.abs(i1 - mBezierControl2.y);
        float f3 = Math.min(f1, f2);
        mPath1.reset();
        mPath1.moveTo(mBeziervertex2.x, mBeziervertex2.y);
        mPath1.lineTo(mBeziervertex1.x, mBeziervertex1.y);
        mPath1.lineTo(mBezierEnd1.x, mBezierEnd1.y);
        mPath1.lineTo(mTouch.x, mTouch.y);
        mPath1.lineTo(mBezierEnd2.x, mBezierEnd2.y);
        mPath1.close();
        GradientDrawable mFolderShadowDrawable;
        int left;
        int right;
        if (mIsRTandLB) {
            left = (int) (mBezierStart1.x - 1);
            right = (int) (mBezierStart1.x + f3 + 1);
            mFolderShadowDrawable = mFolderShadowDrawableLR;
        } else {
            left = (int) (mBezierStart1.x - f3 - 1);
            right = (int) (mBezierStart1.x + 1);
            mFolderShadowDrawable = mFolderShadowDrawableRL;
        }
        canvas.save();
        canvas.clipPath(mPath0);
        canvas.clipPath(mPath1, Region.Op.INTERSECT);

        mPaint.setColorFilter(mColorMatrixFilter);

        float dis = (float) Math.hypot(mCornerX - mBezierControl1.x,
                mBezierControl2.y - mCornerY);
        float f8 = (mCornerX - mBezierControl1.x) / dis;
        float f9 = (mBezierControl2.y - mCornerY) / dis;
        mMatrixArray[0] = 1 - 2 * f9 * f9;
        mMatrixArray[1] = 2 * f8 * f9;
        mMatrixArray[3] = mMatrixArray[1];
        mMatrixArray[4] = 1 - 2 * f8 * f8;
        mMatrix.reset();
        mMatrix.setValues(mMatrixArray);
        mMatrix.preTranslate(-mBezierControl1.x, -mBezierControl1.y);
        mMatrix.postTranslate(mBezierControl1.x, mBezierControl1.y);
        canvas.drawBitmap(bitmap, mMatrix, mPaint);
        mPaint.setColorFilter(null);
        canvas.rotate(mDegrees, mBezierStart1.x, mBezierStart1.y);
        mFolderShadowDrawable.setBounds(left, (int) mBezierStart1.y, right,
                (int) (mBezierStart1.y + mMaxLength));
        mFolderShadowDrawable.draw(canvas);
        canvas.restore();
    }

    /**
     * 覆盖动画--画出当前页
     *
     * @param canvas canvas
     * @param bitmap bitmap
     */
    private void drawCurrentPage(Canvas canvas, Bitmap bitmap) {
        if (canvas == null || bitmap == null || bitmap.isRecycled()) {
            return;
        }
        float dx = mTouch.x;
        canvas.save();
        canvas.clipRect(0, 0, mTouch.x, mHeight);
        canvas.drawBitmap(bitmap, -mWidth + dx, 0, null);
        canvas.restore();
    }

    /**
     * 覆盖动画--画出当前页阴影
     *
     * @param canvas canvas
     */
    private void drawCurPageShadow(Canvas canvas) {
        if (mTouch.x <= 0.01f && mTouch.y <= 0.01f) {
            return;
        }
        GradientDrawable mCurrentPageShadow = mSilideShadowDrawable;

        mCurrentPageShadow.setBounds((int) mTouch.x, 0, (int) (mTouch.x + mDefaultShadow), mHeight);
        canvas.save();
        mCurrentPageShadow.draw(canvas);
        canvas.restore();
    }

    /**
     * 覆盖动画--画出下一页
     *
     * @param canvas canvas
     * @param bitmap bitmap
     */
    private void drawNextPage(Canvas canvas, Bitmap bitmap) {
        if (canvas == null || bitmap == null || bitmap.isRecycled())
            return;

        canvas.save();
        canvas.clipRect(mTouch.x, 0, mWidth, mHeight);
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.restore();
    }

    /**
     * 垂直滑动条件下换页判断
     *
     * @param offset offset
     */
    public void updatePageForScroolV(int offset) {
        offsetheight = offsetheight + offset;
        if (offsetheight > contentHeight / 2) {
            if (!isCache) {
                drawPrePageForV();
            }
        }
        if (offsetheight < (-1) * contentHeight / 2) {
            if (!isCache) {
                drawNextPageForV(false);
            }
        }
    }

    public boolean updatePageForNotScroolV(boolean isleft) {
        if (isSlideing) {
            pageFactory.draw(curCanvas);
            if (isleft) {
                if (pageFactory.prePage()) {
                    pageFactory.draw(nextCanvas);
                    if (browseMode == BROWSE_MODE_SCROOL_H) {
                        setBitmaps(nextBitmap, curBitmap);
                    } else {
                        if (isCancelTurnPage) {
                            setBitmaps(nextBitmap, curBitmap);
                        } else {
                            setBitmaps(curBitmap, nextBitmap);
                        }
                    }
                } else {
                    isSlideing = false;
                    return false;
                }
            } else {
                if (pageFactory.nextPage()) {
                    pageFactory.draw(nextCanvas);
                    if (browseMode == BROWSE_MODE_SCROOL_H) {
                        setBitmaps(curBitmap, nextBitmap);
                    } else {
                        if (isCancelTurnPage) {
                            setBitmaps(nextBitmap, curBitmap);
                        } else {
                            setBitmaps(curBitmap, nextBitmap);
                        }
                    }
                } else {
                    pageViewListener.readEnd();
                    isSlideing = false;
                    return false;
                }
            }
            isSlideing = false;
        }
        return true;
    }


    /**
     * 控件相关设置方法
     *
     * @param size size
     */
    public void setTextSize(int size) {
        if (pageFactory.getFontSize() != size) {
            pageFactory.setFontSize(size);
            pageFactory.skipPage(pageFactory.reSlicePage());
            //UserActionManager.getInstance().recordEventValue(UserActionEvent.KEY_FONTSIZE + size);
            reDraw();
        }
    }

    public int setTextSizeDown() {
        int size = pageFactory.getFontSize() - 2 > 12 ? pageFactory.getFontSize() - 2 : 12;
        setTextSize(size);
        return size;
    }

    public int setTextSizeUp() {
        int size = pageFactory.getFontSize() + 2 < 30 ? pageFactory.getFontSize() + 2 : 30;
        setTextSize(size);
        return size;
    }

    public int getTextSize() {
        return pageFactory.getFontSize();
    }

    //    public void setScreenHeight(boolean showStatus){
    //        pageFactory.setContentHeight(showStatus);
    //        pageFactory.skipPage(pageFactory.reSlicePage());
    //        reDraw();
    //    }

    public void setPageViewListener(PageViewListener pageViewListener) {
        this.pageViewListener = pageViewListener;
    }

    public void changeTheme() {
        bgColor = Color.parseColor(ThemeManage.getTheme());
        pageFactory.setBgColor(bgColor);
        if (titleBgCanvas != null) {
            titleBgCanvas.drawColor(bgColor);
        }
        reDraw();
    }

    public void setSummaryExist(boolean summaryExist) {
        isSummaryExist = summaryExist;
    }

    public void setTTSSetExist(boolean exist) {
        isTTSSetExist = exist;
    }

    public void setFirstPage(boolean first) {
        this.isFirstPage = first;
    }

    public void setPageFactory(PageFactory pageFactory) {
        this.pageFactory = pageFactory;
        contentHeight = pageFactory.getContentHeight();
        bgColor = pageFactory.getBgColor();
    }

    public int getBrowseMode() {
        return browseMode;
    }

    public boolean setBrowseMode(int browseMode) {
        if (this.browseMode != browseMode) {
            this.browseMode = browseMode;
            if (browseMode == BROWSE_MODE_SCROOL_V) {
                curBitmap = Bitmap.createBitmap(mWidth, contentHeight, Bitmap.Config.RGB_565);
                nextBitmap = Bitmap.createBitmap(mWidth, contentHeight, Bitmap.Config.RGB_565);
                cacheBitmap = Bitmap.createBitmap(mWidth, contentHeight, Bitmap.Config.RGB_565);
                titleBgBitmap = Bitmap.createBitmap(mWidth, PixelUtil.dp2px(48), Bitmap.Config.RGB_565);
                cacheCanvas = new Canvas(cacheBitmap);
                curCanvas = new Canvas(curBitmap);
                nextCanvas = new Canvas(nextBitmap);
                titleBgCanvas = new Canvas(titleBgBitmap);
                titleBgCanvas.drawColor(pageFactory.getBgColor());
                isScroolMark = false;
            } else {
                curBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.RGB_565);
                nextBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.RGB_565);
                curCanvas = new Canvas(curBitmap);
                nextCanvas = new Canvas(nextBitmap);
                isScroolMark = true;
            }
            return true;
        }
        return false;
    }

    public void changeBookMark(boolean isAdd) {
        if (isAdd) {
            pageFactory.addBookMark();
            //UserActionManager.getInstance().recordEvent(UserActionEvent.ACTION_ADD_MARK);
        } else {
            pageFactory.detCurpageMark();
            //UserActionManager.getInstance().recordEvent(UserActionEvent.ACTION_DEL_MARK);
        }
        reDraw();
    }

    /**
     * 重新绘制当前页
     */
    public void reDraw() {
        mTouch.x = 0.01f; // 不让x,y为0,否则在点计算时会有问题
        mTouch.y = 0.01f;
        mCornerX = 0; // 拖拽点对应的页脚
        mCornerY = 0;
        pageFactory.skipPage();
        if (browseMode == BROWSE_MODE_SCROOL_V) {
            boolean wait = true;
            while (wait) {
                if (pageFactory.isPreLoadEnd()) {
                    if (pageFactory.prePage()) {
                        offsetheight = (-1) * contentHeight;
                        wait = false;
                        drawNextPageForV(false);
                    } else {
                        offsetheight = (-1) * contentHeight;
                        wait = false;
                        drawNextPageForV(true);
                    }
                }
            }
        } else {
            pageFactory.draw(curCanvas);
            pageFactory.draw(nextCanvas);
            setBitmaps(curBitmap, nextBitmap, cacheBitmap);
            postInvalidate();
        }
    }

    public void reDraw(int page) {
        mTouch.x = 0.01f; // 不让x,y为0,否则在点计算时会有问题
        mTouch.y = 0.01f;
        mCornerX = 0; // 拖拽点对应的页脚
        mCornerY = 0;
        pageFactory.skipPage(page);
        if (browseMode == BROWSE_MODE_SCROOL_V) {
            boolean wait = true;
            while (wait) {
                if (pageFactory.isPreLoadEnd()) {
                    if (pageFactory.prePage()) {
                        offsetheight = (-1) * contentHeight;
                        wait = false;
                        drawNextPageForV(false);
                    } else {
                        offsetheight = (-1) * contentHeight;
                        wait = false;
                        drawNextPageForV(true);
                    }
                }
            }
        } else {
            pageFactory.draw(curCanvas);
            pageFactory.draw(nextCanvas);
            setBitmaps(curBitmap, nextBitmap, cacheBitmap);
            postInvalidate();
        }
    }

    /**
     * 增加划线方法
     *
     * @param startPos startPos
     * @param endPos   endPos
     */
    public void addBookSummary(int startPos, int endPos) {
        pageFactory.addBookSummary(startPos, endPos);
        reDraw();
    }

    private class LongPressRunnable implements Runnable {
        private float x, y;

        private void setPressLocation(float x, float y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void run() {
            if (!isSummaryExist) {
                isLongClickState = true;
                if (browseMode != BROWSE_MODE_SCROOL_V) {
                    if (pageFactory.mathPosByCoordinate(x, y) != null) {
                        Map<String, Object> map = pageFactory.mathPosByCoordinate(x, y);
                        startPos = (int) (map.get("position"));
                        endPos = (int) (map.get("position")) + 1;
                        minY = (float) (map.get("coordinateStartY"));
                        maxY = (float) (map.get("coordinateEndY"));
                        addBookSummary(startPos, endPos);
                        pageViewListener.startSelection(true, (float) map.get("coordinateStartX"), (float) (map.get("coordinateStartY")));
                        pageViewListener.endSelection(true, (float) map.get("coordinateEndX"), (float) (map.get("coordinateEndY")));
                    }
                } else {
                    pageViewListener.selectionError();
                }
            }
        }
    }

    /**
     * 通过移动游标改变划线区域的索引值
     *
     * @param x    x
     * @param y    y
     * @param isup isup
     */
    public void changeSummaryPos(float x, float y, boolean isup) {
        if (isup) {
            if (pageFactory.mathPosByCoordinate(x, y) != null) {
                Map<String, Object> map = pageFactory.mathPosByCoordinate(x, y);
                if (endPos > (int) (map.get("position"))) {
                    startPos = (int) (map.get("position"));
                    minY = (float) (map.get("coordinateStartY"));
                    addBookSummary(startPos, endPos);
                    pageViewListener.startSelection(true, (float) map.get("coordinateStartX"), (float) (map.get("coordinateStartY")));
                }
            }
        } else {
            if (pageFactory.mathPosByCoordinate(x, y) != null) {
                Map<String, Object> map = pageFactory.mathPosByCoordinate(x, y);
                if ((int) (map.get("position")) + 1 > startPos) {
                    endPos = (int) (map.get("position")) + 1;
                    maxY = (float) (map.get("coordinateEndY"));
                    addBookSummary(startPos, endPos);
                    pageViewListener.endSelection(true, (float) map.get("coordinateEndX"), (float) (map.get("coordinateEndY")));
                }
            }
        }
    }

    /**
     * 更新popupwindow位置
     */
    public void updateSummaryPopup(boolean isExist) {
        pageViewListener.callSummaryPopup(isExist, minY, maxY);
    }

    /**
     * 获取划线内容
     */
    public BookSummary getSummaryObject() {
        isLongClickState = false;
        return pageFactory.getAddSummary();
    }

    /**
     * 添加划线
     */
    public void addSummary2List() {
        pageFactory.addBookSummary2List();
    }

    /**
     * 刷新划线和书签
     */
    public void reflushMarkWithSummary() {
        pageFactory.reflushMarkWithSummary();
        reDraw();
    }

    /**
     * 结束长按状态，复原系统设置
     */
    public void endLongState() {
        isLongClickState = false;
        isTouchSummary = false;
        pageViewListener.startSelection(false, 0, 0);
        pageViewListener.endSelection(false, 0, 0);
        pageFactory.restoreAddSummary();
        reDraw();
    }

    /**
     * 结束编辑状态，用于页面跳转
     */
    public void endEditState() {
        if (isEditState) {
            isEditState = false;
            canSlide = true;
            removeCallbacks(longPressRunnable);
        }
        if (isTouchSummary) {
            isTouchSummary = false;
        }
    }

    public void drawNextPageForV(final boolean isfirst) {
        isCache = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!isfirst) {
                    pageFactory.drawJustContent(cacheCanvas);
                    pageFactory.nextPage();
                    isFirstPage = false;
                } else {
                    isFirstPage = true;
                }
                pageFactory.drawJustContent(curCanvas, true);
                if (pageFactory.nextPage()) {
                    pageFactory.drawJustContent(nextCanvas);
                    isLastPage = false;
                    pageFactory.prePage();
                } else {
                    isLastPage = true;
                }
                setBitmaps(curBitmap, nextBitmap, cacheBitmap);
                offsetheight = offsetheight + contentHeight;
                postInvalidate();
                isCache = false;
            }
        }).start();
    }

    public void drawPrePageForV() {
        isCache = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                pageFactory.drawJustContent(nextCanvas);
                if (pageFactory.prePage()) {
                    pageFactory.drawJustContent(curCanvas, true);
                    if (pageFactory.prePage()) {
                        pageFactory.drawJustContent(cacheCanvas);
                        pageFactory.nextPage();
                    } else {
                        pageFactory.drawJustContent(cacheCanvas);
                    }
                }
                setBitmaps(curBitmap, nextBitmap, cacheBitmap);
                offsetheight = offsetheight - contentHeight;
                postInvalidate();
                isCache = false;
            }
        }).start();
    }


    /**
     * 通过监听音量键进行翻页
     */
    public void pageChangeByVolumeKey(int keyCode) {
        pageFactory.setHandSkipPage(true);
        switch (browseMode) {
            case BROWSE_MODE_EMULATION:
            case BROWSE_MODE_SCROOL_H:
                if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
                    mTouch.x = mWidth - 3;
                    mTouch.y = mHeight - 3;
                    calcCornerXY(mTouch.x, mTouch.y);
                    isStartAnim = updatePageForNotScroolV(false);
                    isSlideing = true;
                    if (isStartAnim) {
                        startAnimation(400);
                    } else {
                        mTouch.x = 0.01f; // 不让x,y为0,否则在点计算时会有问题
                        mTouch.y = 0.01f;
                        mCornerX = 0; // 拖拽点对应的页脚
                        mCornerY = 0;
                    }
                    this.postInvalidate();
                } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
                    mTouch.x = 3;
                    mTouch.y = mHeight - 3;
                    calcCornerXY(mTouch.x, mTouch.y);
                    isStartAnim = updatePageForNotScroolV(true);
                    isSlideing = true;
                    if (isStartAnim) {
                        startAnimation(400);
                    } else {
                        mTouch.x = 0.01f; // 不让x,y为0,否则在点计算时会有问题
                        mTouch.y = 0.01f;
                        mCornerX = 0; // 拖拽点对应的页脚
                        mCornerY = 0;
                    }
                    this.postInvalidate();
                }
                break;
            case BROWSE_MODE_SCROOL_V:
                if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
                    lastTonchY = 0;
                    mScroller.fling(0, 0, 0, -10000, 0, 0, (-1) * contentHeight, contentHeight);
                    ViewCompat.postInvalidateOnAnimation(this);
                } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
                    lastTonchY = 0;
                    mScroller.fling(0, 0, 0, 10000, 0, 0, (-1) * contentHeight, contentHeight);
                    ViewCompat.postInvalidateOnAnimation(this);
                }
                break;
            default:
                break;
        }
    }

}
