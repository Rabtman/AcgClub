package com.rabtman.common.base;

import android.app.Application;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hss01248.dialog.StyledDialog;
import com.kingja.loadsir.core.LoadSir;
import com.rabtman.common.BuildConfig;
import com.rabtman.common.R;
import com.rabtman.common.base.widget.loadsir.EmptyCallback;
import com.rabtman.common.base.widget.loadsir.EmptyCollectionCallback;
import com.rabtman.common.base.widget.loadsir.LoadingCallback;
import com.rabtman.common.base.widget.loadsir.PlaceholderCallback;
import com.rabtman.common.base.widget.loadsir.RetryCallback;
import com.rabtman.common.di.component.AppComponent;
import com.rabtman.common.di.component.DaggerAppComponent;
import com.rabtman.common.di.module.AppModule;
import com.rabtman.common.di.module.ClientModule;
import com.rabtman.common.di.module.GlobeConfigModule;
import com.rabtman.common.di.module.ImageModule;
import com.rabtman.common.integration.ActivityLifecycle;
import com.rabtman.common.integration.ConfigModule;
import com.rabtman.common.integration.ManifestParser;
import com.rabtman.common.utils.LogUtil;
import com.rabtman.common.utils.SPUtils;
import com.rabtman.common.utils.Utils;
import com.rabtman.common.utils.constant.SPConstants;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import io.realm.Realm;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Common Application的生命周期代理
 */

public class CommonApplicationLike implements IApplicationLike {

  static {
    //Umeng Share各个平台的配置
    PlatformConfig.setWeixin(BuildConfig.WEIXIN_ID, BuildConfig.WEIXIN_KEY);
    PlatformConfig.setSinaWeibo(BuildConfig.SINA_WEIBO_KEY, BuildConfig.SINA_WEIBO_SECRET,
        "http://sns.whalecloud.com");
    PlatformConfig.setQQZone(BuildConfig.QQ_ZONE_ID, BuildConfig.QQ_ZONE_KEY);
  }

  private final List<ConfigModule> mModules;
  @Inject
  protected ActivityLifecycle mActivityLifecycle;
  private Application mApplication;
  private AppComponent mAppComponent;
  private List<Lifecycle> mLifecycles = new ArrayList<>();

  public CommonApplicationLike(Application application) {
    this.mApplication = application;
    this.mModules = new ManifestParser(mApplication).parse();
    for (ConfigModule module : mModules) {
      module.injectAppLifecycle(mApplication, mLifecycles);
    }
  }

  @Override
  public void onCreate() {
    mAppComponent = DaggerAppComponent
        .builder()
        .appModule(new AppModule(mApplication))////提供application
        .clientModule(new ClientModule())//用于提供okhttp和retrofit的单例
        .imageModule(new ImageModule())//图片加载框架默认使用glide
        .globeConfigModule(getGlobeConfigModule(mApplication, mModules))//全局配置
        .build();
    mAppComponent.inject(this);
    Utils.initAppComponent(mAppComponent);

    //router
    if (BuildConfig.DEBUG) {
      ARouter.openLog();
      ARouter.openDebug();
      ARouter.printStackTrace();
    }
    ARouter.init(mApplication);

    //db init
    Realm.init(mApplication);

    //init utils
    Utils.init(mApplication);

    //log
    LogUtil.init(BuildConfig.DEBUG);

    //loadsir init
    LoadSir.beginBuilder()
        .addCallback(new LoadingCallback())
        .addCallback(new EmptyCallback())
        .addCallback(new EmptyCollectionCallback())
        .addCallback(new PlaceholderCallback())
        .addCallback(new RetryCallback())
        .setDefaultCallback(LoadingCallback.class)
        .commit();

    mApplication.registerActivityLifecycleCallbacks(mActivityLifecycle);

    for (ConfigModule module : mModules) {
      module.registerComponents(mApplication, mAppComponent.repositoryManager());
    }

    for (Lifecycle lifecycle : mLifecycles) {
      lifecycle.onCreate(mApplication);
    }

    //初始化全局dialog
    StyledDialog.init(mApplication);

    //leakCanary内存泄露检查
    //LeakCanary.install(mApplication);

    //rx全局异常处理
    setRxJavaErrorHandler();
  }

  /**
   * RxJava2 当取消订阅后(dispose())，RxJava抛出的异常后续无法接收(此时后台线程仍在跑，可能会抛出IO等异常),全部由RxJavaPlugin接收，需要提前设置ErrorHandler
   * 详情：http://engineering.rallyhealth.com/mobile/rxjava/reactive/2017/03/15/migrating-to-rxjava-2.html#Error
   * Handling
   */
  private void setRxJavaErrorHandler() {
    RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
      @Override
      public void accept(Throwable throwable) throws Exception {
        LogUtil.d("setRxJavaErrorHandler:");
        throwable.printStackTrace();
      }
    });
  }

  public void onDefaultProcessCreate() {
    recordVersionCode();
    initX5Web();
    initUShare();
    //installGodEye();
  }

  /**
   * 记录app版本号
   * 可以通过这个记录后期做一些新旧版本切换埋点和额外操作
   */
  private void recordVersionCode() {
    int hisVerion = SPUtils.getInstance().getInt(SPConstants.APP_VERSION_CODE, -1);
    int curVersion = BuildConfig.appVerCode;
    if (hisVerion != curVersion) {
      SPUtils.getInstance().put(SPConstants.APP_VERSION_CODE, curVersion);
    }
  }

  public void onTerminate() {
    if (mActivityLifecycle != null) {
      mApplication.unregisterActivityLifecycleCallbacks(mActivityLifecycle);
    }
    this.mAppComponent = null;
    this.mActivityLifecycle = null;
    this.mApplication = null;

    for (Lifecycle lifecycle : mLifecycles) {
      lifecycle.onTerminate(mApplication);
    }

    //LeakCanary.uninstall();
    //GodEyeMonitor.shutDown();
  }

  //Umeng Share
  private void initUShare() {
    Config.DEBUG = BuildConfig.DEBUG;
    QueuedWork.isUseThreadPool = false;
    UMShareAPI.init(mApplication, BuildConfig.UMENG_APP_KEY);
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

  private void initX5Web() {
    //x5内核初始化接口
    QbSdk.initX5Environment(mApplication, new QbSdk.PreInitCallback() {

      @Override
      public void onViewInitFinished(boolean arg0) {
        // TODO Auto-generated method stub
        //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
        LogUtil.d("onViewInitFinished is " + arg0);
      }

      @Override
      public void onCoreInitFinished() {
        // TODO Auto-generated method stub
      }
    });
  }

  /**
   * 将app的全局配置信息封装进module(使用Dagger注入到需要配置信息的地方)
   * 需要在AndroidManifest中声明{@link ConfigModule}的实现类,和Glide的配置方式相似
   */
  private GlobeConfigModule getGlobeConfigModule(Application context, List<ConfigModule> modules) {

    GlobeConfigModule.Builder builder = GlobeConfigModule
        .builder()
        .baseurl(
            "https://api.github.com")//为了防止用户没有通过GlobeConfigModule配置baseurl,而导致报错,所以提前配置个默认baseurl
        .statusBarColor(R.color.colorPrimary)   //提供一个默认的状态栏颜色
        .statusBarAlpha(0);

    for (ConfigModule module : modules) {
      module.applyOptions(context, builder);
    }

    return builder.build();
  }

  public interface Lifecycle {

    void onCreate(Application application);

    void onTerminate(Application application);
  }

}