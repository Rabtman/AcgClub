package com.rabtman.acgclub.base;

import android.content.Context;
import com.rabtman.acgclub.BuildConfig;
import com.rabtman.common.base.BaseApplication;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * @author Rabtman
 */

public class App extends BaseApplication {

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

    initToastyConfig();
    installLeakCanary();//leakCanary内存泄露检查
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
    this.mRefWatcher = BuildConfig.DEBUG ? LeakCanary.install(this) : RefWatcher.DISABLED;
  }
}
