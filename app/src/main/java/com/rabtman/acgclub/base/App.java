package com.rabtman.acgclub.base;

import android.text.TextUtils;
import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.leon.channel.helper.ChannelReaderUtil;
import com.rabtman.acgclub.BuildConfig;
import com.rabtman.common.base.BaseApplication;
import com.rabtman.common.utils.SystemUtils;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.BuglyStrategy;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.MobclickAgent.UMAnalyticsConfig;

/**
 * @author Rabtman
 */

public class App extends BaseApplication {

  @Override
  public void onCreate() {
    super.onCreate();

    boolean defaultProcess = SystemUtils.getCurProcessName().equals(getPackageName());

    //获取渠道包值
    String channel = ChannelReaderUtil.getChannel(this);
    if (TextUtils.isEmpty(channel)) {
      channel = "offical";
    }

    //bugly
    BuglyStrategy strategy = new BuglyStrategy();
    strategy.setAppChannel(channel);
    strategy.setAppPackageName(BuildConfig.APPLICATION_ID);
    strategy.setAppVersion(BuildConfig.VERSION_NAME);
    strategy.setUploadProcess(defaultProcess);
    Bugly.init(this, BuildConfig.BUGLY_APP_ID, BuildConfig.APP_DEBUG, strategy);
    Bugly.setIsDevelopmentDevice(this, BuildConfig.APP_DEBUG);

    if (defaultProcess) {
      //umeng初始化
      MobclickAgent
          .startWithConfigure(
              new UMAnalyticsConfig(this, com.rabtman.common.BuildConfig.UMENG_APP_KEY, channel));
      initFeedback();
    }
  }

  //阿里用户反馈
  private void initFeedback() {
    FeedbackAPI.init(this, BuildConfig.FEEDBACK_APP_KEY, BuildConfig.FEEDBACK_APP_SECRET);
  }
}
