package com.rabtman.common.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * @author Rabtman
 */

public class IntentUtils {

  /**
   * 调起浏览器
   *
   * @param context 上下文
   * @param url 浏览器地址
   */
  public static boolean go2Browser(Context context, String url) {
    Uri uri = Uri.parse(url);
    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    try {
      context.startActivity(intent);
      return true;
    } catch (ActivityNotFoundException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 跳转到应用市场
   *
   * @param context 上下文
   * @param packageName 应用包名
   * @return 跳转是否成功
   */
  public static boolean go2Market(Context context, String packageName) {
    Uri uri = Uri.parse("market://details?id=" + packageName);
    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
    goToMarket.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    try {
      context.startActivity(goToMarket);
      return true;
    } catch (ActivityNotFoundException e) {
      e.printStackTrace();
      return false;
    }
  }

}
