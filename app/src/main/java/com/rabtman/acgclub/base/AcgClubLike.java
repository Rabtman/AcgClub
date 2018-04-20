package com.rabtman.acgclub.base;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.leon.channel.helper.ChannelReaderUtil;
import com.rabtman.acgclub.BuildConfig;
import com.rabtman.common.base.App;
import com.rabtman.common.base.CommonApplicationLike;
import com.rabtman.common.di.component.AppComponent;
import com.rabtman.common.utils.SystemUtils;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.beta.Beta;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.MobclickAgent.UMAnalyticsConfig;

/**
 * @author Rabtman
 */

public class AcgClubLike extends DefaultApplicationLike implements App {

  private CommonApplicationLike mCommonApplicationLike;
  private String mChannel = "official";

  public AcgClubLike(Application application, int tinkerFlags,
      boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime,
      long applicationStartMillisTime,
      Intent tinkerResultIntent) {
    super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime,
        applicationStartMillisTime, tinkerResultIntent);
  }

  @Override
  public void onCreate() {
    super.onCreate();
    mCommonApplicationLike.onCreate();

    boolean defaultProcess = SystemUtils.getCurProcessName()
        .equals(getApplication().getPackageName());

    //获取渠道包值
    String channel = ChannelReaderUtil.getChannel(getApplication());
    if (!TextUtils.isEmpty(channel)) {
      mChannel = channel;
    }

    //bugly
    BuglyStrategy strategy = new BuglyStrategy();
    strategy.setAppChannel(mChannel);
    strategy.setAppPackageName(BuildConfig.APPLICATION_ID);
    strategy.setAppVersion(BuildConfig.VERSION_NAME);
    strategy.setUploadProcess(defaultProcess);
    Bugly.init(getApplication(), BuildConfig.BUGLY_APP_ID, BuildConfig.APP_DEBUG, strategy);
    Bugly.setIsDevelopmentDevice(getApplication(), BuildConfig.APP_DEBUG);

    if (defaultProcess) {
      mCommonApplicationLike.onDefaultProcessCreate();
      onDefaultProcessCreate();
    }
  }

  @Override
  public void onBaseContextAttached(Context base) {
    super.onBaseContextAttached(base);
    MultiDex.install(getApplication());
    Beta.installTinker(this);

    if (mCommonApplicationLike == null) {
      mCommonApplicationLike = new CommonApplicationLike(getApplication());
    }
  }

  @Override
  public void onTerminate() {
    super.onTerminate();
    mCommonApplicationLike.onTerminate();
  }

  @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
  public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
    getApplication().registerActivityLifecycleCallbacks(callbacks);
  }

  /**
   * 在默认进程中进行操作
   */
  public void onDefaultProcessCreate() {
    //umeng初始化
    MobclickAgent
        .startWithConfigure(
            new UMAnalyticsConfig(getApplication(), com.rabtman.common.BuildConfig.UMENG_APP_KEY,
                mChannel));
    initFeedback();

  }

  /**
   * 将AppComponent返回出去,供其它地方使用, AppComponent接口中声明的方法返回的实例,在getAppComponent()拿到对象后都可以直接使用
   */
  @Override
  public AppComponent getAppComponent() {
    return ((App) mCommonApplicationLike).getAppComponent();
  }

  //阿里用户反馈
  private void initFeedback() {
    FeedbackAPI
        .init(getApplication(), BuildConfig.FEEDBACK_APP_KEY, BuildConfig.FEEDBACK_APP_SECRET);
  }
}
