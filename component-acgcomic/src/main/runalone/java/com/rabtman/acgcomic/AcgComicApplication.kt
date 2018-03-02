package com.rabtman.acgcomic

import com.rabtman.common.base.BaseApplication
import com.rabtman.common.utils.LogUtil

/**
 * @author Rabtman
 */

class AcgComicApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        LogUtil.init(BuildConfig.DEBUG, BuildConfig.APPLICATION_ID)
    }

    override fun onDefaultProcessCreate() {

    }
}
