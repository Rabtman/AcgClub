package com.rabtman.common.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.rabtman.common.utils.SystemUtils

abstract class BaseApplication : Application() {
    private var mCommonApplicationLike: CommonApplicationLike? = null
    override fun onCreate() {
        super.onCreate()
        mCommonApplicationLike = CommonApplicationLike(this)
        mCommonApplicationLike!!.onCreate()
        val defaultProcess = SystemUtils.getCurProcessName() == packageName
        if (defaultProcess) {
            mCommonApplicationLike!!.onDefaultProcessCreate()
            onDefaultProcessCreate()
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    /**
     * 程序终止的时候执行
     */
    override fun onTerminate() {
        super.onTerminate()
        mCommonApplicationLike!!.onTerminate()
    }

    /**
     * 在默认进程中进行操作
     */
    open fun onDefaultProcessCreate() {}
}