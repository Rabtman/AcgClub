package com.rabtman.common.base

import android.os.Bundle
import android.view.View
import com.rabtman.common.base.mvp.BasePresenter
import com.rabtman.common.di.component.AppComponent
import javax.inject.Inject

abstract class BaseFragment<T : BasePresenter<*, *>> : SimpleFragment() {

    @Inject
    lateinit var mPresenter: T
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFragmentComponent(mAppComponent)
    }

    override fun onDestroyView() {
        if (this::mPresenter.isInitialized) {
            mPresenter.onDestroy()
        }
        super.onDestroyView()
    }

    /**
     * 提供AppComponent(提供所有的单例对象)给子类，进行Component依赖
     */
    protected abstract fun setupFragmentComponent(appComponent: AppComponent)
    abstract override fun getLayoutId(): Int
    abstract override fun initData()
}