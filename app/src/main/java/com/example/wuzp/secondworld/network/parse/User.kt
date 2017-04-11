package com.example.wuzp.secondworld.network.parse

import com.vread.hs.network.parse.AuthPartner
import java.io.Serializable
import java.util.*

/**
 * Created by wuzp on 2017/3/19.
 * 使用kotlin 来写javabean 如果使用()就是表示使用的是构造函数，使用{}就表示默认为空的构造函数，并且定义了很多的属性
 */
class User :Serializable {
    var access_token: String = ""
    var uid: String = ""
    var nickname: String = ""
    var avatar: String = ""
    var cover: String = ""
    var gender: String = ""
    var phone: String = ""
    var story_count: String = ""
    var follow_count: String = ""
    var fav_count: String = ""
    var like_count: String = ""
    var description: String = ""
    var auth: Map<String, AuthPartner> = HashMap()
    override fun toString(): String {
        return "User(access_token='$access_token', uid='$uid', nickname='$nickname', avatar='$avatar', cover='$cover', gender='$gender', phone='$phone', story_count='$story_count', follow_count='$follow_count', fav_count='$fav_count', like_count='$like_count', description='$description', auth=$auth)"
    }
}