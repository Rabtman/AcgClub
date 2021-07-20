package com.rabtman.common.utils

import android.app.Application
import android.content.Context
import com.rabtman.common.di.component.AppComponent

object Utils {

    private var baseApp: Application? = null

    /**
     * 为了配合tinker的使用，将其抽取为静态变量
     */
    private var appComponent: AppComponent? = null

    /**
     * 初始化工具类
     */
    fun init(app: Application) {
        baseApp = app
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    @JvmStatic
    val app: Context
        get() {
            return baseApp?.applicationContext ?: run {
                throw NullPointerException("u should init first")
            }
        }

    fun initAppComponent(appComponent: AppComponent) {
        this.appComponent = appComponent
    }

    @JvmStatic
    fun getAppComponent(): AppComponent {
        return appComponent ?: run {
            throw NullPointerException("appComponent need to be initialized")
        }
    }
}