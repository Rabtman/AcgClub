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
import com.rabtman.common.utils.Utils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;
import io.realm.Realm;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Common Application的生命周期代理
 */

public class CommonApplicationLike implements IApplicationLike, App {

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
  private RefWatcher mRefWatcher;//leakCanary观察器

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
    installLeakCanary();
  }

  public void onDefaultProcessCreate() {
    initToastyConfig();
    initX5Web();
    initUShare();
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

    if (mRefWatcher != null) {
      this.mRefWatcher = null;
    }
  }

  //Umeng Share
  private void initUShare() {
    Config.DEBUG = BuildConfig.DEBUG;
    QueuedWork.isUseThreadPool = false;
    UMShareAPI.init(mApplication, BuildConfig.UMENG_APP_KEY);
  }

  private void initToastyConfig() {
    /*Toasty.Config.getInstance()
        .setErrorColor( @ColorInt int errorColor) // optional
    .setInfoColor( @ColorInt int infoColor) // optional
    .setSuccessColor( @ColorInt int successColor) // optional
    .setWarningColor( @ColorInt int warningColor) // optional
    .setTextColor( @ColorInt int textColor) // optional
    .tintIcon( boolean tintIcon) // optional (apply textColor also to the icon)
    .setToastTypeface(@NonNull Typeface typeface) // optional
        .apply(); */
  }

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
   * 安装leakCanary检测内存泄露
   */
  protected void installLeakCanary() {
    this.mRefWatcher = BuildConfig.DEBUG ? LeakCanary.install(mApplication) : RefWatcher.DISABLED;
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


  /**
   * 将AppComponent返回出去,供其它地方使用, AppComponent接口中声明的方法返回的实例,在getAppComponent()拿到对象后都可以直接使用
   */
  @Override
  public AppComponent getAppComponent() {
    return mAppComponent;
  }


  public interface Lifecycle {

    void onCreate(Application application);

    void onTerminate(Application application);
  }

}