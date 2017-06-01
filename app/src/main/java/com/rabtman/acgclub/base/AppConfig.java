package com.rabtman.acgclub.base;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Bundle;
import com.hss01248.dialog.MyActyManager;
import com.rabtman.acgclub.api.AcgService;
import com.rabtman.common.base.delegate.AppDelegate.Lifecycle;
import com.rabtman.common.di.module.GlobeConfigModule.Builder;
import com.rabtman.common.integration.ConfigModule;
import com.rabtman.common.integration.IRepositoryManager;
import java.util.List;

/**
 * @author Rabtman
 */

public class AppConfig implements ConfigModule {

  @Override
  public void applyOptions(Context context, Builder builder) {
    builder.baseurl(AcgService.BASE_URL);
  }

  @Override
  public void registerComponents(Context context, IRepositoryManager repositoryManager) {
    repositoryManager.injectRetrofitService(AcgService.class);
  }

  @Override
  public void injectAppLifecycle(Context context, List<Lifecycle> lifecycles) {

  }

  @Override
  public void injectActivityLifecycle(Context context,
      List<ActivityLifecycleCallbacks> lifecycles) {
    lifecycles.add(new ActivityLifecycleCallbacks() {
      @Override
      public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
      }

      @Override
      public void onActivityStarted(Activity activity) {

      }

      @Override
      public void onActivityResumed(Activity activity) {
        MyActyManager.getInstance().setCurrentActivity(activity);
      }

      @Override
      public void onActivityPaused(Activity activity) {

      }

      @Override
      public void onActivityStopped(Activity activity) {

      }

      @Override
      public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

      }

      @Override
      public void onActivityDestroyed(Activity activity) {

      }
    });
  }
}
