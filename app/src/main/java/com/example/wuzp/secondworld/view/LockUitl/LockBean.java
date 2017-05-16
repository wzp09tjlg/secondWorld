package com.example.wuzp.secondworld.view.LockUitl;

/**
 * Created by wuzp on 2017/5/16.
 * 针对于锁的再理解
 */
public class LockBean {
    private String name;
    private byte[] mLockTarget = new byte[0];//锁的标的物

    private static LockBean lockBean;

    private LockBean(String name){
        this.name = name;
    }

   public static LockBean getInstance(){
       if(lockBean == null){
           lockBean = new LockBean("wuzp");
       }
       return lockBean;
   }

   public void methodOne(final int num){
       /*synchronized (mLockTarget){//锁住的是这个对象的持有的标的物
       }*/
       System.out.println("name:" + name + "  num:" + num);
   }

}
