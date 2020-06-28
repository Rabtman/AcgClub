package com.rabtman.common.base

import android.content.Context
import android.text.TextUtils
import com.rabtman.common.R
import com.rabtman.common.base.mvp.IView
import com.rabtman.common.http.ApiException
import com.rabtman.common.utils.ToastUtil
import io.reactivex.subscribers.ResourceSubscriber
import retrofit2.HttpException
import java.net.SocketTimeoutException

abstract class CommonSubscriber<T> : ResourceSubscriber<T> {
    private var mContext: Context? = null
    private var mView: IView? = null
    private var mErrorMsg: String? = null
    private var isShowErrorState = true

    protected constructor(context: Context?) {
        mContext = context
    }

    protected constructor(view: IView?) {
        mView = view
    }

    protected constructor(view: IView?, errorMsg: String?) {
        mView = view
        mErrorMsg = errorMsg
    }

    protected constructor(view: IView?, isShowErrorState: Boolean) {
        mView = view
        this.isShowErrorState = isShowErrorState
    }

    protected constructor(view: IView?, errorMsg: String?, isShowErrorState: Boolean) {
        mView = view
        mErrorMsg = errorMsg
        this.isShowErrorState = isShowErrorState
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onComplete() {}
    override fun onError(e: Throwable) {
        e.printStackTrace()
        if (mView == null && mContext == null) {
            return
        }
        showError(e)
    }

    private fun showError(e: Throwable) {
        if (mView != null) {
            if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
                mView?.showError(mErrorMsg)
            } else if (e is ApiException) {
                mView?.showError(e.toString())
            } else if (e is HttpException) {
                mView?.showError(handleHttpExceptionTips(e))
            } else if (e is SocketTimeoutException) {
                mView?.showError(R.string.msg_error_network)
            } else {
                mView?.showError(R.string.msg_error_unknown)
            }
        } else if (mContext != null) {
            if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
                ToastUtil.show(mContext, mErrorMsg)
            } else if (e is ApiException) {
                ToastUtil.show(mContext, e.toString())
            } else if (e is HttpException) {
                val resString = handleHttpExceptionTips(e)
                ToastUtil.show(mContext, mContext!!.getString(resString))
            }
        }
    }

    private fun handleHttpExceptionTips(e: Throwable): Int {
        val code = (e as HttpException).code()
        return if (code == 429) {
            R.string.msg_error_too_fast
        } else if (code == 404) {
            R.string.msg_error_404
        } else {
            R.string.msg_error_network
        }
    }
}