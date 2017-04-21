package com.rabtman.common.di.module;

import com.rabtman.common.imageloader.BaseImageLoaderStrategy;
import com.rabtman.common.imageloader.glide.GlideImageLoaderStrategy;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;


@Module
public class ImageModule {

  @Singleton
  @Provides
  public BaseImageLoaderStrategy provideImageLoaderStrategy(
      GlideImageLoaderStrategy glideImageLoaderStrategy) {
    return glideImageLoaderStrategy;
  }

}
