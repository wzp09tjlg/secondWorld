package com.example.wuzp.secondworld.view.wangdou.interf;

/**
 * Created by zyb on 2016/10/19.
 * pageview与activity交互接口
 */
public interface PageViewListener {
    void callTitleBarWithBottomBar();//显示控制界面

    void dismissTitleBarWithBottomBar();//隐藏控制界面

    void addBookMark(float offset);//书签操作

    void startSelection(boolean doing, float x, float y);//划线上

    void endSelection(boolean doing, float x, float y);//划线下

    void callSummaryPopup(boolean exist, float minY, float maxY);//划线window显示

    void dismissSummaryPopup();//隐藏划线window

    void selectionError();//滑动长按失败

    void readEnd();//跳转阅读尾页

    void ttsStatusChange(boolean statu);
}
