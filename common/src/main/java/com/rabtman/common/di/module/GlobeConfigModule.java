package com.rabtman.common.di.module;

import android.app.Application;
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

/**
 * Created by jessyan on 2016/3/14.
 */
@Module
public class GlobeConfigModule {

  private HttpUrl mApiUrl;
  private GlobeHttpHandler mHandler;
  private List<Interceptor> mInterceptors;

  /**
   * @author: jess
   * @date 8/5/16 11:03 AM
   * @description: 设置baseurl
   */
  private GlobeConfigModule(Buidler buidler) {
    this.mApiUrl = buidler.apiUrl;
    this.mHandler = buidler.handler;
    this.mInterceptors = buidler.interceptors;
  }

  public static Buidler buidler() {
    return new Buidler();
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

  public static final class Buidler {

    private HttpUrl apiUrl = HttpUrl.parse("https://api.github.com/");
    private GlobeHttpHandler handler;
    private List<Interceptor> interceptors = new ArrayList<>();
    private File cacheFile;

    private Buidler() {
    }

    public Buidler baseurl(String baseurl) {//基础url
      if (TextUtils.isEmpty(baseurl)) {
        throw new IllegalArgumentException("baseurl can not be empty");
      }
      this.apiUrl = HttpUrl.parse(baseurl);
      return this;
    }

    public Buidler globeHttpHandler(GlobeHttpHandler handler) {//用来处理http响应结果
      this.handler = handler;
      return this;
    }

    public Buidler addInterceptor(Interceptor interceptor) {//动态添加任意个interceptor
      this.interceptors.add(interceptor);
      return this;
    }

    public Buidler cacheFile(File cacheFile) {
      this.cacheFile = cacheFile;
      return this;
    }


    public GlobeConfigModule build() {
      return new GlobeConfigModule(this);
    }


  }


}
