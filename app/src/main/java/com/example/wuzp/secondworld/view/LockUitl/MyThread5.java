package com.example.wuzp.secondworld.view.LockUitl;

/**
 * Created by wuzp on 2017/5/16.
 */
public class MyThread5 extends Thread {//姓神不信神，还是踏踏实实的为人好
    private String name;
    private int num;
    private LockBean lockBean;
    private static byte[] lockTarget = new byte[0];
    //这是这个类对象锁持有的东西，在对象中 锁住了，会是怎么样的
    //锁住的标的物是类对象的，能够保证线程执行是不会被打断。但是不保证多线程的执行顺序
    //如果将lockBean 锁住，能够保证线程执行不会打断，也不能保证多线程执行的顺序

    public MyThread5(String name,int num,LockBean lockBean){
        this.name = name;
        this.num = num;
        this.lockBean = lockBean;
    }

    public static void main(String[] args){
        LockBean lockBean = LockBean.getInstance();

        String[] names = {"a","b","c","d"};

        for(int i=0;i<names.length;i++){
           new MyThread5(names[i],i,lockBean).start();
        }
        //这里的输出不一定是 a b c d这样的顺序，但是只要a输出了，那么一定会让a执行完才会调用其他的。
    }

    @Override
    public void run() {
        super.run();
        synchronized (lockBean){//lockTarget 两个锁住的标的物都只能保证线程执行的完整性 但是不能保证多线程执行的顺序
            System.out.println("name ------------------------------ " + name);
            lockBean.methodOne(num);
            System.out.println("name ------------------------------ " + name);
        }
    }
}
