package com.rabtman.common.di.module

import com.google.gson.Gson
import com.rabtman.common.utils.FileUtils
import dagger.Module
import dagger.Provides
import io.rx_cache2.internal.RxCache
import io.victoralbertos.jolyglot.GsonSpeaker
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * @author Rabtman
 */
@Module
class ClientModule {

    companion object {
        private const val TIME_OUT = 30
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        builder: Retrofit.Builder,
        client: OkHttpClient,
        httpUrl: HttpUrl
    ): Retrofit {
        return builder
            .baseUrl(httpUrl) //域名
            .client(client) //设置okhttp
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //使用rxjava
            .addConverterFactory(GsonConverterFactory.create()) //使用Gson
            .build()
    }

    /**
     * 提供OkhttpClient
     */
    @Singleton
    @Provides
    fun provideClient(
        okHttpClient: OkHttpClient.Builder,
        @Named("OkHttpInterceptors") interceptors: List<@JvmSuppressWildcards Interceptor>
    ): OkHttpClient {
        val builder = okHttpClient
            .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
        //如果外部提供了interceptor的数组则遍历添加
        for (interceptor in interceptors) {
            builder.addInterceptor(interceptor)
        }
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
    }

    @Singleton
    @Provides
    fun provideClientBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
    }

    /**
     * 提供RXCache客户端
     *
     * @param cacheDirectory RxCache缓存路径
     */
    @Singleton
    @Provides
    fun provideRxCache(@Named("RxCacheDirectory") cacheDirectory: File, gson: Gson): RxCache {
        return RxCache.Builder()
            .persistence(cacheDirectory, GsonSpeaker(gson))
    }

    /**
     * 需要单独给RxCache提供缓存路径
     * 提供RxCache缓存地址
     */
    @Singleton
    @Provides
    @Named("RxCacheDirectory")
    fun provideRxCacheDirectory(cacheDir: File): File {
        val cacheDirectory = File(cacheDir, "RxCache")
        return FileUtils.makeDirs(cacheDirectory)
    }
}