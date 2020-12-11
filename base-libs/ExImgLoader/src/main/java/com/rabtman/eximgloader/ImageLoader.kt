package com.rabtman.eximgloader

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.rabtman.eximgloader.glide.GlideApp
import com.rabtman.eximgloader.glide.GlideImageConfig
import com.rabtman.eximgloader.glide.progress.GlideImageViewTarget
import com.rabtman.eximgloader.glide.progress.ProgressManager
import jp.wasabeef.glide.transformations.BitmapTransformation
import okhttp3.Call
import okhttp3.OkHttpClient

object ImageLoader {

    var okHttpClient: Call.Factory = OkHttpClient.Builder().build()

    fun initConfig(client: OkHttpClient? = null) {
        client?.let {
            okHttpClient = it
        }
    }

    inline fun ImageView.loadImage(drawableResId: Int, crossinline builder: GlideImageConfig.Builder.() -> Unit = {}) {
        loadImage(this.context, GlideImageConfig.builder()
                .imageView(this)
                .drawableId(drawableResId)
                .apply(builder)
                .build())
    }

    inline fun ImageView.loadImage(url: String?, crossinline builder: GlideImageConfig.Builder.() -> Unit = {}) {
        loadImage(this.context, GlideImageConfig.builder()
                .imageView(this)
                .url(url)
                .apply(builder)
                .build())
    }

    fun ImageView.loadImage(ctx: Context, url: String?, vararg transformations: BitmapTransformation, isCenterCrop: Boolean = false, isCircleCrop: Boolean = false, isCrossFade: Boolean = true, placeholder: Int = 0, errorPic: Int = 0, fallback: Int = 0, imageLoadListener: ImageLoadListener? = null) {
        loadImage(ctx,
                GlideImageConfig.builder()
                        .imageView(this)
                        .url(url)
                        .isCrossFade(isCrossFade)
                        .isCircleCrop(isCircleCrop)
                        .transformation(*transformations)
                        .placeholder(placeholder)
                        .errorPic(errorPic)
                        .fallback(fallback)
                        .listener(imageLoadListener)
                        .build()
        )
    }

    fun loadImage(context: Context?, config: GlideImageConfig) {
        val view = config.imageView ?: throw NullPointerException("imageView is required")
        context?.takeIf { isValidContextForGlide(it) }?.let { ctx ->
            val glideRequest = if (config.drawableId != 0) {
                GlideApp.with(ctx).load(config.drawableId)
            } else {
                GlideApp.with(ctx).load(config.url)
            }
            glideRequest.apply {
                when (config.cacheStrategy) {
                    0 -> diskCacheStrategy(DiskCacheStrategy.ALL)
                    1 -> diskCacheStrategy(DiskCacheStrategy.NONE)
                    2 -> diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    3 -> diskCacheStrategy(DiskCacheStrategy.DATA)
                    4 -> diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                }

                if (config.isCenterCrop) {
                    centerCrop()
                }
                if (config.isCrossFade) {
                    transition(DrawableTransitionOptions.withCrossFade())
                }
                if (config.isCircleCrop) {
                    circleCrop()
                }
                //glide用它来改变图形的形状
                config.transformation?.let {
                    transform(*it)
                }
                if (config.placeholder != 0) //设置占位符
                {
                    placeholder(config.placeholder)
                }
                if (config.errorPic != 0) //设置错误的图片
                {
                    error(config.errorPic)
                }
                if (config.fallback != 0) //设置请求 url 为空图片
                {
                    fallback(config.fallback)
                }
                config.listener?.let {
                    addListener(object : RequestListener<Drawable?> {

                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable?>?, isFirstResource: Boolean): Boolean {
                            it.loadFail(view, e)
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable?>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            it.loadReady(view)
                            return false
                        }
                    })
                }
                if (config.size != null && config.size[0] > 0 && config.size[0] > 0) {
                    override(config.size[0], config.size[1])
                }

                into(GlideImageViewTarget(view, config.url))
            }

            config.onProgressListener?.let { progressListener ->
                config.url?.let { imgUrl -> ProgressManager.addListener(imgUrl, progressListener) }
            }
        }
    }

    private fun isValidContextForGlide(context: Context?): Boolean {
        if (context == null) {
            return false
        }
        if (context is Activity) {
            if (context.isDestroyed || context.isFinishing) {
                return false
            }
        }
        return true
    }
}