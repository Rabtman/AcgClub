package com.rabtman.acgschedule.base

import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import com.rabtman.acgschedule.base.constant.SystemConstant
import com.rabtman.common.base.CommonApplicationLike
import com.rabtman.common.di.module.GlobeConfigModule
import com.rabtman.common.integration.ConfigModule
import com.rabtman.common.integration.IRepositoryManager
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * @author Rabtman
 */
class AcgScheduleConfig : ConfigModule {
    override fun applyOptions(context: Context, builder: GlobeConfigModule.Builder) {}
    override fun registerComponents(context: Context, repositoryManager: IRepositoryManager) {
        repositoryManager.injectRealmConfigs(
            RealmConfiguration.Builder()
                .name(SystemConstant.DB_NAME)
                .schemaVersion(SystemConstant.DB_VERSION)
                .modules(Realm.getDefaultModule(), AcgScheduleRealmModule())
                .build()
        )
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