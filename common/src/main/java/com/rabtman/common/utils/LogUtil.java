package com.rabtman.common.utils;

import com.orhanobut.logger.Logger;

/**
 * @author Rabtman
 */
public class LogUtil {

  private static final String TAG = "com.rabtman.acgclub";
  private static boolean mIsDebug = true;

  public static void init(Boolean isDebug) {
    mIsDebug = isDebug;
  }

  public static void e(String tag, Object o) {
    if (mIsDebug) {
      Logger.e(tag, o);
    }
  }

  public static void e(Object o) {
    LogUtil.e(TAG, o);
  }

  public static void w(String tag, Object o) {
    if (mIsDebug) {
      Logger.w(tag, o);
    }
  }

  public static void w(Object o) {
    LogUtil.w(TAG, o);
  }

  public static void d(String msg) {
    if (mIsDebug) {
      Logger.d(msg);
    }
  }

  public static void i(String msg) {
    if (mIsDebug) {
      Logger.i(msg);
    }
  }
}
