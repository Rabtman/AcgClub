package com.rabtman.acgmusic.base

import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import android.content.Intent
import com.rabtman.acgmusic.api.AcgMusicService
import com.rabtman.acgmusic.service.MusicPlayService
import com.rabtman.common.base.CommonApplicationLike.Lifecycle
import com.rabtman.common.di.module.GlobeConfigModule.Builder
import com.rabtman.common.integration.ConfigModule
import com.rabtman.common.integration.IRepositoryManager

/**
 * @author Rabtman
 */

class AcgMusicConfig : ConfigModule {

    override fun applyOptions(context: Context, builder: Builder) {

    }

    override fun registerComponents(context: Context, repositoryManager: IRepositoryManager) {
        repositoryManager.injectRetrofitService(AcgMusicService::class.java)
    }

    override fun injectAppLifecycle(context: Context, lifecycles: MutableList<Lifecycle>) {
        lifecycles.add(object : Lifecycle {
            override fun onCreate(application: Application?) {
                application?.startService(Intent(application, MusicPlayService::class.java))
            }

            override fun onTerminate(application: Application?) {
                application?.stopService(Intent(application, MusicPlayService::class.java))
            }

        })
    }

    override fun injectActivityLifecycle(context: Context,
                                         lifecycles: List<ActivityLifecycleCallbacks>) {
    }
}
