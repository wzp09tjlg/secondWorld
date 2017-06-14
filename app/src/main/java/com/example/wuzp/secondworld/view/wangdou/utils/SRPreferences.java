package com.example.wuzp.secondworld.view.wangdou.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.wuzp.secondworld.HApplication;


/**
 * Created by zyb on 2016/9/12.
 * function:
 * SharedPreferences类
 */
public class SRPreferences {
    private Context context = HApplication.gContext;
    private static SRPreferences ourInstance;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    //是否是老用户
    public static final String PREFERENCES_OLDUSER = "PREFERENCES_OLDUSER";
    //是否进行过性别选择//是否是第一次进入
    public static final String PREFERENCES_CHECKSEX = "PREFERENCES_CHECKSEX";
    //0 man,1 woman
    public static final String PREFERENCES_UESR_SEX = "PREFERENCES_UESR_SEX";
    //0 all,1 网文，2出版
    public static final String PREFERENCES_USER_FAVOR = "PREFERENCES_USER_FAVOR";
    //是否进行过使用引导
    public static final String ISGUIDE = "PREFERENCES_GUIDE";
    //3.1.0版本使用  是否处理过书籍库数据,本地书籍与账户关联
    public static final String OLD_DATA_UPDATE_3_1_0 = "old_data_update_3_1_0";
    public static final String UNAME = "PREFERENCES_UNAME";
    public static final String UICON = "PREFERENCES_UICON";
    public static final String SEARCH_HISTORY = "SEARCH_HISTORY";
    public static final String ACCOUNT_UB = "ACCOUNT_UB";//阅读币
    public static final String ACCOUNT_DJ = "ACCOUNT_DJ";//代金券

    //书城是否引导
    public static final String ISGUIDESTORE = "IS_GUIDE_STORE";//书城在3.1.0版本更改后，此字段废弃，书城不需要引导

    //阅读器设置
    public static final String READ_LIGHT_MODE = "readlightmode";
    public static final String READ_LIGHT_VOLUE = "readlightvolue";
    public static final String READ_THEME = "readtheme";
    public static final String DAY_THEME = "daytheme";
    public static final String READ_BROWSEMODE = "browseMode";
    public static final String READ_FONTSIZE = "fontsize";
    public static final String READ_LIGHT_TIME = "readlighttime";
    public static final String READ_CHAPTERCACHE_COUNT = "read_chaptercache_count";
    public static final String READ_LIGHT_TIME_SYSTER = "readlighttimesyster";
    public static final String READ_CONTORL_VOL = "read_contorl_vol";
    public static final String READ_FULL_ALL = "read_full_all";

    //书架fragment
    public static final String SHELF_NOTICE = "shelf_notice";
    public static final String SHELF_OPERATE = "shelf_Operate";
    //统计
    public static final String KEY_INSTALL_DATE = "install_date";
    public static final String KEY_INSTALL_FIRST = "install_first";

    //最近阅读书籍id
    public static final String KEY_LAST_READ_BOOKID = "last_read_bookid";

    //首次进入个人中心
    public static final String KEY_FIRST_USERINFO = "last_first_userinfo";

    //书架章节更新时间
    public static final String KEY_UPDATE_CHAPTER_TIME = "update_chapter_time";

    //资源文件中h5资源版本
    public static final String KEY_RES_LEVEL = "key_res_level";
    //用户设置页面推送开关
    public static final String KEY_PUSH_SWITCH = "key_push_switch";
    //运营相关标志位
    public static final String RECOMMEND_URL = "recommend_url";
    public static final String RECOMMEND_TEXT = "recommend_text";
    public static final String RECOMMEND_ID_LASTSEE = "recommend_id_lastsee";
    public static final String RECOMMEND_ID_LASTGET = "recommend_id_lastget";
    public static final String RECOMMEND_IMG = "recommend_img";

    //个推活动网址
    public static final String DSINTENT_URL = "DSIntent_url";

    //个推活动网址
    public static final String SHOW_NEWUSER_DIALOG = "showNewUserDialog";

    public static SRPreferences getInstance() {
        if (ourInstance == null) {
            ourInstance = new SRPreferences(HApplication.gContext);
        }
        return ourInstance;
    }

    private SRPreferences(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("sr_prefreence", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    /**
     * 获取指定Key对应的String值
     *
     * @param key 关键字
     * @return key对应的value，默认为空字符串""
     */

    public void setString(final String key, final String value) {
        editor.putString(key, value).commit();
    }

    public String getString(final String key, final String value) {
        return preferences.getString(key, value);
    }

    public void setLong(final String key, final Long value) {
        editor.putLong(key, value).commit();
    }

    public Long getLong(final String key, final Long value) {
        return preferences.getLong(key, value);
    }

    public void setInt(final String key, final int value) {
        editor.putInt(key, value).commit();
    }

    public int getInt(final String key, final int defValue) {
        return preferences.getInt(key, defValue);
    }

    public void setBoolean(final String key, final boolean value) {
        editor.putBoolean(key, value).commit();
    }

    public Boolean getBoolean(final String key, final boolean value) {
        return preferences.getBoolean(key, value);
    }

}
