package com.rabtman.acgclub.base;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import androidx.multidex.MultiDex;
import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.leon.channel.helper.ChannelReaderUtil;
import com.rabtman.acgclub.BuildConfig;
import com.rabtman.common.base.CommonApplicationLike;
import com.rabtman.common.utils.SystemUtils;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.tinker.TinkerManager;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.MobclickAgent.UMAnalyticsConfig;

/**
 * @author Rabtman
 */

public class AcgClubApp extends Application {

  private CommonApplicationLike mCommonApplicationLike;
  private String mChannel = "official";

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
    MultiDex.install(base);
    Beta.installTinker(this);

    if (mCommonApplicationLike == null) {
      mCommonApplicationLike = new CommonApplicationLike(TinkerManager.getApplication());
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

  //阿里用户反馈
  private void initFeedback() {
    FeedbackAPI
        .init(getApplication(), BuildConfig.FEEDBACK_APP_KEY, BuildConfig.FEEDBACK_APP_SECRET);
  }
}
