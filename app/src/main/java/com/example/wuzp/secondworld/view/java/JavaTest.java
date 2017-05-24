package com.example.wuzp.secondworld.view.java;

/**
 * Created by wuzp on 2017/5/24.
 * 这段时间在学习Java8 当然也是在回顾深入理解Java
 * 这个类就是简单的回顾Java中的常见的包及类
 */
public class JavaTest {
    //String
    private void testForString(){
        //每一个String 都会在字符常量池中 创建对象，才会放置到堆中 引用String对象的变量是放在栈中的
        //涉及到的String 会关联到这样一个比较 == 还是equals() == 是比较两个变量的地址 equals()是比较两个变量的值
        //所以某个类希望比较对象是不是相等，那么就要实现equals()的方法了
        //熟悉和理解String的基本方法 创建，转换大小 截取部分等方法
    }
    //StringBuilder 功能与StringBuffer类似，但是没有线程安全的处理
    //StringBuffer  String功能相当的类，是线程安全的。需要记住的是 常用的方法 构建 插入 添加 toString等
    //Date  日期类，常见的获取日期的方法，年月日 时间等
    //Math  常有的数据方法，记住这个类

}
