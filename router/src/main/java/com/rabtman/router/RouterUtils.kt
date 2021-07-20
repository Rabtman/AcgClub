package com.rabtman.router

import com.alibaba.android.arouter.launcher.ARouter

/**
 * @author Rabtman 在ARouter外再包一层，方便以后替换
 */
object RouterUtils {

    @JvmStatic
    val instance: ARouter
        get() = ARouter.getInstance()
}