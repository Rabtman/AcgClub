package com.rabtman.acgclub.base;

import android.content.Context;
import com.hss01248.dialog.StyledDialog;
import com.rabtman.acgclub.BuildConfig;
import com.rabtman.common.base.BaseApplication;
import com.rabtman.common.utils.LogUtil;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.smtt.sdk.QbSdk;

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

    initX5Web();
    initToastyConfig();
    //初始化全局dialog
    StyledDialog.init(this);
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
    this.mRefWatcher = BuildConfig.DEBUG ? LeakCanary.install(this) : RefWatcher.DISABLED;
  }
}
