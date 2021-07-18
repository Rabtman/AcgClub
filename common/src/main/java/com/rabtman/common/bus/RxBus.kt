package com.rabtman.common.bus

import com.rabtman.common.utils.RxUtil
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.processors.FlowableProcessor
import io.reactivex.processors.PublishProcessor

class RxBus private constructor() {
    private val bus: FlowableProcessor<Any> = PublishProcessor.create<Any>().toSerialized()

    /**
     * 能保留订阅前最后一个事件
     */
    private val lastBus: FlowableProcessor<Any> = BehaviorProcessor.create<Any>().toSerialized()

    // 提供了一个新的事件
    fun post(o: Any) {
        bus.onNext(o)
    }

    fun postWithLast(o: Any) {
        lastBus.onNext(o)
    }

    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    fun <T> toFlowable(eventType: Class<T>?): Flowable<T> {
        return bus.ofType(eventType)
    }

    // 封装默认订阅
    fun <T> toDefaultFlowable(eventType: Class<T>?, act: Consumer<T>?): Disposable {
        return bus.ofType(eventType).compose(RxUtil.rxSchedulerHelper()).subscribe(act)
    }

    fun <T> toLastFlowable(eventType: Class<T>?): Flowable<T> {
        return lastBus.ofType(eventType)
    }

    private object RxBusHolder {
        val sInstance = RxBus()
    }

    companion object {
        @JvmStatic
        val default: RxBus
            get() = RxBusHolder.sInstance
    }

}