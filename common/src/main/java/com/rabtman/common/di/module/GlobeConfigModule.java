package com.rabtman.common.di.module;

import android.text.TextUtils;
import com.rabtman.common.http.GlobeHttpHandler;
import dagger.Module;
import dagger.Provides;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Singleton;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;

@Module
public class GlobeConfigModule {

  private HttpUrl mApiUrl;
  private GlobeHttpHandler mHandler;
  private List<Interceptor> mInterceptors;

  private GlobeConfigModule(Builder builder) {
    this.mApiUrl = builder.apiUrl;
    this.mHandler = builder.handler;
    this.mInterceptors = builder.interceptors;
  }

  public static Builder builder() {
    return new Builder();
  }


  @Singleton
  @Provides
  List<Interceptor> provideInterceptors() {
    return mInterceptors;
  }


  @Singleton
  @Provides
  HttpUrl provideBaseUrl() {
    return mApiUrl;
  }


  @Singleton
  @Provides
  GlobeHttpHandler provideGlobeHttpHandler() {
    return mHandler == null ? GlobeHttpHandler.EMPTY : mHandler;//打印请求信息
  }

  public static final class Builder {

    private HttpUrl apiUrl = HttpUrl.parse("https://api.github.com/");
    private GlobeHttpHandler handler;
    private List<Interceptor> interceptors = new ArrayList<>();
    private File cacheFile;

    private Builder() {
    }

    public Builder baseurl(String baseurl) {//基础url
      if (TextUtils.isEmpty(baseurl)) {
        throw new IllegalArgumentException("baseurl can not be empty");
      }
      this.apiUrl = HttpUrl.parse(baseurl);
      return this;
    }

    public Builder globeHttpHandler(GlobeHttpHandler handler) {//用来处理http响应结果
      this.handler = handler;
      return this;
    }

    public Builder addInterceptor(Interceptor interceptor) {//动态添加任意个interceptor
      this.interceptors.add(interceptor);
      return this;
    }

    public GlobeConfigModule build() {
      return new GlobeConfigModule(this);
    }

  }


}
