package com.rabtman.common.imageloader.glide.transformations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

public class BlurTransformation extends BitmapTransformation {

  private static int MAX_RADIUS = 25;
  private static int DEFAULT_DOWN_SAMPLING = 1;

  private int radius;
  private int sampling;

  public BlurTransformation() {
    this(MAX_RADIUS, DEFAULT_DOWN_SAMPLING);
  }

  public BlurTransformation(int radius) {
    this(radius, DEFAULT_DOWN_SAMPLING);
  }

  public BlurTransformation(int radius, int sampling) {
    this.radius = radius;
    this.sampling = sampling;
  }

  @Override
  public String key() {
    return "BlurTransformation(radius=" + radius + ", sampling=" + sampling + ")";
  }

  @Override
  protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool pool,
      @NonNull Bitmap toTransform, int outWidth,
      int outHeight) {

    int width = toTransform.getWidth();
    int height = toTransform.getHeight();
    int scaledWidth = width / sampling;
    int scaledHeight = height / sampling;

    Bitmap bitmap = pool.get(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);

    Canvas canvas = new Canvas(bitmap);
    canvas.scale(1 / (float) sampling, 1 / (float) sampling);
    Paint paint = new Paint();
    paint.setFlags(Paint.FILTER_BITMAP_FLAG);
    canvas.drawBitmap(toTransform, 0, 0, paint);

    bitmap = doBlur(context, bitmap, radius);

    return bitmap;
  }

  private Bitmap doBlur(Context context, Bitmap bitmap, int radius)
      throws RSRuntimeException {
    RenderScript rs = null;
    Allocation input = null;
    Allocation output = null;
    ScriptIntrinsicBlur blur = null;
    try {
      rs = RenderScript.create(context);
      rs.setMessageHandler(new RenderScript.RSMessageHandler());
      input = Allocation.createFromBitmap(rs, bitmap, Allocation.MipmapControl.MIPMAP_NONE,
          Allocation.USAGE_SCRIPT);
      output = Allocation.createTyped(rs, input.getType());
      blur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

      blur.setInput(input);
      blur.setRadius(radius);
      blur.forEach(output);
      output.copyTo(bitmap);
    } finally {
      if (rs != null) {
        rs.destroy();
      }
      if (input != null) {
        input.destroy();
      }
      if (output != null) {
        output.destroy();
      }
      if (blur != null) {
        blur.destroy();
      }
    }

    return bitmap;
  }

}