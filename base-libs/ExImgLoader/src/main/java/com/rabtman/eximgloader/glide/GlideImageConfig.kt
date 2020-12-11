package com.rabtman.eximgloader.glide

import android.widget.ImageView
import com.rabtman.eximgloader.ImageConfig
import com.rabtman.eximgloader.ImageLoadListener
import com.rabtman.eximgloader.glide.progress.OnProgressListener
import jp.wasabeef.glide.transformations.BitmapTransformation

/**
 * Glide配置信息
 */
class GlideImageConfig private constructor(builder: Builder) : ImageConfig() {
    /**
     * 缓存策略:
     * 0对应DiskCacheStrategy.all
     * 1对应DiskCacheStrategy.NONE
     * 2对应DiskCacheStrategy.RESOURCE
     * 3对应DiskCacheStrategy.DATA
     * 4对应DiskCacheStrategy.AUTOMATIC
     */
    val cacheStrategy: Int
    val transformation: Array<out BitmapTransformation>?
    val isCrossFade: Boolean
    val isCenterCrop: Boolean
    val isCircleCrop: Boolean
    val fallback: Int
    val size: IntArray?
    val onProgressListener: OnProgressListener?

    class Builder {
        var url: String? = null
        var drawableId = 0
        var imageView: ImageView? = null
        var placeholder = 0
        var errorPic = 0

        //请求 url 为空,则使用此图片作为占位符
        var fallback = 0
        var cacheStrategy = 0
        var transformation: Array<out BitmapTransformation>? = null

        //图片加载进度监听
        var listener: ImageLoadListener? = null
        var size: IntArray? = null
        var isCrossFade = true
        var isCenterCrop = false
        var isCircleCrop = false
        var onProgressListener: OnProgressListener? = null

        fun url(url: String?): Builder {
            this.url = url
            return this
        }

        fun drawableId(drawableId: Int): Builder {
            this.drawableId = drawableId
            return this
        }

        fun placeholder(placeholder: Int): Builder {
            this.placeholder = placeholder
            return this
        }

        fun errorPic(errorPic: Int): Builder {
            this.errorPic = errorPic
            return this
        }

        fun fallback(fallback: Int): Builder {
            this.fallback = fallback
            return this
        }

        fun imageView(imageView: ImageView?): Builder {
            this.imageView = imageView
            return this
        }

        fun cacheStrategy(cacheStrategy: Int): Builder {
            this.cacheStrategy = cacheStrategy
            return this
        }

        fun isCenterCrop(isCenterCrop: Boolean): Builder {
            this.isCenterCrop = isCenterCrop
            return this
        }

        fun isCircleCrop(isCircleCrop: Boolean): Builder {
            this.isCircleCrop = isCircleCrop
            return this
        }

        fun isCrossFade(isCrossFade: Boolean): Builder {
            this.isCrossFade = isCrossFade
            return this
        }

        fun transformation(vararg transformation: BitmapTransformation): Builder {
            this.transformation = transformation
            return this
        }

        inline fun listener(
                crossinline loadFail: (target: ImageView?, e: Exception?) -> Unit = { _, _ -> },
                crossinline loadReady: (target: ImageView?) -> Unit = {}
        ) = listener(object : ImageLoadListener {
            override fun loadFail(target: ImageView?, e: Exception?) = loadFail(target, e)
            override fun loadReady(target: ImageView?) = loadReady(target)
        })

        fun listener(listener: ImageLoadListener?): Builder {
            this.listener = listener
            return this
        }

        inline fun progressListener(
                crossinline onProgress: (isComplete: Boolean, percentage: Int, bytesRead: Long, totalBytes: Long) -> Unit = { _, _, _, _ -> }
        ) = progressListener(object : OnProgressListener {
            override fun onProgress(isComplete: Boolean, percentage: Int, bytesRead: Long, totalBytes: Long) = onProgress(isComplete, percentage, bytesRead, totalBytes)
        })

        fun progressListener(onProgressListener: OnProgressListener?): Builder {
            this.onProgressListener = onProgressListener
            return this
        }

        fun override(width: Int, height: Int): Builder {
            size = intArrayOf(width, height)
            return this
        }

        fun build(): GlideImageConfig {
            return GlideImageConfig(this)
        }
    }

    companion object {
        fun builder(): Builder {
            return Builder()
        }
    }

    init {
        url = builder.url
        imageView = builder.imageView
        drawableId = builder.drawableId
        placeholder = builder.placeholder
        listener = builder.listener
        onProgressListener = builder.onProgressListener
        errorPic = builder.errorPic
        fallback = builder.fallback
        isCrossFade = builder.isCrossFade
        isCenterCrop = builder.isCenterCrop
        isCircleCrop = builder.isCircleCrop
        cacheStrategy = builder.cacheStrategy
        transformation = builder.transformation
        size = builder.size
    }
}