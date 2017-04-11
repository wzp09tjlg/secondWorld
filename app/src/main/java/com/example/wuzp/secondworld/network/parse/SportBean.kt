package com.example.wuzp.secondworld.network.parse

/**
 * Created by wuzp on 2017/3/30.
 */
//kotlin的基本类实现
//data修饰的class 是一个必须带括号的类
//而非data修饰的class 就不必须在
//在一个Kotlin 的数据bean中可以写很多的数据bean
class SportBean{
    var name:String = ""
    var sex:Int = 0
    var address : String = ""
    constructor(){
        name = ""
        sex = 0;
        address = ""
    }
    constructor( name:String,age:Int,address:String)
}

data class BallBean(var name:String,var size:Int,var desc:String){
    fun add(x: Int, y: Int): Int {
        return (x+y)
    }
    //在kotlin中 声明方法 是fun 作为方法的定义声明，其次是方法的名字，最后是参数
    //参数中 前边的参数是变量名，后边是参数类型Any 表示各种类型，之后再括号之后
    // 返回的是方法的返回类型

    fun doSomeAction(): String {
        var tempName :String = ""
        var tempSex:Int = 0
        var tempAddress :String = ""

        return "name:" + tempName+" sex:" + tempSex + " address:" + tempAddress;
    }
    //在定义方法时 会选择传入的参数的个数

    fun showBean(bean:SportBean):String{
        var tempStr:String = ""
        tempStr = "name:" + bean.name + " address:" + bean.address + " sex:" + bean.sex;
        return tempStr;
    }
    //传入的参数 可以是基本类型也可以是复杂的数据结构
}
