package com.rabtman.common.base;

import android.app.Application;
import android.content.Context;
import com.rabtman.common.di.module.AppModule;
import com.rabtman.common.di.module.GlobeConfigModule;
import com.rabtman.common.di.module.ImageModule;
import com.rabtman.common.di.module.RemoteModule;
import javax.inject.Inject;


public abstract class BaseApplication extends Application {

  static private BaseApplication mApplication;
  protected final String TAG = this.getClass().getSimpleName();
  @Inject
  protected AppManager mAppManager;
  private RemoteModule mRemoteModule;
  private AppModule mAppModule;
  private ImageModule mImagerModule;
  private GlobeConfigModule mGlobeConfigModule;

  /**
   * 返回上下文
   */
  public static Context getContext() {
    return mApplication;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    mApplication = this;
    this.mAppModule = new AppModule(this);//提供application
    DaggerBaseComponent
        .builder()
        .appModule(mAppModule)
        .build()
        .inject(this);
    this.mImagerModule = new ImageModule();//图片加载框架默认使用glide
    this.mRemoteModule = new RemoteModule(mAppManager);//用于提供okhttp和retrofit的单例


  }

  /**
   * 程序终止的时候执行
   */
  @Override
  public void onTerminate() {
    super.onTerminate();
    if (mRemoteModule != null) {
      this.mRemoteModule = null;
    }
    if (mAppModule != null) {
      this.mAppModule = null;
    }
    if (mImagerModule != null) {
      this.mImagerModule = null;
    }
    if (mAppManager != null) {//释放资源
      this.mAppManager.release();
      this.mAppManager = null;
    }
    if (mApplication != null) {
      this.mApplication = null;
    }
  }

  /**
   * 将app的全局配置信息封装进module(使用Dagger注入到需要配置信息的地方)
   */
  protected abstract GlobeConfigModule getGlobeConfigModule();

  public RemoteModule getRemoteModule() {
    return mRemoteModule;
  }

  public AppModule getAppModule() {
    return mAppModule;
  }

  public ImageModule getImageModule() {
    return mImagerModule;
  }

  public AppManager getAppManager() {
    return mAppManager;
  }

}
