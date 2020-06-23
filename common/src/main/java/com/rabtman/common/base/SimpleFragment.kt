package com.rabtman.common.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.ButterKnife
import butterknife.Unbinder
import com.hss01248.dialog.StyledDialog
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.rabtman.common.base.mvp.IView
import com.rabtman.common.base.widget.loadsir.EmptyCallback
import com.rabtman.common.base.widget.loadsir.LoadingCallback
import com.rabtman.common.base.widget.loadsir.RetryCallback
import com.rabtman.common.di.component.AppComponent
import com.rabtman.common.utils.ToastUtil
import me.yokeyword.fragmentation.SupportFragment

abstract class SimpleFragment : SupportFragment(), IView {
    protected var mView: View? = null
    lateinit var mActivity: SimpleActivity
    lateinit var mContext: Context

    @JvmField
    var isInited = false

    @JvmField
    protected var isVisibled = false

    @JvmField
    protected var mLoadService: LoadService<*>? = null
    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    private var mLoadingDialog: Dialog? = null
    private var mUnBinder: Unbinder? = null
    override fun onAttach(context: Context) {
        mActivity = context as SimpleActivity
        mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(getLayoutId(), null)
        return if (useLoadSir()) {
            mLoadService = LoadSir.getDefault().register(registerTarget()) { v ->
                showPageLoading()
                onPageRetry(v)
            }
            mLoadService?.loadLayout ?: mView
        } else {
            mView
        }
    }

    /**
     * loadsir注册目标
     */
    protected fun registerTarget(): Any {
        return mView ?: mActivity
    }

    /**
     * 是否使用loadsir，默认不使用
     */
    protected open fun useLoadSir(): Boolean {
        return false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mUnBinder = ButterKnife.bind(this, view)
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        isInited = true
        initData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mUnBinder?.unbind()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        isVisibled = true
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        isVisibled = false
    }

    protected val mAppComponent: AppComponent
        protected get() = mActivity.mAppComponent

    protected fun setSwipeRefreshLayout(swipeRefreshLayout: SwipeRefreshLayout?) {
        mSwipeRefreshLayout = swipeRefreshLayout
    }

    override fun showPageLoading() {
        mLoadService?.showCallback(LoadingCallback::class.java)
    }

    override fun showPageEmpty() {
        mLoadService?.showCallback(EmptyCallback::class.java)
    }

    override fun showPageError() {
        mLoadService?.showCallback(RetryCallback::class.java)
    }

    override fun showPageContent() {
        mLoadService?.showSuccess()
    }

    /**
     * 页面重试
     */
    protected open fun onPageRetry(v: View?) {}
    override fun showLoading() {
        mSwipeRefreshLayout?.let {
            if (!it.isRefreshing) {
                it.isRefreshing = true
            }
        } ?: run {
            mLoadingDialog = StyledDialog.buildMdLoading().show()
        }
    }

    override fun hideLoading() {
        mSwipeRefreshLayout?.takeIf { it.isRefreshing }?.let {
            it.isRefreshing = false
        }
        mLoadingDialog?.takeIf { it.isShowing }?.let {
            StyledDialog.dismiss(mLoadingDialog)
        }
    }

    override fun showMsg(stringId: Int) {
        showMsg(getString(stringId))
    }

    override fun showMsg(message: String) {
        ToastUtil.show(mContext, message)
    }

    override fun showError(stringId: Int) {
        showError(getString(stringId))
    }

    override fun showError(message: String) {
        hideLoading()
        ToastUtil.show(mContext, message)
    }

    protected abstract fun getLayoutId(): Int
    protected abstract fun initData()
}