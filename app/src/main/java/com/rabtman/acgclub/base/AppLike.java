package com.rabtman.acgclub.base;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import com.leon.channel.helper.ChannelReaderUtil;
import com.rabtman.common.BuildConfig;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.beta.Beta;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.MobclickAgent.UMAnalyticsConfig;

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

    String processName = getCurProcessName(getApplication());
    boolean defaultProcess = processName.equals(getApplication().getPackageName());

    //获取渠道包值
    String channel = ChannelReaderUtil.getChannel(getApplication());
    if (TextUtils.isEmpty(channel)) {
      channel = "offical";
    }

    //bugly
    BuglyStrategy strategy = new BuglyStrategy();
    strategy.setAppChannel(channel);
    strategy.setAppPackageName(BuildConfig.APPLICATION_ID);
    strategy.setAppVersion(BuildConfig.VERSION_NAME);
    strategy.setUploadProcess(defaultProcess);
    //Bugly.init(getApplication(), BuildConfig.BUGLY_APP_ID, BuildConfig.DEBUG, strategy);

    if (defaultProcess) {
      //umeng初始化
      MobclickAgent
          .startWithConfigure(
              new UMAnalyticsConfig(getApplication(), BuildConfig.UMENG_APP_KEY, channel));
    }
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

  //获取进程名称
  private String getCurProcessName(Context context) {
    int pid = android.os.Process.myPid();
    ActivityManager activityManager = (ActivityManager) context
        .getSystemService(Context.ACTIVITY_SERVICE);
    for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
        .getRunningAppProcesses()) {
      if (appProcess.pid == pid) {
        return appProcess.processName;
      }
    }
    return "";
  }
}
