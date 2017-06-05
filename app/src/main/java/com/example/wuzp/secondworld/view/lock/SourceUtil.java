package com.example.wuzp.secondworld.view.lock;


import android.util.Log;

/**
 * Created by wuzp on 2017/6/2.
 * 测试锁的粒度
 */

public class SourceUtil {
    public static byte[] lock_one = new byte[0];
    public static byte[] lock_two = new byte[0];
    public static byte[] lock_three = new byte[0];

    private static SourceUtil sourceUtil;
    private SourceUtil(){}

    public static SourceUtil getInstance(){
        if(sourceUtil == null){
            sourceUtil = new SourceUtil();
        }
        return sourceUtil;
    }

    //测试  4秒的占用时间 每一秒输出一个log
    public void testOne(){
        synchronized (lock_one){
            try{
                boolean flag = true;
                int tempSum = 1000;
                while (flag){
                    Thread.sleep(1000);
                    tempSum +=1000;
                    Log.e("wzp","testOne:" + tempSum);
                    if(tempSum >  4000)
                        flag = false;
                }
            }catch (Exception e){}
        }
    }

    //测试  4秒的占用时间 每一秒输出一个log
    public void testTwo(){
        synchronized (lock_two){
            try{
                boolean flag = true;
                int tempSum = 1000;
                while (flag){
                    Thread.sleep(1000);
                    tempSum +=1000;
                    Log.e("wzp","testTwo:" + tempSum);
                    if(tempSum >  3000)
                        flag = false;
                }
            }catch (Exception e){}
        }
    }

    //测试  4秒的占用时间 每一秒输出一个log
    public void testThree(){
        synchronized (lock_three){
            try{
                boolean flag = true;
                int tempSum = 1000;
                while (flag){
                    Thread.sleep(1000);
                    tempSum +=1000;
                    Log.e("wzp","testThree:" + tempSum);
                    if(tempSum >  2000)
                        flag = false;
                }
            }catch (Exception e){}
        }
    }

    //测试  4秒的占用时间 每一秒输出一个log
    public void testAll(){
            try{
                boolean flag = true;
                int tempSum = 1000;
                while (flag){
                    Thread.sleep(1000);
                    tempSum +=1000;
                    Log.e("wzp","testAll:" + tempSum);
                    if(tempSum >  4000)
                        flag = false;
                }
            }catch (Exception e){}
    }
}
