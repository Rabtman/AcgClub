package com.rabtman.acgcomic

import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import com.rabtman.acgcomic.api.AcgComicService
import com.rabtman.acgcomic.base.constant.SystemConstant.DB_NAME
import com.rabtman.acgcomic.base.constant.SystemConstant.DB_VERSION
import com.rabtman.common.base.CommonApplicationLike.Lifecycle
import com.rabtman.common.di.module.GlobeConfigModule.Builder
import com.rabtman.common.integration.ConfigModule
import com.rabtman.common.integration.IRepositoryManager
import io.realm.RealmConfiguration
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level

/**
 * @author Rabtman
 */

class AcgComicConfig : ConfigModule {

    override fun applyOptions(context: Context, builder: Builder) {
        builder.addInterceptor(HttpLoggingInterceptor().setLevel(Level.BODY))

    }

    override fun registerComponents(context: Context, repositoryManager: IRepositoryManager) {
        repositoryManager.injectRetrofitService(AcgComicService::class.java)
        repositoryManager.injectRealmConfigs(
                RealmConfiguration.Builder()
                        .name(DB_NAME)
                        .schemaVersion(DB_VERSION)
                        //.modules(Realm.getDefaultModule(), AcgComicRealmModule())
                        .build()
        )
    }

    override fun injectAppLifecycle(context: Context, lifecycles: List<Lifecycle>) {

    }

    override fun injectActivityLifecycle(context: Context,
                                         lifecycles: List<ActivityLifecycleCallbacks>) {
    }
}
