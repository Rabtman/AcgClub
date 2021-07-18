package com.rabtman.common.base.mvp

import com.rabtman.common.bus.RxBus
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

open class BasePresenter<M : IModel, V : IView>(val mModel: M, val mView: V) : IPresenter {
    protected val TAG = this.javaClass.simpleName
    protected var mCompositeDisposable: CompositeDisposable? = null

    init {
        onStart()
    }

    override fun onStart() {
        //if (useEventBus())//如果要使用bus请将此方法返回true
        //EventBus.getDefault().register(this);//注册
    }

    override fun onDestroy() {
        unSubscribe()
        mModel?.onDestroy()
    }

    /**
     * 是否使用eventBus,默认为使用(true)，
     */
    protected fun useEventBus(): Boolean {
        return true
    }

    protected fun unSubscribe() {
        mCompositeDisposable?.dispose()
    }

    protected fun addSubscribe(subscription: Disposable) {
        mCompositeDisposable?.add(subscription) ?: run {
            mCompositeDisposable = CompositeDisposable().apply {
                add(subscription)
            }
        }
    }

    protected fun <U> addRxBusSubscribe(eventType: Class<U>, act: Consumer<U>) {
        mCompositeDisposable?.add(RxBus.default.toDefaultFlowable(eventType, act)) ?: run {
            mCompositeDisposable = CompositeDisposable().apply {
                add(RxBus.default.toDefaultFlowable(eventType, act))
            }
        }
    }
}