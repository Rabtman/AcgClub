package com.rabtman.common.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import com.rabtman.common.utils.SystemUtils;


public abstract class BaseApplication extends Application {

  private CommonApplicationLike mCommonApplicationLike;


  @Override
  public void onCreate() {
    super.onCreate();
    this.mCommonApplicationLike = new CommonApplicationLike(this);
    this.mCommonApplicationLike.onCreate();

    boolean defaultProcess = SystemUtils.getCurProcessName().equals(getPackageName());
    if (defaultProcess) {
      this.mCommonApplicationLike.onDefaultProcessCreate();
      this.onDefaultProcessCreate();
    }
  }

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(this);
  }

  /**
   * 程序终止的时候执行
   */
  @Override
  public void onTerminate() {
    super.onTerminate();
    this.mCommonApplicationLike.onTerminate();
  }

  /**
   * 在默认进程中进行操作
   */
  public void onDefaultProcessCreate() {

  }

}
