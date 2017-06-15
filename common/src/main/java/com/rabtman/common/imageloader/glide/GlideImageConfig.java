package com.rabtman.common.imageloader.glide;

import android.widget.ImageView;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.rabtman.common.imageloader.ImageConfig;

/**
 * Glide配置信息
 */
public class GlideImageConfig extends ImageConfig {

  //缩放策略
  public final static int CENTER_CROP = 1;
  public final static int FIT_CENTER = 2;

  private int cacheStrategy;//0对应DiskCacheStrategy.all,1对应DiskCacheStrategy.NONE,2对应DiskCacheStrategy.SOURCE,3对应DiskCacheStrategy.RESULT
  private int zoomStrategy;
  private BitmapTransformation transformation;//glide用它来改变图形的形状

  private GlideImageConfig(Buidler builder) {
    this.url = builder.url;
    this.imageView = builder.imageView;
    this.placeholder = builder.placeholder;
    this.errorPic = builder.errorPic;
    this.cacheStrategy = builder.cacheStrategy;
    this.zoomStrategy = builder.zoomStrategy;
    this.transformation = builder.transformation;
  }

  public static Buidler builder() {
    return new Buidler();
  }

  public int getCacheStrategy() {
    return cacheStrategy;
  }

  public int getZoomStrategy() {
    return zoomStrategy;
  }

  public BitmapTransformation getTransformation() {
    return transformation;
  }

  public static final class Buidler {

    private String url;
    private ImageView imageView;
    private int placeholder;
    private int errorPic;
    private int cacheStrategy;//0对应DiskCacheStrategy.all,1对应DiskCacheStrategy.NONE,2对应DiskCacheStrategy.SOURCE,3对应DiskCacheStrategy.RESULT
    private int zoomStrategy;
    private BitmapTransformation transformation;//glide用它来改变图形的形状

    private Buidler() {
    }

    public Buidler url(String url) {
      this.url = url;
      return this;
    }

    public Buidler placeholder(int placeholder) {
      this.placeholder = placeholder;
      return this;
    }

    public Buidler errorPic(int errorPic) {
      this.errorPic = errorPic;
      return this;
    }

    public Buidler imagerView(ImageView imageView) {
      this.imageView = imageView;
      return this;
    }

    public Buidler cacheStrategy(int cacheStrategy) {
      this.cacheStrategy = cacheStrategy;
      return this;
    }

    public Buidler zoomStrategy(int zoomStrategy) {
      this.zoomStrategy = zoomStrategy;
      return this;
    }

    public Buidler transformation(BitmapTransformation transformation) {
      this.transformation = transformation;
      return this;
    }


    public GlideImageConfig build() {
      if (url == null) {
        throw new IllegalStateException("url is required");
      }
      if (imageView == null) {
        throw new IllegalStateException("imageview is required");
      }
      return new GlideImageConfig(this);
    }
  }
}
