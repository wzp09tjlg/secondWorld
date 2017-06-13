package com.example.wuzp.secondworld.view.factory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzp on 2017/6/13.
 * 可以当做一个类的辅助类 专门处理一些比较复杂的计算的工作
 */
public class ChangeFactory {

    private List<String> mData;

    public ChangeFactory(){
        mData = new ArrayList<>();
    }

    public void init(){
        //进行一些简单的处理工作
    }

    public void calcComplextAction(){
        //提供一个处理复杂的逻辑的方法
    }

}
