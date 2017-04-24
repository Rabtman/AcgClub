package com.rabtman.common.di.module;

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
public class ClientModule {

  private static final int TIME_OUT = 10;

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
  OkHttpClient provideClient(OkHttpClient.Builder okHttpClient, List<Interceptor> interceptors) {
    OkHttpClient.Builder builder = okHttpClient
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS);
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

}
