package com.example.wuzp.secondworld.view.java;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by wuzp on 2017/5/25.
 */
//看看 字符串中的转义的处理 普通的字符串 在做转义时  是不是会变化
public class TestStrEncode {

    static String tempStr = "abcdefg+HIJKLMN";

    //一个Java的主函数 在运行之后就会创建一个jvm 然后创建一个进程
    //一个主线程，当然也是可以创建子线程
    //来处理其他的东西的。直到应用的自动退出。主线程自己停止掉，进程停止掉 然后Jvm停止下来
    public static void main(String[] args){
       show("before");
       show(tempStr);
       String str = "";
        try{
            str = URLEncoder.encode(tempStr,"UTF-8");//encode处理
        }catch (Exception e ){}
        show("after");
        show(str);

        String afterStr = "";
        try{
            afterStr =  URLDecoder.decode(str,"UTF-8");//decode 处理
        }catch (Exception e){}
        show("*******************************************");
        show(afterStr);

        long threadId = Thread.currentThread().getId();
        show("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        show("threadID:" + threadId);

        new Thread(new Runnable() {
            @Override
            public void run() {
                long threadId = Thread.currentThread().getId();
                show("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                show("inner  threadID:" + threadId);
                try {
                    Thread.sleep(10000);
                }catch (Exception e){}
            }
        }).start();
    }

    private static void show(String msg){
        System.out.println("-----------------");
        System.out.println("msg:" + msg);
        System.out.println("-----------------");
    }
}
