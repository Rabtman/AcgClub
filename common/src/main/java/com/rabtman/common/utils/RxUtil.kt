package com.rabtman.common.utils

import com.rabtman.common.http.ApiException
import com.rabtman.common.http.BaseResponse
import io.reactivex.BackpressureStrategy
import io.reactivex.CompletableTransformer
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object RxUtil {
    /**
     * Flowable线程切换简化
     */
    @JvmStatic
    fun <T> rxSchedulerHelper(): FlowableTransformer<T, T> {    //compose简化线程
        return FlowableTransformer { observable ->
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * Completable线程切换简化
     */
    fun completableSchedulerHelper(): CompletableTransformer {
        return CompletableTransformer { observable ->
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * 统一返回结果处理
     */
    fun <T> handleResult(): FlowableTransformer<BaseResponse<T>, T> {
        return FlowableTransformer { httpResponseFlowable ->
            httpResponseFlowable.flatMap { tBaseResponse ->
                /*if (tBaseResponse.code == 200) {
                  return createData(tBaseResponse.data);
                } else {
                  return Flowable.error(new ApiException(tBaseResponse.message));
                }*/
                createData(tBaseResponse.data)
            }
        }
    }

    /**
     * 生成Flowable
     */
    fun <T> createData(t: T?): Flowable<T> {
        return Flowable.create({ emitter ->
            try {
                t?.let {
                    emitter.onNext(it)
                    emitter.onComplete()
                } ?: run {
                    emitter.onError(ApiException("createData data is null"))
                }
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }, BackpressureStrategy.BUFFER)
    }
}