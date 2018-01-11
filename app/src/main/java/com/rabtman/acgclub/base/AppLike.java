package com.rabtman.acgclub.base;

import android.annotation.TargetApi;
import android.app.ActivityManager;
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
import com.rabtman.common.utils.LogUtil;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.tinker.TinkerManager;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.MobclickAgent.UMAnalyticsConfig;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;

/**
 * @author Rabtman
 */

public class AppLike extends DefaultApplicationLike implements App {

  static {
    //Umeng Share各个平台的配置
    PlatformConfig.setWeixin(BuildConfig.WEIXIN_ID, BuildConfig.WEIXIN_KEY);
    PlatformConfig.setSinaWeibo(BuildConfig.SINA_WEIBO_KEY, BuildConfig.SINA_WEIBO_SECRET,
        "http://sns.whalecloud.com");
    PlatformConfig.setQQZone(BuildConfig.QQ_ZONE_ID, BuildConfig.QQ_ZONE_KEY);
  }

  private CommonApplicationLike mCommonApplicationLike;

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

    this.mCommonApplicationLike = new CommonApplicationLike(TinkerManager.getApplication());
    this.mCommonApplicationLike.onCreate();

    String processName = getCurProcessName(getApplication().getApplicationContext());
    boolean defaultProcess = processName.equals(getApplication().getPackageName());

    //获取渠道包值
    String channel = ChannelReaderUtil.getChannel(getApplication().getApplicationContext());
    if (TextUtils.isEmpty(channel)) {
      channel = "offical";
    }

    //bugly
    BuglyStrategy strategy = new BuglyStrategy();
    strategy.setAppChannel(channel);
    strategy.setAppPackageName(BuildConfig.APPLICATION_ID);
    strategy.setAppVersion(BuildConfig.VERSION_NAME);
    strategy.setUploadProcess(defaultProcess);
    Bugly.init(getApplication(), BuildConfig.BUGLY_APP_ID, true, strategy);
    //上线前，将此设置为false
    Bugly.setIsDevelopmentDevice(getApplication().getApplicationContext(), true);

    if (defaultProcess) {
      //log
      LogUtil.init(BuildConfig.APP_DEBUG);
      initToastyConfig();
      //umeng初始化
      MobclickAgent
          .startWithConfigure(
              new UMAnalyticsConfig(getApplication().getApplicationContext(),
                  BuildConfig.UMENG_APP_KEY, channel));
      initFeedback();
      initX5Web();
      initUShare();
    }
  }

  @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
  @Override
  public void onBaseContextAttached(Context base) {
    super.onBaseContextAttached(base);
    MultiDex.install(base);
    // 安装tinker
    // TinkerManager.installTinker(this); 替换成下面Bugly提供的方法
    Beta.installTinker(this);
  }

  @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
  public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
    getApplication().registerActivityLifecycleCallbacks(callbacks);
  }


  //Umeng Share
  private void initUShare() {
    Config.DEBUG = BuildConfig.APP_DEBUG;
    QueuedWork.isUseThreadPool = false;
    UMShareAPI.init(getApplication().getApplicationContext(), BuildConfig.UMENG_APP_KEY);

  }

  //阿里用户反馈
  private void initFeedback() {
    FeedbackAPI
        .init(getApplication(), BuildConfig.FEEDBACK_APP_KEY, BuildConfig.FEEDBACK_APP_SECRET);
  }

  private void initToastyConfig() {
    /*Toasty.Config.getInstance()
        .setErrorColor( @ColorInt int errorColor) // optional
    .setInfoColor( @ColorInt int infoColor) // optional
    .setSuccessColor( @ColorInt int successColor) // optional
    .setWarningColor( @ColorInt int warningColor) // optional
    .setTextColor( @ColorInt int textColor) // optional
    .tintIcon( boolean tintIcon) // optional (apply textColor also to the icon)
    .setToastTypeface(@NonNull Typeface typeface) // optional
        .apply(); */
  }

  private void initX5Web() {
    //x5内核初始化接口
    QbSdk.initX5Environment(getApplication().getApplicationContext(), new QbSdk.PreInitCallback() {

      @Override
      public void onViewInitFinished(boolean arg0) {
        // TODO Auto-generated method stub
        //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
        LogUtil.d("onViewInitFinished is " + arg0);
      }

      @Override
      public void onCoreInitFinished() {
        // TODO Auto-generated method stub
      }
    });
  }

  @Override
  public void onTerminate() {
    super.onTerminate();
    this.mCommonApplicationLike.onTerminate();
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

  @Override
  public AppComponent getAppComponent() {
    return mCommonApplicationLike.getAppComponent();
  }
}
