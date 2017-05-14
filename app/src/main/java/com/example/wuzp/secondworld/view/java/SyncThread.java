package com.example.wuzp.secondworld.view.java;

/**
 * Created by wuzp on 2017/5/14.
 * to test syncnised key word
 */
public class SyncThread extends Thread {
    private String name = "";
    private int    num  = -1;

    public SyncThread(String name,int num){
          this.name = name;
        this.num = num;
    }

    public static void main(String[] args){
       String name = "wuzp";
        for(int i=0;i<10;i++){
            new SyncThread(name+i,i).start();
        }
    }

    @Override
    public void run() {
        super.run();
        synchronized (this){ //这里锁住的是对象锁 不是类对象锁
            for(int i=0;i<10;i++){
                System.out.println("name:" + name + "  num:" + num + "   i:" + i);
                try{
                    Thread.sleep(100);
                }catch (Exception e){}
            }
        }
    }
}
