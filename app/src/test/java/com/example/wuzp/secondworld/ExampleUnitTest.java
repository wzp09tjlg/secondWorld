package com.example.wuzp.secondworld;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {//这里抛出异常 就是要求调用者必须获取异常
        assertEquals(4, 2 + 2);
    }

    @Test
    public void minus_isCorrect() throws  Exception{
       assertEquals(8,16-8);
    }
}