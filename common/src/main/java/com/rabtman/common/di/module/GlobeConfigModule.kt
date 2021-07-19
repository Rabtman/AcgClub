package com.rabtman.common.di.module

import android.app.Application
import com.rabtman.common.utils.FileUtils
import com.rabtman.common.utils.constant.StatusBarConstants
import dagger.Module
import dagger.Provides
import dagger.internal.Preconditions
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import java.io.File
import java.util.*
import javax.inject.Named
import javax.inject.Singleton

@Module
class GlobeConfigModule private constructor(builder: Builder) {

    private val mApiUrl: HttpUrl = builder.apiUrl
    private val mInterceptors = builder.interceptors
    private val mCacheFile: File? = builder.cacheFile
    private val mStatusBarAttr: HashMap<String, Int> = builder.statusBarAttr

    @Singleton
    @Provides
    @Named("OkHttpInterceptors")
    fun provideInterceptors(): List<Interceptor> {
        return mInterceptors.toList()
    }

    @Singleton
    @Provides
    fun provideBaseUrl(): HttpUrl {
        return mApiUrl
    }

    /**
     * 提供缓存文件
     */
    @Singleton
    @Provides
    fun provideCacheFile(application: Application?): File {
        return mCacheFile ?: FileUtils.getCacheFile(application)
    }

    /**
     * 提供系统状态栏属性值
     */
    @Singleton
    @Provides
    fun provideStatusBarAttr(): HashMap<String, Int> {
        return mStatusBarAttr
    }

    class Builder {
        var apiUrl: HttpUrl = "https://api.github.com/".toHttpUrl()
        val interceptors = mutableListOf<Interceptor>()
        var cacheFile: File? = null
        val statusBarAttr = HashMap<String, Int>()

        fun baseurl(baseurl: String?): Builder { //基础url
            baseurl?.takeIf { it.isNotBlank() }?.let {
                apiUrl = it.toHttpUrl()
            } ?: run {
                throw IllegalArgumentException("baseurl can not be empty")
            }
            return this
        }

        fun addInterceptor(interceptor: Interceptor): Builder { //动态添加任意个interceptor
            interceptors.add(interceptor)
            return this
        }

        fun cacheFile(cacheFile: File?): Builder {
            this.cacheFile = cacheFile
            return this
        }

        fun statusBarColor(color: Int): Builder {
            statusBarAttr[StatusBarConstants.COLOR] = color
            return this
        }

        fun statusBarAlpha(alpha: Int): Builder {
            statusBarAttr[StatusBarConstants.ALPHA] = alpha
            return this
        }

        fun build(): GlobeConfigModule {
            Preconditions.checkNotNull(apiUrl, "baseurl is required")
            return GlobeConfigModule(this)
        }
    }

    companion object {
        fun builder(): Builder {
            return Builder()
        }
    }
}