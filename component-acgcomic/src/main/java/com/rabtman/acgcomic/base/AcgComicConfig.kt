package com.rabtman.acgcomic.base

import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import com.rabtman.acgcomic.api.AcgComicService
import com.rabtman.acgcomic.base.constant.SystemConstant
import com.rabtman.common.base.CommonApplicationLike.Lifecycle
import com.rabtman.common.di.module.GlobeConfigModule.Builder
import com.rabtman.common.integration.ConfigModule
import com.rabtman.common.integration.IRepositoryManager
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * @author Rabtman
 */

class AcgComicConfig : ConfigModule {

    override fun applyOptions(context: Context, builder: Builder) {

    }

    override fun registerComponents(context: Context, repositoryManager: IRepositoryManager) {
        repositoryManager.injectRetrofitService(AcgComicService::class.java)
        repositoryManager.injectRealmConfigs(
                RealmConfiguration.Builder()
                        .name(SystemConstant.DB_NAME)
                        .schemaVersion(SystemConstant.DB_VERSION)
                        .modules(Realm.getDefaultModule(), AcgComicRealmModule())
                        .build()
        )
    }

    override fun injectAppLifecycle(context: Context, lifecycles: List<Lifecycle>) {

    }

    override fun injectActivityLifecycle(context: Context,
                                         lifecycles: List<ActivityLifecycleCallbacks>) {
    }
}
