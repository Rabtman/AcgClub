package com.rabtman.business.base

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.rabtman.common.base.CommonApplicationLike
import com.rabtman.common.di.module.GlobeConfigModule
import com.rabtman.common.integration.ConfigModule
import com.rabtman.common.integration.IRepositoryManager
import com.umeng.analytics.MobclickAgent

class BusinessConfig : ConfigModule {
    override fun applyOptions(context: Context, builder: GlobeConfigModule.Builder) {

    }

    override fun registerComponents(context: Context, repositoryManager: IRepositoryManager) {

    }

    override fun injectAppLifecycle(
        context: Context,
        lifecycles: MutableList<CommonApplicationLike.Lifecycle>
    ) {

    }

    override fun injectActivityLifecycle(
        context: Context,
        lifecycles: MutableList<Application.ActivityLifecycleCallbacks>
    ) {
        lifecycles.add(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity?, p1: Bundle?) {

            }

            override fun onActivityStarted(activity: Activity?) {

            }

            override fun onActivityResumed(activity: Activity?) {
                MobclickAgent.onResume(activity)
            }

            override fun onActivityPaused(activity: Activity?) {
                MobclickAgent.onPause(activity)
            }

            override fun onActivityStopped(activity: Activity?) {

            }

            override fun onActivitySaveInstanceState(activity: Activity?, p1: Bundle?) {

            }

            override fun onActivityDestroyed(activity: Activity?) {

            }
        })
    }
}