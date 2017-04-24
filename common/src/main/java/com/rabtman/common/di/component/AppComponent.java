package com.rabtman.common.di.component;

import android.app.Application;
import com.google.gson.Gson;
import com.rabtman.common.base.BaseApplication;
import com.rabtman.common.di.module.AppModule;
import com.rabtman.common.di.module.ClientModule;
import com.rabtman.common.di.module.GlobeConfigModule;
import com.rabtman.common.di.module.ImageModule;
import com.rabtman.common.imageloader.ImageLoader;
import com.rabtman.common.integration.AppManager;
import com.rabtman.common.integration.IRepositoryManager;
import dagger.Component;
import java.io.File;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;


@Singleton
@Component(modules = {AppModule.class, ClientModule.class, ImageModule.class,
    GlobeConfigModule.class})
public interface AppComponent {

  Application Application();

  //用于管理网络请求层,以及数据缓存层
  IRepositoryManager repositoryManager();


  OkHttpClient okHttpClient();

  //图片管理器,用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
  ImageLoader imageLoader();

  //gson
  Gson gson();

  //缓存文件根目录(RxCache和Glide的的缓存都已经作为子文件夹在这个目录里),应该将所有缓存放到这个根目录里,便于管理和清理,可在GlobeConfigModule里配置
  File cacheFile();

  //用于管理所有activity
  AppManager appManager();

  void inject(BaseApplication application);
}
