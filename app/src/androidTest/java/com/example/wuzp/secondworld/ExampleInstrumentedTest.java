package com.example.wuzp.secondworld;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.wuzp.secondworld.utils.CommonHelper;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    //这个测试方法 是会抛出异常的
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.wuzp.bookselfdemo", appContext.getPackageName());
    }

    //以下的两个方法都是能够执行正常
    @Test
    public void useAppPackage() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.wuzp.secondworld", appContext.getPackageName());
    }

    @Test
    public void useAppMethod() throws Exception {
        //同这里的类可以很方便的调用 工具类中实现的方法  看看方法是不是能够真正的处理各种极端的情况
        //to use app method
        assertEquals(false, CommonHelper.isFastDoubleClick());//自己代码的逻辑处理 方法测试
    }
}
