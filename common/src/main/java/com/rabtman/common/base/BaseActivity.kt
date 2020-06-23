package com.rabtman.common.base

import androidx.appcompat.widget.Toolbar
import com.rabtman.common.base.mvp.BasePresenter
import com.rabtman.common.di.component.AppComponent
import javax.inject.Inject

abstract class BaseActivity<P : BasePresenter<*, *>> : SimpleActivity() {

    @Inject
    lateinit var mPresenter: P
    override fun onViewCreated() {
        super.onViewCreated()
        setupActivityComponent(mAppComponent) //依赖注入
    }

    override fun setToolBar(toolbar: Toolbar, title: String) {
        toolbar.title = title
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedSupport() }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::mPresenter.isInitialized) {
            mPresenter.onDestroy()
        }
    }

    /**
     * 提供AppComponent(提供所有的单例对象)给子类，进行Component依赖
     */
    protected abstract fun setupActivityComponent(appComponent: AppComponent)
    abstract override fun getLayoutId(): Int
    abstract override fun initData()
}