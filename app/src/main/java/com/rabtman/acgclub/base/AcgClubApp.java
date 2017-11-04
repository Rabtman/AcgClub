package com.rabtman.acgclub.base;

import static com.tencent.bugly.beta.tinker.TinkerManager.getApplication;

import com.rabtman.common.base.App;
import com.rabtman.common.base.delegate.AppDelegate;
import com.rabtman.common.di.component.AppComponent;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * @author Rabtman
 */

public class AcgClubApp extends TinkerApplication implements App {

  private AppDelegate mAppDelegate;

  public AcgClubApp() {
    super(ShareConstants.TINKER_ENABLE_ALL, "com.rabtman.acgclub.base.AppLike",
        "com.tencent.tinker.loader.TinkerLoader", false);
  }

  @Override
  public void onCreate() {
    super.onCreate();
    this.mAppDelegate = new AppDelegate(getApplication());
    this.mAppDelegate.onCreate();
  }

  @Override
  public void onTerminate() {
    this.mAppDelegate.onTerminate();
    super.onTerminate();
  }

  @Override
  public AppComponent getAppComponent() {
    return mAppDelegate.getAppComponent();
  }
}
