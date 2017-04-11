package com.example.wuzp.secondworld.network.parse

/**
 * Created by wuzp on 2017/4/5.
 */
class SelectBean(var imgs:Imgs,var storys:Storys){
   class Imgs{
       var type:String = ""
       var title:String = ""
       var data:List<BannerBean>? = null

       class BannerBean(var link:String,var image:String)
   }

    class Storys{
        var type:String = ""
        var title:String = ""
        var count : String = ""
        var data : List<Story> ? = null

        class  Story{
             var content_url :String = ""
             var tags:List<Tag> ? = null
             var story_id :String = ""
             var title :String  = ""
             var image:String  = ""
             var cover:String  = ""
             var summary :String = ""
            var create_time :String = ""
            var show_icon: Boolean = false
            var type: String = ""
            var status: String = ""
            var review: String = ""
            var editable: Boolean = false
            var contract: String = ""
            var version: Int = 0
            var recent_read_count: String = ""
            var create_date: String = ""
            var user: User ?= null
            var  read_count: String = ""
            var fav_count: Int= 0
            var like_count: Int = 0
            var comment_count: Int = 0
            class Tag{
                var name:String = ""
                var image:String = ""
                var id:String = ""
            }

            class User{
                var uid: String = ""
                var nickname:String = ""
                var small_avatar:String = ""
                var large_avatar: String = ""
                var weibo_verified_type: String = ""
            }
        }
    }
}