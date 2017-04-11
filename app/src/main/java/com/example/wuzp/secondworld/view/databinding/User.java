package com.example.wuzp.secondworld.view.databinding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by wuzp on 2017/4/10.
 * Error:Execution failed for task ':app:compileDebugJavaWithJavac'.
 * > java.lang.RuntimeException: Found data binding errors.
 * *** data binding error ****msg:Identifiers must have user defined types from the XML file. View is missing it
 * file:D:\workspace_test\android\secondWorld\app\src\main\res\layout\activity_databinding.xml
 * loc:21:49 - 21:52
 * loc:21:64 - 21:67
 * ***\ data binding error ****
 * <p>
 * databinding的双向绑定  是需要将数据进行集成BaseObservable 然后还需要实现各个属性的get set方法
 */
public class User extends BaseObservable {
    private String firstName;
    private String lastName;
    private boolean isvisit;

    public User(String firstName,String lastName,boolean isvisit){
        this.firstName = firstName;
        this.lastName = lastName;
        this.isvisit = isvisit;
    }

    @Bindable //只有在get方法上添加 这个注解 才能在set方法中调用通知属性改变 连同View上的显示页一块变化
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        //notifyChange(); //这个方法是可以实现数据的更新的
        //notifyPropertyChanged(BR.user);//这个方法是可以动态改变数据导致view上的数据跟随变化
        //notify();//报错，说没有锁住object
        //notifyAll();//报错 说是对象没有被锁住
        //不知道为什么这里只有 整体的更新的方法  没有单个更新的方法 是不是有些浪费资源？
        //在网上看到的 是可以针对某些字段做更新的操作的
        //notifyPropertyChanged(BR.firstName);//貌似得在set方法上添加注解 才能使用单个属性的改变 效果可行
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isvisit() {
        return isvisit;
    }

    public void setIsvisit(boolean isvisit) {
        this.isvisit = isvisit;
    }
}
