package com.example.wuzp.secondworld.view.other;

import com.apkfuns.logutils.LogUtils;

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
    }
}
