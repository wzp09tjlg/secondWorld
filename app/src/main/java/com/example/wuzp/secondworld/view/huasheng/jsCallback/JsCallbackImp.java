package com.example.wuzp.secondworld.view.huasheng.jsCallback;

import android.webkit.JavascriptInterface;

/**
 * Created by wuzp on 2017/3/29.
 * 父类中 定义一个方法 子类中在去重写这个方法 ，参数是可以在子类中获取到的
 */
public abstract class JsCallbackImp {//implements IJsCallBack {


    @JavascriptInterface
    //@Override
    public void jsClick(String json){}
}
