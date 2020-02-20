package com.rabtman.common.imageloader.glide;

import android.graphics.Bitmap;
import android.widget.ImageView;
import com.bumptech.glide.load.Transformation;
import com.rabtman.common.imageloader.ImageConfig;
import com.rabtman.common.imageloader.ImageLoadListener;

/**
 * Glide配置信息
 */
public class GlideImageConfig extends ImageConfig {

  /**
   * 缓存策略:
   * 0对应DiskCacheStrategy.all
   * 1对应DiskCacheStrategy.NONE
   * 2对应DiskCacheStrategy.RESOURCE
   * 3对应DiskCacheStrategy.DATA
   * 4对应DiskCacheStrategy.AUTOMATIC
   */
  private int cacheStrategy;
  private Transformation<Bitmap> transformation;//glide用它来改变图形的形状
  private int fallback;
  private int[] size;

  private GlideImageConfig(Buidler builder) {
    this.url = builder.url;
    this.imageView = builder.imageView;
    this.placeholder = builder.placeholder;
    this.listener = builder.listener;
    this.errorPic = builder.errorPic;
    this.fallback = builder.fallback;
    this.cacheStrategy = builder.cacheStrategy;
    this.transformation = builder.transformation;
    this.size = builder.size;
  }

  public static Buidler builder() {
    return new Buidler();
  }

  public int getCacheStrategy() {
    return cacheStrategy;
  }

  public int getFallback() {
    return fallback;
  }

  public Transformation<Bitmap> getTransformation() {
    return transformation;
  }

  public int[] getSize() {
    return size;
  }

  public static final class Buidler {

    private String url;
    private ImageView imageView;
    private int placeholder;
    private int errorPic;
    private int fallback; //请求 url 为空,则使用此图片作为占位符
    private int cacheStrategy;
    private Transformation<Bitmap> transformation;//glide用它来改变图形的形状
    private ImageLoadListener listener;   //图片加载进度监听
    private int[] size;

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

    public Buidler fallback(int fallback) {
      this.fallback = fallback;
      return this;
    }


    public Buidler imageView(ImageView imageView) {
      this.imageView = imageView;
      return this;
    }

    public Buidler cacheStrategy(int cacheStrategy) {
      this.cacheStrategy = cacheStrategy;
      return this;
    }

    public Buidler transformation(Transformation<Bitmap> transformation) {
      this.transformation = transformation;
      return this;
    }

    public Buidler listener(ImageLoadListener listener) {
      this.listener = listener;
      return this;
    }

    public Buidler override(int width, int height) {
      this.size = new int[]{width, height};
      return this;
    }

    public GlideImageConfig build() {
      return new GlideImageConfig(this);
    }
  }
}
