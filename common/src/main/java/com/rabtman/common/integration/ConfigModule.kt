package com.rabtman.common.integration

import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import com.rabtman.common.base.CommonApplicationLike
import com.rabtman.common.di.module.GlobeConfigModule

/**
 * 此接口可以给框架配置一些参数,需要实现类实现后,并在AndroidManifest中声明该实现类
 * Created by jess on 12/04/2017 11:37
 * Contact with jess.yan.effort@gmail.com
 */
interface ConfigModule {
    /**
     * 使用[GlobeConfigModule.Builder]给框架配置一些配置参数
     */
    fun applyOptions(context: Context, builder: GlobeConfigModule.Builder)

    /**
     * 使用[IRepositoryManager]给框架注入一些网络请求和数据缓存等服务
     */
    fun registerComponents(context: Context, repositoryManager: IRepositoryManager)

    /**
     * 使用[CommonApplicationLike.Lifecycle]在Application的声明周期中注入一些操作
     */
    fun injectAppLifecycle(
        context: Context,
        lifecycles: MutableList<CommonApplicationLike.Lifecycle>
    )

    /**
     * 使用[Application.ActivityLifecycleCallbacks]在Activity的生命周期中注入一些操作
     */
    fun injectActivityLifecycle(
        context: Context,
        lifecycles: MutableList<ActivityLifecycleCallbacks>
    )
}