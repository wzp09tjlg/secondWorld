package com.example.wuzp.secondworld.view.LockUitl.consumer;

import java.util.concurrent.locks.Lock;

/**
 * Created by wuzp on 2017/5/8.
 * 儿子类 主要适用于消费
 */
public class SonThread implements Runnable {
    private CardBean cardBean;
    private Lock lock;

    public SonThread(CardBean cardBean,Lock lock){
        this.cardBean = cardBean;
        this.lock = lock;
    }

    @Override
    public void run() {
        long tempTime = 0;

       while(true){
          lock.lock();
           try{
               System.out.println("Son    当前card上的余额是：" + cardBean.getBalance());
               System.out.println("Son    son 准备消费，消费金额是：2000");
               cardBean.minusBalance( 2000);
               System.out.println("Son     son消费之后 card上余额是：" + cardBean.getBalance());
           }finally {
               lock.unlock();
           }
           try {
               Thread.sleep(1000);
               tempTime +=1000;
           }catch (Exception e){
           }

           if(tempTime > 120000){
               System.out.println("Son    son 消费时间超时了");
               break;
           }

           if(cardBean.getBalance() < -20000){
               System.out.println("Son    son 消费的card 已经透支了，不能再消费了");
               break;
           }
       }
    }
}
