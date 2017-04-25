package com.rabtman.common.imageloader.glide;

import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import com.rabtman.common.base.App;
import com.rabtman.common.di.component.AppComponent;
import com.rabtman.common.utils.FileUtils;
import java.io.File;
import java.io.InputStream;

public class GlideConfiguration implements GlideModule {

  public static final int IMAGE_DISK_CACHE_MAX_SIZE = 100 * 1024 * 1024;//图片缓存文件最大值为100Mb

  @Override
  public void applyOptions(final Context context, GlideBuilder builder) {
    builder.setDiskCache(new DiskCache.Factory() {
      @Override
      public DiskCache build() {
        // Careful: the external cache directory doesn't enforce permissions
        AppComponent appComponent = ((App) context.getApplicationContext()).getAppComponent();
        return DiskLruCacheWrapper
            .get(FileUtils.makeDirs(new File(appComponent.cacheFile(), "Glide")),
                IMAGE_DISK_CACHE_MAX_SIZE);
      }
    });

    MemorySizeCalculator calculator = new MemorySizeCalculator(context);
    int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
    int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

    int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
    int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);

    builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
    builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));

  }

  @Override
  public void registerComponents(Context context, Glide glide) {
    //Glide默认使用HttpURLConnection做网络请求,在这切换成okhttp请求
    AppComponent appComponent = ((App) context.getApplicationContext()).getAppComponent();
    glide.register(GlideUrl.class, InputStream.class,
        new OkHttpUrlLoader.Factory(appComponent.okHttpClient()));
  }
}
