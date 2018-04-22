package com.rabtman.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import com.rabtman.common.di.component.AppComponent;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 16/12/08
 *     desc  : Utils初始化相关
 * </pre>
 */
public final class Utils {

  @SuppressLint("StaticFieldLeak")
  private static Context context;
  /**
   * 为了配合tinker的使用，将其抽取为静态变量
   */
  private static AppComponent appComponent;

  private Utils() {
    throw new UnsupportedOperationException("u can't instantiate me...");
  }

  /**
   * 初始化工具类
   *
   * @param context 上下文
   */
  public static void init(@NonNull Context context) {
    Utils.context = context.getApplicationContext();
  }

  /**
   * 获取ApplicationContext
   *
   * @return ApplicationContext
   */
  public static Context getContext() {
    if (context != null) {
      return context;
    }
    throw new NullPointerException("u should init first");
  }

  public static void initAppComponent(@NonNull AppComponent appComponent) {
    Utils.appComponent = appComponent;
  }

  public static AppComponent getAppComponent() {
    if (appComponent != null) {
      return appComponent;
    }
    throw new NullPointerException("appComponent need to be initialized");
  }
}