package com.rabtman.eximgloader.glide.progress

import android.text.TextUtils
import okhttp3.Call
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.ConcurrentHashMap

/**
 * 进度监听器管理类
 * 加入图片加载进度监听，加入Https支持
 * @author : BaoZhou
 * @date : 2019/3/17 1:49
 */
object ProgressManager {
    private val listenersMap = ConcurrentHashMap<String, OnProgressListener>()
    var okHttpClient: Call.Factory = OkHttpClient.Builder().addNetworkInterceptor { chain: Interceptor.Chain ->
        val request = chain.request()
        val response = chain.proceed(request)
        response.newBuilder().run {
            val body = response.body()
            if (body != null) {
                this.body(ProgressResponseBody(request.url().toString(), LISTENER, body))
            }
            this.build()
        }
    }.build()

    private val LISTENER = object : ProgressResponseBody.InternalProgressListener {
        override fun onProgress(url: String, bytesRead: Long, totalBytes: Long) {
            val onProgressListener = getProgressListener(url)
            if (onProgressListener != null) {
                val percentage = (bytesRead * 1f / totalBytes * 100f).toInt()
                val isComplete = percentage >= 100
                onProgressListener.onProgress(isComplete, percentage, bytesRead, totalBytes)
                if (isComplete) {
                    removeListener(url)
                }
            }
        }
    }

    fun addListener(url: String, listener: OnProgressListener?) {
        if (!TextUtils.isEmpty(url) && listener != null) {
            listenersMap[url] = listener
            listener.onProgress(false, 1, 0, 0)
        }
    }

    fun removeListener(url: String) {
        if (!TextUtils.isEmpty(url)) {
            listenersMap.remove(url)
        }
    }

    fun getProgressListener(url: String): OnProgressListener? {
        return if (TextUtils.isEmpty(url) || listenersMap.size == 0) {
            null
        } else listenersMap[url]
    }
}