package com.rabtman.common.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class DimenUtils {

  /**
   * Get the size of current screen.
   *
   * @param context the context
   * @return the size
   */
  @SuppressWarnings("deprecation")
  @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
  public static Point getScreenSize(Context context) {
    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    Display display = windowManager.getDefaultDisplay();
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
      return new Point(display.getWidth(), display.getHeight());
    } else {
      Point point = new Point();
      display.getSize(point);
      return point;
    }
  }

  /**
   * Get the width of current screen.
   *
   * @param contex the contex
   * @return the width
   */
  public static int getScreenWidth(Context contex) {
    DisplayMetrics dm = contex.getResources().getDisplayMetrics();
    return dm.widthPixels;
  }

  /**
   * Get the height of current screen.
   *
   * @param contex the mContext
   * @return the height
   */
  public static int getScreenHeight(Context contex) {
    DisplayMetrics dm = contex.getResources().getDisplayMetrics();
    return dm.heightPixels;
  }

  /**
   * Convert dp to pixel.
   *
   * @param context mContext to use
   * @param dp dp
   * @return pixel
   */
  public static int dpToPx(Context context, float dp) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dp * scale + 0.5);
  }

  /**
   * Convert pixel to dp.
   *
   * @param context the mContext to use
   * @param px pixel
   * @return dp
   */
  public static int pxToDp(Context context, float px) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (px / scale + 0.5);
  }
}
