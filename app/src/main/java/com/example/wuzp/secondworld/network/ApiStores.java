package com.example.wuzp.secondworld.network;

import com.example.wuzp.secondworld.network.parse.HttpBase;
import com.example.wuzp.secondworld.network.parse.HttpBase_j;
import com.example.wuzp.secondworld.network.parse.SelectBean;
import com.example.wuzp.secondworld.network.parse.TopicBean;
import com.google.gson.JsonElement;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

@SuppressWarnings("All")
public interface ApiStores {

    // 是否强制使用测试服务器,当这是为true是无论debug，还是release都将使用测试服务器
    public static final boolean FORCEUSE_TEST_HOST = false;
    // 安卓花生公参
    public static final String HS_APP_KEY = "android_phone_client";
    public static final String HS_APP_SECRET = "f7c3a45e816c09e64ed609954b5441ee";

    // Host地址
    public static final String HS_HOST = "http://client.hs.vread.com/";
    public static final String HS_HOST_KEY = "client-hs.vread.com";
    // 测试服务器地址
    public static String HS_HOST_TEST = "http://221.179.193.164/huasheng/client/";
    public static String HS_HOST_KEY_TEST = "221.179.193.164";

    // 统计-用户行为统计
    public static final String HS_USER_STATS = "stats/clickInfo.json";
    // 举报页
    public static final String HS_STORY_REPORT = HS_HOST_TEST + "report/story.html";
    // 故事接龙
    public static final String HS_RELAY_SOLITAIRE = HS_HOST_TEST + "relay/solitaire.html";
    // 品花生(品专题)
    public static final String HS_PINGHUASHENG = HS_HOST_TEST + "peanut/taste.html";

    //获取运营为
    //http://client.hs.vread.com/operation/show.json
    //http://client.hs.vread.com/operation/show.json?position=start_topic
    /**
     *  获取运营位 QueryMap 是针对get方法  FieldMap是针对Post方法
     *  及时ApiStores是空值
     * */
    @GET("operation/show.json")
    Flowable<HttpBase_j<TopicBean>> getTopicList(@QueryMap Map<String,String> params);

    /**
     * 引导页接口-获取首页tab数据
     *
     * @param position {search_tip 搜索推荐位,recommend_follow 推荐关注,
     *                 top_tab 顶部tab栏,start_topic 写作主题推荐}
     */
/*    @GET("operation/show.json")
    Flowable<HttpBase<TabList>> getTabList(@Query("position") String position);

    *//**
     * 引导页接口-获取版本信息
     *
     * @param app_version 版本信息
     * @param app_channel 渠道信息
     *//*
    @GET("config/check_app_update.json")
    Flowable<HttpBase<UpdateBean>> getUpdateInfo(@Query("app_version") String version,
                                                 @Query("app_channel") String channel);

    *//**
     * 登入接口
     *
     * @param params
     *//*
    @FormUrlEncoded
    @POST("user/login.json")
    Flowable<HttpBase<User>> login(@FieldMap Map<String, String> params);

    *//**
     * 获得我-我的收藏中的数据
     *
     * @param token    token
     * @param page     当前请求的页数
     * @param pageSize 每页请求的大小
     *//*
    @GET("user/fav_list.json")
    Flowable<HttpBase<StoryList>> getUserCollections(@QueryMap Map<String, String> params);

    @GET("user/like_list.json")
    Flowable<HttpBase<StoryList>> getUserFollows(@Query("access_token") String token,
                                                 @Query("page") String page,
                                                 @Query("page_size") String pageSize);

    *//**
     * 得到我的故事中我的故事数据
     *//*
    @GET("user/story_list.json")
    Flowable<HttpBase<StoryList>> getUserStory(@QueryMap Map<String, String> params);

    *//**
     * 获取搜索页推荐话题
     *
     * @param position search_tip 固定参数
     * @return
     *//*
    @GET("operation/show.json")
    Flowable<HttpBase<HotSearch>> getSearchTip(@Query("position") String position);

    *//**
     * 搜索资源
     *//*
    @GET("search/all.json")
    Flowable<HttpBase<SearchResult>> getSearchResource(@QueryMap() Map<String, String> params);

    *//**
     * 获取搜索页面上，用户标签之后获取更多
     *//*
    @GET("search/more.json")
    Flowable<HttpBase<SearchUserMoreList>> getSearchUserMore(@QueryMap() Map<String, String> params);

    *//**
     * 获取搜索页面上，标签之后的标签获取更多
     *//*
    @GET("search/more.json")
    Flowable<HttpBase<SearchTagMoreList>> getSearchTagMore(@QueryMap() Map<String, String> params);


    *//**
     * 获取用户信息
     *
     * @param accessToken
     * @return
     *//*
    @GET("user/info.json")
    Flowable<HttpBase<UserBaseInfo>> getUserInfo(@Query("access_token") String accessToken);

    *//**
     * 获取uesrframgeent 页面上个人中心信息
     *//*
    @GET("user/me.json")
    Flowable<HttpBase<PersonalCenter>> getPersonalCenter(@Query("access_token") String accessToken);

    *//**
     * 更新用户个人信息
     *//*
    @FormUrlEncoded
    @POST("user/update.json")
    Flowable<HttpBase> updateProfileInfo(@FieldMap() Map<String, String> params);

    *//**
     * 获取验证码
     *//*
    @FormUrlEncoded
    @POST("user/send_phone_code.json")
    Flowable<HttpBase> getVerificationCode(@FieldMap() Map<String, String> params);

    *//**
     * 检查手机号与验证码是否正确
     *//*
    @FormUrlEncoded
    @POST("user/verify_phone_code.json")
    Flowable<HttpBase> checkVerificationPhone(@FieldMap() Map<String, String> params);

    *//**
     * 重置手机号
     *//*
    @FormUrlEncoded
    @POST("user/rebind_phone.json")
    Flowable<HttpBase> resetPhoneNumber(@FieldMap() Map<String, String> params);

    *//**
     * 绑定三方账户 - hs服务器
     *//*
    @FormUrlEncoded
    @POST("user/bind_partner_account.json")
    Flowable<HttpBase> bindPartnerAccount(@FieldMap() Map<String, String> params);

    *//**
     * 解绑三方账户 - hs服务器
     *//*
    @FormUrlEncoded
    @POST("user/unbind_partner_account.json")
    Flowable<HttpBase> unBindPartnerAccount(@FieldMap() Map<String, String> params);

    *//**
     * 绑定或者重新绑定手机号 - 使用三方授权登入跳转后
     *//*
    @FormUrlEncoded
    @POST("user/join.json")
    Flowable<HttpBase<User>> bindPhoneNumber(@FieldMap Map<String, String> params);

    *//**
     * 注册用户
     *//*
    @FormUrlEncoded
    @POST("user/join.json")
    Flowable<HttpBase<User>> registerUser(@FieldMap Map<String, String> params);

    *//**
     * 请求意见反馈
     *//*
    @GET("operation/getQuestionList.json")
    Flowable<HttpBase<FeedBack>> getQuestionList(@QueryMap() Map<String, String> params);

    *//**
     * 提交问题反馈
     *//*
    @FormUrlEncoded
    @POST("operation/feed_back.json")
    Flowable<HttpBase> submitQuestions(@FieldMap() Map<String, String> params);

    *//**
     * 获取我的反馈历史
     *//*
    @GET("operation/back_list.json")
    Flowable<HttpBase<UserFeedBack.UserFeedBackItem>> getFeedbackHistorys(@QueryMap Map<String, String> params);

    *//**
     * 用户行为统计
     *//*
    @FormUrlEncoded
    @POST("stats/clickInfo.json")
    Flowable<EmptyBean> postUserStats(@QueryMap() Map<String, String> params);

    *//**
     * 关注
     *//*
    @GET("follow/lists.json")
    Flowable<HttpBase<FavoriteList>> getMineFavorite(@Query("access_token") String token,
                                                     @Query("page") String page,
                                                     @Query("page_size") String pageSize);

    @GET("operation/recommend_follow.json")
    Flowable<HttpBase<FavoriteRecommend>> getRecommendFavorite(@Query("page") String page,
                                                               @Query("page_size") String pageSize);

    @FormUrlEncoded
    @POST("follow/tag.json")
    Flowable<HttpBase<EmptyBean>> postFollowTag(@FieldMap Map<String, String> params);*/

    //@FormUrlEncoded
    //@POST("follow/user.json")
    //Flowable<HttpBase<EmptyBean>> postFollowUser(@FieldMap Map<String, String> params);

    /**
     * 重置密码
     */
    @FormUrlEncoded
    @POST("user/reset_password.json")
    Flowable<HttpBase> resetPasswordNumber(@FieldMap() Map<String, String> params);

    /**
     * 找回密码
     */
    @FormUrlEncoded
    @POST("user/update_password.json")
    Flowable<HttpBase<JsonElement>> retrievePassword(@FieldMap() Map<String, String> params);


    /**
     * 检测手机号是否已经注册
     */
    //@FormUrlEncoded
    //@POST("user/check_phone.json")
    //Flowable<HttpBase<PhoneState>> checkPhoneRegisterStatus(@FieldMap() Map<String, String> params);

    /**
     * 上传图片
     */
    //@FormUrlEncoded
    //@Streaming
    //@POST("upload/base64.json")
    //Flowable<HttpBase<UploadImage>> uploadImage(@Field("image") String image);

    /**
     * 获取内容页的内容
     */
    //@GET("story/show.json")
    //Flowable<HttpBase<StoryDetail>> getContentUrl(@QueryMap() Map<String, String> params);

    /**
     * 写评论
     */
    //@FormUrlEncoded
    //@POST("comment/add.json")
    //Flowable<HttpBase<Comment>> postAddComment(@FieldMap() Map<String, String> params);

    /**
     * 删评论
     */
    //@FormUrlEncoded
    //@POST("comment/del.json")
    //Flowable<HttpBase<EmptyBean>> postDelComment(@FieldMap() Map<String, String> params);

    /**
     * 点赞
     */
    @FormUrlEncoded
    @POST("like/add.json")
    Flowable<HttpBase<JsonElement>> postAddPraise(@FieldMap() Map<String, String> params);

    /**
     * 取消赞
     */
    @FormUrlEncoded
    @POST("like/remove.json")
    Flowable<HttpBase<JsonElement>> postRemovePraise(@FieldMap() Map<String, String> params);

    /**
     * 收藏
     */
    @FormUrlEncoded
    @POST("fav/add.json")
    Flowable<HttpBase<JsonElement>> postAddCollect(@FieldMap() Map<String, String> params);

    /**
     * 取消收藏
     */
    @FormUrlEncoded
    @POST("fav/remove.json")
    Flowable<HttpBase<JsonElement>> postRemoveCollect(@FieldMap() Map<String, String> params);

    /**
     * 连载列表
     */
    //@GET("album/list_to_me.json")
    //Flowable<HttpBase<UserAlbum>> getAlbumList(@QueryMap() Map<String, String> params);

    /**
     * 获取首页的信息
     * */
    //221.179.193.164/huasheng/client/operation/show.json?position=top_tab_data&id=4&page=1&page_size=20
     @GET("operation/show.json")
    Flowable<HttpBase<SelectBean>> getData(@QueryMap Map<String,String> params);
}