package com.rabtman.common.base

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.hss01248.dialog.StyledDialog
import com.kingja.loadsir.core.LoadSir
import com.rabtman.common.BuildConfig
import com.rabtman.common.R
import com.rabtman.common.base.widget.loadsir.*
import com.rabtman.common.di.component.AppComponent
import com.rabtman.common.di.component.DaggerAppComponent
import com.rabtman.common.di.module.AppModule
import com.rabtman.common.di.module.ClientModule
import com.rabtman.common.di.module.GlobeConfigModule
import com.rabtman.common.integration.ActivityLifecycle
import com.rabtman.common.integration.ConfigModule
import com.rabtman.common.integration.ManifestParser
import com.rabtman.common.utils.LogUtil
import com.rabtman.common.utils.SPUtils
import com.rabtman.common.utils.Utils
import com.rabtman.common.utils.constant.SPConstants

import io.reactivex.plugins.RxJavaPlugins
import io.realm.Realm
import java.util.*
import javax.inject.Inject

/**
 * Common Application的生命周期代理
 */
class CommonApplicationLike(private var mApplication: Application) : IApplicationLike {

    private val mModules: List<ConfigModule> = ManifestParser(mApplication).parse()

    @Inject
    lateinit var mActivityLifecycle: ActivityLifecycle
    private lateinit var mAppComponent: AppComponent
    private val mLifecycles = ArrayList<Lifecycle>()
    private val mActivityLifecycles = ArrayList<Application.ActivityLifecycleCallbacks>()

    init {
        for (module in mModules) {
            module.injectAppLifecycle(mApplication, mLifecycles)
            module.injectActivityLifecycle(mApplication, mActivityLifecycles)
        }
    }

    override fun onCreate() {
        mAppComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(mApplication)) ////提供application
            .clientModule(ClientModule()) //用于提供okhttp和retrofit的单例
            .globeConfigModule(getGlobeConfigModule(mApplication, mModules)) //全局配置
            .build()
        mAppComponent.inject(this)
        Utils.initAppComponent(mAppComponent)

        //router
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
            ARouter.printStackTrace()
        }
        ARouter.init(mApplication)

        //db init
        Realm.init(mApplication)

        //init utils
        Utils.init(mApplication)

        //log
        LogUtil.init(BuildConfig.DEBUG)

        //loadsir init
        LoadSir.beginBuilder()
            .addCallback(LoadingCallback())
            .addCallback(EmptyCallback())
            .addCallback(EmptyCollectionCallback())
            .addCallback(PlaceholderCallback())
            .addCallback(RetryCallback())
            .setDefaultCallback(LoadingCallback::class.java)
            .commit()
        mApplication.registerActivityLifecycleCallbacks(mActivityLifecycle)
        for (module in mModules) {
            module.registerComponents(mApplication, mAppComponent.repositoryManager())
        }
        for (lifecycle in mLifecycles) {
            lifecycle.onCreate(mApplication)
        }
        for (activityLifecycle in mActivityLifecycles) {
            mApplication.registerActivityLifecycleCallbacks(activityLifecycle)
        }

        //初始化全局dialog
        StyledDialog.init(mApplication)

        //rx全局异常处理
        setRxJavaErrorHandler()
    }

    /**
     * RxJava2 当取消订阅后(dispose())，RxJava抛出的异常后续无法接收(此时后台线程仍在跑，可能会抛出IO等异常),全部由RxJavaPlugin接收，需要提前设置ErrorHandler
     * 详情：http://engineering.rallyhealth.com/mobile/rxjava/reactive/2017/03/15/migrating-to-rxjava-2.html#Error
     * Handling
     */
    private fun setRxJavaErrorHandler() {
        RxJavaPlugins.setErrorHandler { throwable ->
            LogUtil.d("setRxJavaErrorHandler:")
            throwable.printStackTrace()
        }
    }

    fun onDefaultProcessCreate() {
        recordVersionCode()
    }

    /**
     * 记录app版本号
     * 可以通过这个记录后期做一些新旧版本切换埋点和额外操作
     */
    private fun recordVersionCode() {
        val hisVersion = SPUtils.getInstance().getInt(SPConstants.APP_VERSION_CODE, -1)
        val curVersion = BuildConfig.appVerCode
        if (hisVersion != curVersion) {
            SPUtils.getInstance().put(SPConstants.APP_VERSION_CODE, curVersion)
        }
    }

    fun onTerminate() {
        mApplication.unregisterActivityLifecycleCallbacks(mActivityLifecycle)
        for (activityLifecycle in mActivityLifecycles) {
            mApplication.unregisterActivityLifecycleCallbacks(activityLifecycle)
        }
        for (lifecycle in mLifecycles) {
            lifecycle.onTerminate(mApplication)
        }
    }

    /**
     * 将app的全局配置信息封装进module(使用Dagger注入到需要配置信息的地方)
     * 需要在AndroidManifest中声明[ConfigModule]的实现类,和Glide的配置方式相似
     */
    private fun getGlobeConfigModule(
        context: Application,
        modules: List<ConfigModule>
    ): GlobeConfigModule {
        val builder = GlobeConfigModule
            .builder()
            .baseurl(
                "https://api.github.com"
            ) //为了防止用户没有通过GlobeConfigModule配置baseurl,而导致报错,所以提前配置个默认baseurl
            .statusBarColor(R.color.colorPrimary) //提供一个默认的状态栏颜色
            .statusBarAlpha(0)
        for (module in modules) {
            module.applyOptions(context, builder)
        }
        return builder.build()
    }

    interface Lifecycle {
        fun onCreate(application: Application?)
        fun onTerminate(application: Application?)
    }
}