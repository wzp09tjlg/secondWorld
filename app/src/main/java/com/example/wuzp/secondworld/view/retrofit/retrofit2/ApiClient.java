package com.example.wuzp.secondworld.view.retrofit.retrofit2;

import com.example.wuzp.secondworld.BuildConfig;
import com.example.wuzp.secondworld.network.*;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wuzp on 2017/3/19.
  创建一个Retrofit的实例 用来网络访问
 */
public class ApiClient {
    private static final int S_DEFAULT_TIMEOUT = 15;
  private static Retrofit retrofit = null;
  private static ApiStores apiStores = null;

    public static Retrofit getRetrofit(){
        if(retrofit == null){
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(S_DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(S_DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(S_DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            // 添加后置参数
            builder.addInterceptor(new OkHttpInterceptor());

            if (BuildConfig.DEBUG) {
                // Log信息拦截器
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                //设置 Debug Log 模式
                builder.addInterceptor(loggingInterceptor);
            }

            OkHttpClient okHttpClient = builder.build();

                    //以builder的形式去创建一个实例 设置属性的顺序是无关紧要的，因为在最后的build方法中
            // 设置相关属性 那就是一样的了
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiFinal.HOST)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    //获取ApiService的对象 就能方便调用在Service定义各个方法了
    public static ApiStores getApiService(){
      if(retrofit == null){
          retrofit = getRetrofit();
      }
      if(apiStores == null){
          apiStores = retrofit.create(ApiStores.class);
      }
      return apiStores;
    }

}
