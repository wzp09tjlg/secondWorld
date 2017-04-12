package com.example.wuzp.secondworld.view.other;

import com.apkfuns.logutils.LogUtils;
import com.example.hslibrary.ToolUtil;
import com.example.mylibrary.ToolUtils;

/**
 * Created by wuzp on 2017/3/19.
 */

public class OtherClass {

    public void someMethod(){

        //虽然说抽象类不能实例化，但是创建这个对象 重写抽象的方法就可以了
        new AbstractClass(){
            @Override
            public void show() {

            }
        };
    }

    private void doTest(){
        LogUtils.e("before agust I must get the via of beijing deep diving");
        ToolUtil.minus(100,200);
        ToolUtil.add(200,300);
        ToolUtils.add(100,200);
        ToolUtils.minus(200,300);
    }
}
