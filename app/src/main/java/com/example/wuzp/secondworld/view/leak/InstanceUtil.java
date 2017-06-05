package com.example.wuzp.secondworld.view.leak;

/**
 * Created by wuzp on 2017/6/5.
 */

public class InstanceUtil {
    private static InstanceUtil instance;

    private LeakImp leakImp;

    private InstanceUtil(){}

    public static InstanceUtil getInstance(){
        if(instance == null){
            instance = new InstanceUtil();
        }
        return instance;
    }

    public void testOne(){
        leakImp.testMethodOne();
    }

    public void testTwo(){
        leakImp.testMethodTwo();
    }

    public void setLeakImp(LeakImp leakImp) {
        this.leakImp = leakImp;
    }

    public void onDestory(){
      if(leakImp != null){
          leakImp = null;
      }
        instance.setLeakImp(null);
        instance = null;//看看能不能将自己这个静态的类置为空
    }
}
