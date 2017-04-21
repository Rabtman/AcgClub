package com.rabtman.common.di.module;

import com.rabtman.common.base.AppManager;
import dagger.Module;
import dagger.Provides;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Rabtman
 */
@Module
public class RemoteModule {

  private static final int TIME_OUT = 10;
  private AppManager mAppManager;


  public RemoteModule(AppManager appManager) {
    this.mAppManager = appManager;
  }

  /**
   * @author: jess
   * @date 8/30/16 1:15 PM
   * @description:提供retrofit
   */
  @Singleton
  @Provides
  Retrofit provideRetrofit(Retrofit.Builder builder, OkHttpClient client, HttpUrl httpUrl) {
    return builder
        .baseUrl(httpUrl)//域名
        .client(client)//设置okhttp
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//使用rxjava
        .addConverterFactory(GsonConverterFactory.create())//使用Gson
        .build();
  }

  /**
   * 提供OkhttpClient
   */
  @Singleton
  @Provides
  OkHttpClient provideClient(OkHttpClient.Builder okHttpClient, Interceptor intercept
      , List<Interceptor> interceptors) {
    OkHttpClient.Builder builder = okHttpClient
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .addNetworkInterceptor(intercept);
    if (interceptors != null && interceptors.size() > 0) {//如果外部提供了interceptor的数组则遍历添加
      for (Interceptor interceptor : interceptors) {
        builder.addInterceptor(interceptor);
      }
    }
    return builder
        .build();
  }


  @Singleton
  @Provides
  Retrofit.Builder provideRetrofitBuilder() {
    return new Retrofit.Builder();
  }


  @Singleton
  @Provides
  OkHttpClient.Builder provideClientBuilder() {
    return new OkHttpClient.Builder();
  }

  /**
   * 提供处理Rxjava错误的管理器
   *
   * @return
   */
    /*@Singleton
    @Provides
    RxErrorHandler proRxErrorHandler(Application application, ResponseErroListener listener) {
        return RxErrorHandler
                .builder()
                .with(application)
                .responseErroListener(listener)
                .build();
    }*/


  /**
   * 提供管理所有activity的管理类
   */
  @Singleton
  @Provides
  AppManager provideAppManager() {
    return mAppManager;
  }

}
