package com.rabtman.acgclub.base;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;
import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.leon.channel.helper.ChannelReaderUtil;
import com.rabtman.acgclub.BuildConfig;
import com.rabtman.common.base.BaseApplication;
import com.rabtman.common.utils.LogUtil;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.MobclickAgent.UMAnalyticsConfig;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;

/**
 * @author Rabtman
 */

public class App extends BaseApplication {

  static {
    //Umeng Share各个平台的配置
    PlatformConfig.setWeixin(BuildConfig.WEIXIN_ID, BuildConfig.WEIXIN_KEY);
    PlatformConfig.setSinaWeibo(BuildConfig.SINA_WEIBO_KEY, BuildConfig.SINA_WEIBO_SECRET,
        "http://sns.whalecloud.com");
    PlatformConfig.setQQZone(BuildConfig.QQ_ZONE_ID, BuildConfig.QQ_ZONE_KEY);
  }

  private RefWatcher mRefWatcher;//leakCanary观察器

  /**
   * 获得leakCanary观察器
   */
  public static RefWatcher getRefWatcher(Context context) {
    App application = (App) context.getApplicationContext();
    return application.mRefWatcher;
  }

  @Override
  public void onCreate() {
    super.onCreate();

    String processName = getCurProcessName(this);
    boolean defaultProcess = processName.equals(getPackageName());

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
      //log
      LogUtil.init(BuildConfig.APP_DEBUG);
      initToastyConfig();
      //umeng初始化
      MobclickAgent
          .startWithConfigure(
              new UMAnalyticsConfig(this, BuildConfig.UMENG_APP_KEY, channel));
      initFeedback();
      initX5Web();
      initUShare();
    }
    installLeakCanary();//leakCanary内存泄露检查
  }

  //Umeng Share
  private void initUShare() {
    Config.DEBUG = BuildConfig.APP_DEBUG;
    QueuedWork.isUseThreadPool = false;
    UMShareAPI.init(this, BuildConfig.UMENG_APP_KEY);

  }

  //阿里用户反馈
  private void initFeedback() {
    FeedbackAPI.init(this, BuildConfig.FEEDBACK_APP_KEY, BuildConfig.FEEDBACK_APP_SECRET);
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
    QbSdk.initX5Environment(getApplicationContext(), new QbSdk.PreInitCallback() {

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
    if (mRefWatcher != null) {
      this.mRefWatcher = null;
    }
  }

  /**
   * 安装leakCanary检测内存泄露
   */
  protected void installLeakCanary() {
    this.mRefWatcher = BuildConfig.APP_DEBUG ? LeakCanary.install(this) : RefWatcher.DISABLED;
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
