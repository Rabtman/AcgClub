package com.rabtman.common.base

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import butterknife.ButterKnife
import butterknife.Unbinder
import com.hss01248.dialog.StyledDialog
import com.jaeger.library.StatusBarUtil
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.rabtman.common.base.mvp.IView
import com.rabtman.common.base.widget.loadsir.EmptyCallback
import com.rabtman.common.base.widget.loadsir.LoadingCallback
import com.rabtman.common.base.widget.loadsir.RetryCallback
import com.rabtman.common.di.component.AppComponent
import com.rabtman.common.utils.ToastUtil
import com.rabtman.common.utils.Utils
import com.rabtman.common.utils.constant.StatusBarConstants
import com.umeng.analytics.MobclickAgent
import me.yokeyword.fragmentation.SupportActivity

abstract class SimpleActivity : SupportActivity(), IView {

    lateinit var mAppComponent: AppComponent
    lateinit var mContext: Activity

    @JvmField
    protected var mLoadService: LoadService<*>? = null
    private var mLoadingDialog: Dialog? = null
    private var mUnBinder: Unbinder? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        mUnBinder = ButterKnife.bind(this)
        mContext = this
        mAppComponent = Utils.getAppComponent()
        setStatusBar()
        if (useLoadSir()) {
            mLoadService = LoadSir.getDefault().register(registerTarget()) { v ->
                showPageLoading()
                onPageRetry(v)
            }
        }
        onViewCreated()
        initData()
    }

    /**
     * loadsir注册目标，默认为自身acitivity
     */
    protected open fun registerTarget(): Any? {
        return this
    }

    /**
     * 是否使用loadsir，默认不使用
     */
    protected open fun useLoadSir(): Boolean {
        return false
    }

    override fun onResume() {
        super.onResume()
        MobclickAgent.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mUnBinder?.let {
            if (it !== Unbinder.EMPTY) {
                it.unbind()
            }
        }
        mUnBinder = null
    }

    protected open fun setToolBar(toolbar: Toolbar, title: String = "") {
        toolbar.title = title
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedSupport() }
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
        mLoadingDialog = StyledDialog.buildMdLoading().show()
    }

    override fun hideLoading() {
        if (mLoadingDialog?.isShowing == true) {
            StyledDialog.dismiss(mLoadingDialog)
        }
    }

    override fun showMsg(stringId: Int) {
        showMsg(getString(stringId))
    }

    override fun showMsg(message: String?) {
        ToastUtil.show(mContext, message)
    }

    override fun showError(stringId: Int) {
        showError(getString(stringId))
    }

    override fun showError(message: String?) {
        hideLoading()
        ToastUtil.show(mContext, message)
    }

    protected open fun setStatusBar() {
        StatusBarUtil.setColor(mContext,
                ContextCompat.getColor(mContext,
                        mAppComponent.statusBarAttr()[StatusBarConstants.COLOR] ?: 0),
                mAppComponent.statusBarAttr()[StatusBarConstants.ALPHA] ?: 0
        )
    }

    protected open fun onViewCreated() {}
    protected abstract fun getLayoutId(): Int
    protected abstract fun initData()
}