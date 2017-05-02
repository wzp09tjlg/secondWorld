package com.example.wuzp.secondworld.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.network.ApiStores;
import com.example.wuzp.secondworld.network.Net;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.example.wuzp.secondworld.utils.AppUtil.getVersionName;
import static com.example.wuzp.secondworld.utils.AppUtil.pxToDp;

/**
 * Created by wu on 2017/1/19.
 */
@SuppressWarnings("All")
public class CommonHelper {
    public static final String WEIBO_V_TYPE_BLUE = "blue";
    public static final String WEIBO_V_TYPE_YELLOW = "yellow";

    //在WebView 的http请求头user-agent上添加应用的信息
    public static void appendUA(Context context, WebView webView) {
        String user_agent = webView.getSettings().getUserAgentString();
        webView.getSettings().setUserAgentString(user_agent + " huasheng/" + getVersionName(context));
    }

    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
                (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
                && (codePoint <= 0x10FFFF));
    }

    public static String getChanel(Context context) {
        String chanel = "";
        if (context != null) {
            try {

                ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                chanel = String.valueOf(appInfo.metaData.get("UMENG_CHANNEL"));

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return chanel;
    }

    public static void changeWeiboVStatus(String type, ImageView weiboVType) {
        int vId = -1;
        if (type == null || TextUtils.isEmpty(type)) {
            weiboVType.setVisibility(View.GONE);
            return;
        }
        if (!TextUtils.isEmpty(type)) {
            if (WEIBO_V_TYPE_BLUE.equals(type)) {
                vId = R.drawable.weibo_v_blue_new2;
            } else if (WEIBO_V_TYPE_YELLOW.equals(type)) {
                vId = R.drawable.weibo_v_yellow_new2;
            }
        }
        if (vId != -1) {
            weiboVType.setImageResource(vId);
            weiboVType.setVisibility(View.VISIBLE);
        } else {
            weiboVType.setVisibility(View.GONE);

        }
    }

    public static void updateStoryType(final Context ctx, String title, String type, boolean show_icon, View titleView) {
        final TextView tv = (TextView) titleView;
        if (show_icon && type.equals("2")) {//接龙2
            String html = "<img src='" + R.drawable.story_item_type_jielong + "'/> " + title;
            Html.ImageGetter imgGetter = new Html.ImageGetter() {

                @Override
                public Drawable getDrawable(String source) {
                    int id = Integer.parseInt(source);
                    Drawable d = ctx.getResources().getDrawable(id);
                    float rate = pxToDp(ctx, tv.getTextSize()) > 15 ? 1.2f : 1f;
                    d.setBounds(0, 0, (int) (d.getIntrinsicWidth() * rate), (int) (d.getIntrinsicHeight() * rate));
                    return d;
                }
            };
            tv.setText(Html.fromHtml(html, imgGetter, null));
        } else {
            tv.setText(title);
        }
    }

    /**
     * 检查wifi下是否加载图片
     */
    public static boolean isLoadImageEnable(Context context) {
        return false;
       /* if (context != null && HSPreferences.getInstance()
                .getBoolean(HSPreferences.PREFERENCE_KEY_DOWNLOAD_IMG_WIFI_ONLY, false)
                && !CommonHelper.isNetWorkWifi(context)) {
            return false;
        } else {
            return true;
        }*/
    }

    /**
     * 检查是否在wifi网络
     */
    public static boolean isNetWorkWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return null != networkInfo && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public static boolean isContainerHtml(String url) {
        if (!TextUtils.isEmpty(url))
            return url.contains("file:///android_asset/container.html");
        else
            return false;
    }

    public static String getUrlWithComParam(Context context, String url) {
        HashMap<String, String> map = Net.newHashMap();
        String urlParamStr = CommonHelper.paramstoString(map, true);
        String split = "?";
        if (url.indexOf("?") != -1) {
            split = "&";
        }
        return url + split + urlParamStr;
    }


    // map转化为字符串
    public static String paramstoString(Map<String, String> params, boolean isEncodeValue) {
        if (params != null && params.size() > 0) {
            String paramsEncoding = "UTF-8";
            StringBuilder encodedParams = new StringBuilder();
            try {
                int index = 0;
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    encodedParams.append(isEncodeValue ? URLEncoder.encode(entry.getKey(), paramsEncoding) : entry.getKey());
                    encodedParams.append('=');
                    encodedParams.append(isEncodeValue ? URLEncoder.encode(TextUtils.isEmpty(entry.getValue()) ? "" : entry.getValue(), paramsEncoding) : entry.getValue());

                    index++;
                    if (index < params.size()) {
                        encodedParams.append('&');
                    }
                }
                return encodedParams.toString();
            } catch (UnsupportedEncodingException uee) {
                throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
            }
        }
        return null;
    }

    public static boolean isLinkErro(String url) {
        if (!TextUtils.isEmpty(url))
            return url.contains("file:///android_asset/");
        else
            return false;
    }

    public static String fixLink(String url) {
        if (!TextUtils.isEmpty(url)) {
            return url.replaceFirst("file:///android_asset/", "");
        } else {
            return url;
        }
    }

    // 处理重复按键操作方法  处理200秒以内的点击是无效的
    public static long lastClickTime;
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 300) {
            return true;
        }
        lastClickTime = time;
        return false;
    }


    // 检查网络是否可用
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return null != networkInfo && networkInfo.isAvailable();
    }

    // 强制打开键盘
    public static void showKeyBoard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (!imm.isActive(view)) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    // 强制隐藏键盘
    public static void hideKeyBoard(Context context, View view) {
        if (context == null || view == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY); //隐藏键盘
    }

    // 强制隐藏键盘
    public static void forceHideKeyBoard(Context context, View view) {
        if (context == null || view == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }

    /**
     * 设置可浮动的软键盘， 避免在软件盘抬起时挤压布局，造成的背景颜色变化
     *
     * @param activity activity
     */
    public static void setFloatableKeyBoard(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN |
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    /**
     * json 转换成map
     */
    public static Map<String, String> JSON2Map(String json) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            JSONObject jsonMap = new JSONObject(json);
            Iterator<String> it = jsonMap.keys();
            while (it.hasNext()) {
                String key = it.next();
                String value = jsonMap.get(key).toString();
                map.put(key, value);
            }
        } catch (Exception e) {
        }
        return map;
    }

    public static boolean isHsHtml(String url) {

        if (!TextUtils.isEmpty(url)) {
            Uri uri = Uri.parse(url);
            String host = uri.getHost();
            if (!TextUtils.isEmpty(host) && host.contains(ApiStores.HS_HOST)) {
                String path = uri.getPath();
                if (!TextUtils.isEmpty(path)) {
                    if (path.endsWith(".html") || path.endsWith("/") || !path.contains(".")) {
                        return true;
                    }
                    int index = path.lastIndexOf('/');
                    if (index >= 0 && index < path.length() - 1) {
                        String end = path.substring(index, path.length());
                        if (!end.contains(".")) {
                            return true;
                        }
                    }
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    static int MAX_GENERATE_COUNT = 9999;
    static int sGenerateCount = 0;
    /**
     * 生成唯一字符串
     *
     * @return
     */
    public static synchronized String getUniqueString() {
        if (sGenerateCount > MAX_GENERATE_COUNT) {
            sGenerateCount = 0;
        }

        String uniqueString = Long.toString(System.currentTimeMillis()) + Integer.toString(sGenerateCount);
        sGenerateCount++;
        return uniqueString;
    }
}
