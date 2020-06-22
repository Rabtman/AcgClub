package com.rabtman.common.imageloader.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.rabtman.common.imageloader.BaseImageLoaderStrategy;
import com.rabtman.common.imageloader.ImageLoadListener;
import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy<GlideImageConfig> {

  @Inject
  public GlideImageLoaderStrategy() {
  }

  @Override
  public void loadImage(Context ctx, GlideImageConfig config) {
    if (ctx == null) {
      throw new NullPointerException("Context is required");
    }
    if (config == null) {
      throw new NullPointerException("GlideImageConfig is required");
    }
    final ImageView view = config.getImageView();
    if (view == null) {
      throw new NullPointerException("Imageview is required");
    }

    GlideRequest<Drawable> glideRequest = GlideApp.with(ctx).load(config.getUrl())
        .transition(DrawableTransitionOptions.withCrossFade());

    switch (config.getCacheStrategy()) {//缓存策略
      case 0:
        glideRequest.diskCacheStrategy(DiskCacheStrategy.ALL);
        break;
      case 1:
        glideRequest.diskCacheStrategy(DiskCacheStrategy.NONE);
        break;
      case 2:
        glideRequest.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        break;
      case 3:
        glideRequest.diskCacheStrategy(DiskCacheStrategy.DATA);
        break;
      case 4:
        glideRequest.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        break;
    }

    if (config.getTransformation() != null) {//glide用它来改变图形的形状
      glideRequest.transform(config.getTransformation());
    }

    if (config.getPlaceholder() != 0)//设置占位符
    {
      glideRequest.placeholder(config.getPlaceholder());
    }

    if (config.getErrorPic() != 0)//设置错误的图片
    {
      glideRequest.error(config.getErrorPic());
    }

    if (config.getFallback() != 0)//设置请求 url 为空图片
    {
      glideRequest.fallback(config.getFallback());
    }

    final ImageLoadListener listener = config.getListener();
    if (listener != null) {
      glideRequest.listener(new RequestListener<Drawable>() {
        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model,
            Target<Drawable> target,
            boolean isFirstResource) {
          listener.loadFail(view, e);
          return false;
        }

        @Override
        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
            DataSource dataSource, boolean isFirstResource) {
          listener.loadReady(view);
          return false;
        }
      });
    }

    if (config.getSize() != null && config.getSize()[0] > 0 && config.getSize()[0] > 0) {
      glideRequest.override(config.getSize()[0], config.getSize()[1]);
    }

    glideRequest
        .into(view);
  }
}
