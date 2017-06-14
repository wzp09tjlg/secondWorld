package com.example.wuzp.secondworld.view.wangdou.utils;

/**
 * Created by zyb on 2017/2/28.
 */
public class ThemeManage {
    public static boolean isDayOrNight() {
        return false;//!SRPreferences.getInstance().getString(SRPreferences.READ_THEME, ColorManager.FFe6dbc8).equals(ColorManager.FF3e3e3e);
    }

    public static String getTheme() {
        return ColorManager.FFe6dbc8;//isDayOrNight() ? SRPreferences.getInstance().getString(SRPreferences.DAY_THEME, ColorManager.FFe6dbc8) : ColorManager.FF3e3e3e;
    }

    public static void changeThemeGroup(){
        if (isDayOrNight()){
            ;//SRPreferences.getInstance().setString(SRPreferences.READ_THEME,ColorManager.FF3e3e3e);
        }else {
            ;//SRPreferences.getInstance().setString(SRPreferences.READ_THEME,ColorManager.FFe6dbc8);
        }
    }
    public static void setTheme(String theme){
        //SRPreferences.getInstance().setString(SRPreferences.READ_THEME,theme);
        if (isDayOrNight()){
            //SRPreferences.getInstance().setString(SRPreferences.DAY_THEME,theme);
        }
    }
}
