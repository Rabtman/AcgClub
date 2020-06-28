package com.rabtman.eximgloader.glide

import android.widget.ImageView
import com.rabtman.eximgloader.ImageConfig
import com.rabtman.eximgloader.ImageLoadListener
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
    val fallback: Int
    val size: IntArray?

    class Builder {
        var url: String? = null
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

        fun url(url: String?): Builder {
            this.url = url
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

        fun isCrossFade(isCrossFade: Boolean): Builder {
            this.isCrossFade = isCrossFade
            return this
        }

        fun transformation(vararg transformation: BitmapTransformation): Builder {
            this.transformation = transformation
            return this
        }

        fun listener(listener: ImageLoadListener?): Builder {
            this.listener = listener
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
        placeholder = builder.placeholder
        listener = builder.listener
        errorPic = builder.errorPic
        fallback = builder.fallback
        isCrossFade = builder.isCrossFade
        cacheStrategy = builder.cacheStrategy
        transformation = builder.transformation
        size = builder.size
    }
}