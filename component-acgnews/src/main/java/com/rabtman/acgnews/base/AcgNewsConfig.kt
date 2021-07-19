package com.rabtman.acgnews.base

import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import com.rabtman.acgnews.api.AcgNewsService
import com.rabtman.common.base.CommonApplicationLike
import com.rabtman.common.di.module.GlobeConfigModule
import com.rabtman.common.integration.ConfigModule
import com.rabtman.common.integration.IRepositoryManager

/**
 * @author Rabtman
 */
class AcgNewsConfig : ConfigModule {
    override fun applyOptions(context: Context, builder: GlobeConfigModule.Builder) {}
    override fun registerComponents(context: Context, repositoryManager: IRepositoryManager) {
        repositoryManager.injectRetrofitService(AcgNewsService::class.java)
    }

    override fun injectAppLifecycle(
        context: Context,
        lifecycles: MutableList<CommonApplicationLike.Lifecycle>
    ) {
    }

    override fun injectActivityLifecycle(
        context: Context,
        lifecycles: MutableList<ActivityLifecycleCallbacks>
    ) {
    }
}