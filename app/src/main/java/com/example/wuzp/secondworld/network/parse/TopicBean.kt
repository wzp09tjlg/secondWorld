package com.example.wuzp.secondworld.network.parse

/**
 * Created by wuzp on 2017/4/24.
 * 在观察数据结构时  需要看清楚是 数组还是对象
 */
class TopicBean{
    var name:String =""
    var items :Items  ?= null
}

class Items :ArrayList<ItemsBean>()

class ItemsBean{
    var type:String = ""
    var title:String = ""
    var data:Data ?= null
}

class Data :ArrayList<DataBean>()

class DataBean {
    var tag:String = ""
    var topic:String = ""
    var image:String = ""
}