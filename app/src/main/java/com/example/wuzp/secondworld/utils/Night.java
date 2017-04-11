package com.example.wuzp.secondworld.utils;

import android.app.Activity;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.example.wuzp.secondworld.R;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tiny on 2/20/2017.
 * <p>
 * 客户端夜间模式转换框架
 * <p>
 * 使用id进行注入时，首先保证在创建布局之前首先调用init方法，否则会抛异常
 * <p>
 * 使用string进行注入时没有这个限制
 */
@SuppressWarnings({"unused", "deprecation", "JavaDoc"})
public class Night {
    private static final String TAG = "Night";

    // 当前是否时黑色模式须在程序加载的时候设置
    private boolean mIsNight = false;
    private static Night ourInstance = new Night();
    // 存放fragment或者activity的set，用于通知存活页面进行更改
    private static Set<NightModeChangeListener> mListeners = new HashSet<>();
    // 存放r文件中的color drawable的相关信息
    private static final SparseArray<String> resourceMap = new SparseArray<>();

    public static Night getInstance() {
        return ourInstance;
    }

    private Night() {
    }

    public interface NightModeChangeListener {
        void onNightChange();
    }

    private void drawable(View view, String valueName) {
        checkNull(valueName);
        view.setTag(R.id.night_drawable, valueName);
        setViewDrawable(view);
    }

    private void drawable(View view, int valueId) {
        drawable(view, resourceMap.get(valueId));
    }

    private void background(View view, String valueName) {
        checkNull(valueName);
        view.setTag(R.id.night_background, valueName);
        setViewBackGround(view);
    }

    private void background(View view, int valueId) {
        background(view, resourceMap.get(valueId));
    }

    private void textColor(TextView view, String valueName) {
        checkNull(valueName);
        view.setTag(R.id.night_textcolor, valueName);
        setViewTextColor(view);
    }

    private void textColor(TextView view, int valueId) {
        textColor(view, resourceMap.get(valueId));
    }

    private void hintTextColor(TextView view, String valueName) {
        checkNull(valueName);
        view.setTag(R.id.night_hinttextcolor, valueName);
        setViewHintTextColor(view);
    }

    private void hintTextColor(TextView view, int valueId) {
        hintTextColor(view, resourceMap.get(valueId));
    }

    private void drawableLeft(TextView view, String valueName) {
        checkNull(valueName);
        view.setTag(R.id.night_drawableleft, valueName);
        setViewDrawableLeft(view);
    }

    private void drawableLeft(TextView view, int valueId) {
        drawableLeft(view, resourceMap.get(valueId));
    }

    private void checkNull(String valueName) {
        if (TextUtils.isEmpty(valueName)) {
            throw new RuntimeException("Night$ResourceMustNoNull");
        }
    }

    /**
     * 递归调用更改布局颜色属性
     *
     * @param rootView 布局根view
     */
    public void change(ViewGroup rootView) {
        changeView(rootView);
        for (int i = 0; i < rootView.getChildCount(); i++) {
            View view = rootView.getChildAt(i);
            changeView(view);
            if (view instanceof ViewGroup && !(view instanceof RecyclerView)) {
                change((ViewGroup) view);
            }
        }
    }

    /**
     * 更改布局资源
     *
     * @param view view
     */
    public void changeView(View view) {
        setViewBackGround(view);
        setViewDrawable(view);
        // 若不是textview的子类就不进行textview的相关设置
        if (!(view instanceof TextView))
            return;
        setViewTextColor(view);
        setViewHintTextColor(view);
        setViewDrawableLeft(view);
    }

    private void setViewDrawableLeft(View view) {
        if (!TextUtils.isEmpty((CharSequence) view.getTag(R.id.night_drawableleft))) {
            int resId = getResouceFromValueName(view.getContext(), "drawable",
                    (String) view.getTag(R.id.night_drawableleft));
            Drawable drawableLeft = view.getContext().getResources().getDrawable(resId);
            drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
            ((TextView) view).setCompoundDrawables(drawableLeft, null, null, null);
        }
    }

    private void setViewHintTextColor(View view) {
        if (!TextUtils.isEmpty((CharSequence) view.getTag(R.id.night_hinttextcolor))) {
            int colorId = getResouceFromValueName(view.getContext(), "color",
                    (String) view.getTag(R.id.night_hinttextcolor));
            ((TextView) view).setHintTextColor(view.getResources().getColor(colorId));
        }
    }

    private void setViewTextColor(View view) {
        if (!TextUtils.isEmpty((CharSequence) view.getTag(R.id.night_textcolor))) {
            int colorId = getResouceFromValueName(view.getContext(), "color",
                    (String) view.getTag(R.id.night_textcolor));
            ((TextView) view).setTextColor(view.getResources().getColor(colorId));
        }
    }

    private void setViewDrawable(View view) {
        if (!TextUtils.isEmpty((CharSequence) view.getTag(R.id.night_drawable))) {
            int drawable = getResouceFromValueName(view.getContext(), "drawable",
                    (String) view.getTag(R.id.night_drawable));
            view.setBackgroundResource(drawable);
        }
    }

    private void setViewBackGround(View view) {
        if (!TextUtils.isEmpty((CharSequence) view.getTag(R.id.night_background))) {
            view.setBackgroundResource(getResouceFromValueName(view.getContext(), "color",
                    (String) view.getTag(R.id.night_background)));
        }
    }

    /**
     * 返回当前状态
     *
     * @return 夜间模式返回 true
     */
    public boolean isNight() {
        return mIsNight;
    }

    /**
     * 在程序初始化的时候调用，设定当前的夜间模式状态
     *
     * @param isNight 当前记录的模式
     * @param rclass  r.xx.class
     */
    public void initNight(boolean isNight, Class... rclass) {
        mIsNight = isNight;
        if (rclass.length == 0) {
            LogUtils.i("Night -> initNight -> Make sure , No id-inject in the project");
        }
        long start = System.currentTimeMillis();
        for (Class rclas : rclass) {
            injectR(rclas);
        }
    }

    /**
     * 从r文件注入字段
     *
     * @param clazz r.xx.class
     */
    private void injectR(Class clazz) {
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            try {
                String valueString = String.valueOf(field.get(clazz));
                Integer value = Integer.parseInt(field.get(clazz).toString());
                resourceMap.put(field.getInt(clazz), field.getName());
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
    }

    /**
     * 设置夜间模式，在主动改变模式的时候调用
     *
     * @param isNight
     */
    public void setNight(boolean isNight) {
        if (mIsNight == isNight)
            return;

        mIsNight = isNight;
        for (NightModeChangeListener mListener : mListeners) {
            mListener.onNightChange();
        }
    }

    /**
     * 增加一个监听，监听存活的界面
     *
     * @param l
     */
    public void addListener(NightModeChangeListener l) {
        mListeners.add(l);
    }

    /**
     * 清楚错有页面，须在程序完全退出的时候调用
     */
    public void clearListener() {
        mListeners.clear();
    }

    /**
     * 指定移除监听
     *
     * @param l
     */
    public void removeListener(NightModeChangeListener l) {
        mListeners.remove(l);
    }

    /**
     * 将一个drawble放到view 背景
     *
     * @param view      view
     * @param valueName 资源string
     */
    @Deprecated
    @BindingAdapter("drawable")
    public static void setDrawable(View view, String valueName) {
        Night.getInstance().drawable(view, valueName);
    }

    /**
     * 设置textview 字体颜色
     *
     * @param view
     * @param valueName
     */
    @Deprecated
    @BindingAdapter("textcolor")
    public static void setTextColor(@NonNull TextView view, @NonNull String valueName) {
        Night.getInstance().textColor(view, valueName);
    }

    /**
     * 将一个drawble放到view 背景
     * <p>
     * 须在整个应用view加载之前执行init方法
     *
     * @param view    view
     * @param valueId 资源id
     */
    @BindingAdapter("drawable")
    public static void setDrawable(View view, int valueId) {
        Night.getInstance().drawable(view, valueId);
    }

    /**
     * 设置背景颜色
     *
     * @param view      view
     * @param valueName 资源string
     */
    @Deprecated
    @BindingAdapter("background")
    public static void setBackGround(@NonNull View view, @NonNull String valueName) {
        Night.getInstance().background(view, valueName);
    }

    /**
     * 设置textview 字体颜色
     * <p>
     * 须在整个应用view加载之前执行init方法
     *
     * @param view    view
     * @param valueId 资源id
     */
    @BindingAdapter("textcolor")
    public static void setTextColor(@NonNull TextView view, int valueId) {
        Night.getInstance().textColor(view, valueId);
    }

    /**
     * 设置textview hint 文字颜色
     *
     * @param view      view
     * @param valueName 资源string
     */
    @Deprecated
    @BindingAdapter("hinttextcolor")
    public static void setHintTextColor(@NonNull TextView view, @NonNull String valueName) {
        Night.getInstance().hintTextColor(view, valueName);
    }

    /**
     * 设置view背景
     * <p>
     * 须在整个应用view加载之前执行init方法
     *
     * @param view    view
     * @param valueId 资源id
     */
    @BindingAdapter("background")
    public static void setBackGround(@NonNull View view, int valueId) {
        Night.getInstance().background(view, valueId);
    }

    /**
     * 设置textview左边图标
     *
     * @param view      view
     * @param valueName 资源string
     */
    @Deprecated
    @BindingAdapter("drawableleft")
    public static void setDrawableLeft(@NonNull TextView view, @NonNull String valueName) {
        Night.getInstance().drawableLeft(view, valueName);
    }

    /**
     * 设置textview hint 文字颜色
     * <p>
     * 须在整个应用view加载之前执行init方法
     *
     * @param view    view
     * @param valueId 资源id
     */
    @BindingAdapter("hinttextcolor")
    public static void setHintTextColor(@NonNull TextView view, int valueId) {
        Night.getInstance().hintTextColor(view, valueId);
    }

    /**
     * 从资源的名字拿到id
     *
     * @param context   上下文
     * @param type      资源类型 color|drawable
     * @param valueName 资源的名字
     * @return 资源id
     */
    private int getResouceFromValueName(Context context, String type, String valueName) {
        String value;
        if (isNight()) {
            value = valueName + "_night";
        } else {
            value = valueName;
        }
        int id = context.getResources().getIdentifier(value, type, context.getPackageName());
        if (id == 0) {
            Log.e(TAG, "Night$ResourceNotFoundException -> " + valueName);
        }
        return id;
    }

    /**
     * 设置textview左边图标
     * <p>
     * 须在整个应用view加载之前执行init方法
     *
     * @param view    view
     * @param valueId 资源id
     */
    @BindingAdapter("drawableleft")
    public static void setDrawableLeft(@NonNull TextView view, int valueId) {
        Night.getInstance().drawableLeft(view, valueId);
    }

    /**
     * 设置状态栏沉浸
     * <p>
     * 在base activity中调用即可
     *
     * @param activity activity
     */
    public static void setWindowStatusBarColor(Activity activity) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                int colorId = Night.getInstance().getResouceFromValueName(activity, "color",
                        "colorPrimary");

                window.setStatusBarColor(activity.getResources().getColor(colorId));
                window.setNavigationBarColor(activity.getResources().getColor(colorId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}