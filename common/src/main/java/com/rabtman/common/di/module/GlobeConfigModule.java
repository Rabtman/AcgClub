package com.rabtman.common.di.module;

import static dagger.internal.Preconditions.checkNotNull;

import android.app.Application;
import android.text.TextUtils;
import com.rabtman.common.http.GlobeHttpHandler;
import com.rabtman.common.utils.FileUtils;
import com.rabtman.common.utils.constant.StatusBarConstants;
import dagger.Module;
import dagger.Provides;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.inject.Singleton;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;


@Module
public class GlobeConfigModule {

  private HttpUrl mApiUrl;
  private GlobeHttpHandler mHandler;
  private List<Interceptor> mInterceptors;
  private File mCacheFile;
  private HashMap<String, Integer> mStatusBarAttr;

  /**
   * @author: jess
   * @date 8/5/16 11:03 AM
   * @description: 设置baseurl
   */
  private GlobeConfigModule(Builder builder) {
    this.mApiUrl = builder.apiUrl;
    this.mHandler = builder.handler;
    this.mInterceptors = builder.interceptors;
    this.mCacheFile = builder.cacheFile;
    this.mStatusBarAttr = builder.statusBarAttr;
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


  /**
   * 提供缓存文件
   */
  @Singleton
  @Provides
  File provideCacheFile(Application application) {
    return mCacheFile == null ? FileUtils.getCacheFile(application) : mCacheFile;
  }

  /**
   * 提供系统状态栏属性值
   */
  @Singleton
  @Provides
  HashMap<String, Integer> provideStatusBarAttr() {
    return mStatusBarAttr;
  }

  public static final class Builder {

    private HttpUrl apiUrl = HttpUrl.parse("https://api.github.com/");
    private GlobeHttpHandler handler;
    private List<Interceptor> interceptors = new ArrayList<>();
    private File cacheFile;
    private HashMap<String, Integer> statusBarAttr = new HashMap<>();

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


    public Builder cacheFile(File cacheFile) {
      this.cacheFile = cacheFile;
      return this;
    }

    public Builder statusBarColor(int color) {
      this.statusBarAttr.put(StatusBarConstants.COLOR, color);
      return this;
    }

    public Builder statusBarAlpha(int alpha) {
      this.statusBarAttr.put(StatusBarConstants.ALPHA, alpha);
      return this;
    }

    public GlobeConfigModule build() {
      checkNotNull(apiUrl, "baseurl is required");
      return new GlobeConfigModule(this);
    }


  }


}
