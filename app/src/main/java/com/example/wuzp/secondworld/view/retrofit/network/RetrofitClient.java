package com.example.wuzp.secondworld.view.retrofit.network;

import android.app.usage.ConfigurationStats;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.support.v4.BuildConfig;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wuzp on 2017/3/28.
   其实在编写各个类的时候 针对各个类的使用权限自己应该需要注意，因为在编写代码时
 有些类是内部的，不提供对外暴露的方法，其实如果可以那么这个类就应该是包权限
 有些方法 只有对外暴露的方法 才是共有的方法,在细节中注意自己的能力
 */
public class RetrofitClient {
    Retrofit retrofit;
    ApiStores  apiStores;
    OkHttpClient.Builder builder = new OkHttpClient().newBuilder();

    //在okhttp中创建OKHttpClient的对象时 所使用的是构建者模式，在OKHttpClient中存在一个
    //静态的Builder ，专门用于设置OkHttpClient的各种属性，然后在build()方法时设置Client的属性
    private OkHttpClient getClient(){
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10,TimeUnit.SECONDS);

        if(BuildConfig.DEBUG){
            //添加网络请求的日志 监听网络请求中body中的参数
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }
        OkHttpClient client = builder.build();
        return client;
    }

    private Retrofit getRetrofit(){
        if(retrofit == null){
            retrofit = new Retrofit.
                    Builder().
                    baseUrl(getUrl()).//URL的基本地址
                    addConverterFactory(GsonConverterFactory.create()).//数据转换器
                    addCallAdapterFactory(RxJava2CallAdapterFactory.create())//RxJava的数据数据转换器
                    .build();
        }
        return retrofit;
    }

    private String getUrl(){
        if(BuildConfig.DEBUG){
            return ApiFinal.HOST_TEST_URL;
        }else
            return ApiFinal.HOST_URL;
    }

    //暴露的共有方法.并且保证 整个应用中 只有这样一个网络接口
    public ApiStores getApiStores(){
        if(apiStores == null){
            apiStores = getRetrofit().create(ApiStores.class);
        }
        return apiStores;
    }

    private void checkBuildConfig(){
        //这个类是检查BuildConfig中存在的数据数据
        //可以分辨是debug 还是release版本
        //BuildConfig.DEBUG;//是否是debug版本
        //BuildConfig.APPLICATION_ID;//不知道什么意思
        //BuildConfig.BUILD_TYPE;//编译的类型
        //BuildConfig.FLAVOR;//不知道什么意思
        //BuildConfig.VERSION_CODE;//版本VersionCode
        //BuildConfig.VERSION_NAME;//版本的VersionName
    }

    private void checkSomeConfig(Context context){
        //在Bitmap中 Config是表示图片的存储方式.带有透明通道和图片保存的位数
        /*Bitmap.Config.ALPHA_8;
        Bitmap.Config.ARGB_4444;
        Bitmap.Config.ARGB_8888;
        Bitmap.Config.RGB_565;*/

        //在Util中的Config是一个不推荐使用的类.因为不再维护，所以这个类中返回的都是默认值
        /*Config.DEBUG;
        Config.LOGD;
        Config.LOGV;
        Config.PROFILE;
        Config.RELEASE;*/

        //这个类是描述所有的设备的信息
        //获取方式是
        /*Configuration configuration = context.getResources().getConfiguration();
        configuration.densityDpi;//设备密度
        configuration.fontScale;//字体的放大倍数
        configuration.keyboard;//键盘
        configuration.screenHeightDp;//屏幕的高
        configuration.screenWidthDp;//屏幕的宽*/

        //这个类是安卓机器硬件的配置,在清单文件中 声明的feature ,use-configuration,tags等信息
        ConfigurationInfo configurationInfo =  new ConfigurationInfo();//淡然也还有其他的创建的方式.这里只是一个简单实现的方式
        /*reqTouchScreen; //触摸屏幕
        reqKeyboardType; //键盘类型
        reqNavigation; //虚拟键盘
        reqInputFeatures; //输入法的配置
        reqGlEsVersion;*/ // OpenGL ES version 图片的处理的版本

        //设备的信息 等
        ConfigurationStats configurationStats = null; //


    }
}
