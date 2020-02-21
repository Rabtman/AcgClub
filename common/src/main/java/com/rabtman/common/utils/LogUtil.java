package com.rabtman.common.utils;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * @author Rabtman
 */
public class LogUtil {

  private static String mTag = "com.rabtman.acgclub";
  private static boolean mIsDebug = true;

  public static void init(Boolean isDebug) {
    init(isDebug, mTag);
  }

  public static void init(Boolean isDebug, String tag) {
    mIsDebug = isDebug;
    mTag = tag;
    FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
        .tag(mTag)
        .showThreadInfo(false)
        .build();
    Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
      @Override
      public boolean isLoggable(int priority, String tag) {
        return mIsDebug;
      }
    });
  }

  public static void e(String tag, Object o) {
    Logger.e(tag, o);
  }

  public static void e(Object o) {
    LogUtil.e(mTag, o);
  }

  public static void w(String tag, Object o) {
    Logger.w(tag, o);
  }

  public static void w(Object o) {
    LogUtil.w(mTag, o);
  }

  public static void d(String msg) {
    Logger.d(msg);
  }

  public static void i(String msg) {
    Logger.i(msg);
  }
}
