package com.rabtman.acgclub.base;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import com.tencent.bugly.beta.Beta;
import com.tencent.tinker.loader.app.DefaultApplicationLike;

/**
 * @author Rabtman
 */

public class AppLike extends DefaultApplicationLike {

  public AppLike(Application application, int tinkerFlags,
      boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime,
      long applicationStartMillisTime,
      Intent tinkerResultIntent) {
    super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime,
        applicationStartMillisTime, tinkerResultIntent);
  }

  @Override
  public void onCreate() {
    super.onCreate();

  }

  @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
  @Override
  public void onBaseContextAttached(Context base) {
    super.onBaseContextAttached(base);
    //MultiDex.install(base);
    // 安装tinker
    // TinkerManager.installTinker(this); 替换成下面Bugly提供的方法
    Beta.installTinker(this);
  }

  @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
  public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
    getApplication().registerActivityLifecycleCallbacks(callbacks);
  }

}
