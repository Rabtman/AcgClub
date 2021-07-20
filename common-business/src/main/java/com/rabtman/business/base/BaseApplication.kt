package com.rabtman.business.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.rabtman.business.BuildConfig
import com.rabtman.common.base.CommonApplicationLike
import com.rabtman.common.utils.LogUtil
import com.rabtman.common.utils.SystemUtils
import com.tencent.smtt.sdk.QbSdk
import com.umeng.socialize.Config
import com.umeng.socialize.PlatformConfig
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.common.QueuedWork

abstract class BaseApplication : Application() {

    companion object {
        init {
            //Umeng Share各个平台的配置
            PlatformConfig.setWeixin(BuildConfig.WEIXIN_ID, BuildConfig.WEIXIN_KEY)
            PlatformConfig.setSinaWeibo(
                BuildConfig.SINA_WEIBO_KEY, BuildConfig.SINA_WEIBO_SECRET,
                "http://sns.whalecloud.com"
            )
            PlatformConfig.setQQZone(BuildConfig.QQ_ZONE_ID, BuildConfig.QQ_ZONE_KEY)
        }
    }

    private var mCommonApplicationLike: CommonApplicationLike? = null

    override fun onCreate() {
        super.onCreate()
        mCommonApplicationLike = CommonApplicationLike(this)
        mCommonApplicationLike?.onCreate()
        val defaultProcess = SystemUtils.getCurProcessName() == packageName
        if (defaultProcess) {
            mCommonApplicationLike?.onDefaultProcessCreate()
            onDefaultProcessCreate()
        }
        //leakCanary内存泄露检查
        //LeakCanary.install(this);
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    /**
     * 程序终止的时候执行
     */
    override fun onTerminate() {
        super.onTerminate()
        mCommonApplicationLike?.onTerminate()
        //LeakCanary.uninstall();
        //GodEyeMonitor.shutDown();
    }

    /**
     * 在默认进程中进行操作
     */
    open fun onDefaultProcessCreate() {
        initX5Web(this)
        initUShare(this)
        //installGodEye()
    }

    //Umeng Share
    private fun initUShare(ctx: Context) {
        Config.DEBUG = BuildConfig.DEBUG
        QueuedWork.isUseThreadPool = false
        UMShareAPI.init(ctx, BuildConfig.UMENG_APP_KEY)
    }

    private fun initX5Web(ctx: Context) {
        //x5内核初始化接口
        QbSdk.initX5Environment(ctx, object : QbSdk.PreInitCallback {
            override fun onViewInitFinished(arg0: Boolean) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtil.d("onViewInitFinished is $arg0")
            }

            override fun onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        })
    }

    //AndroidGodEye
    /*private void installGodEye() {
    if (BuildConfig.DEBUG) {
      GodEye.instance().init(mApplication);
      GodEyeMonitor.injectAppInfoConext(new GodEyeMonitor.AppInfoConext() {
        @Override
        public Context getContext() {
          return mApplication;
        }

        @Override
        public Map<String, Object> getAppInfo() {
          Map<String, Object> appInfo = new ArrayMap<>();
          appInfo.put("ApplicationID", BuildConfig.applicationId);
          appInfo.put("VersionName", BuildConfig.appVerName);
          appInfo.put("VersionCode", BuildConfig.appVerCode);
          appInfo.put("BuildType", BuildConfig.BUILD_TYPE);
          return appInfo;
        }
      });
      GodEye.instance()
          .install(new BatteryConfig(mApplication))
          .install(new CpuConfig())
          .install(new CrashConfig(new CrashFileProvider(mApplication)))
          .install(new FpsConfig(mApplication))
          .install(new HeapConfig())
          .install(new LeakConfig(mApplication, new RxPermissionRequest()))
          .install(new PageloadConfig(mApplication))
          .install(new PssConfig(mApplication))
          .install(new RamConfig(mApplication))
          .install(new SmConfig(mApplication))
          .install(new ThreadConfig())
          .install(new TrafficConfig());
      GodEyeMonitor.work(mApplication);
    }
  }*/
}