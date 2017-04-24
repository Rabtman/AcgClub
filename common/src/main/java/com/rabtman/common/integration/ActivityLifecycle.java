package com.rabtman.common.integration;

import static com.rabtman.common.integration.AppManager.IS_NOT_ADD_ACTIVITY_LIST;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {

  private AppManager mAppManager;

  @Inject
  public ActivityLifecycle(AppManager appManager) {
    this.mAppManager = appManager;
  }

  @Override
  public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    //如果intent包含了此字段,并且为true说明不加入到list
    // 默认为false,如果不需要管理(比如不需要在退出所有activity(killAll)时，退出此activity就在intent加此字段为true)
    boolean isNotAdd = false;
    if (activity.getIntent() != null) {
      isNotAdd = activity.getIntent().getBooleanExtra(IS_NOT_ADD_ACTIVITY_LIST, false);
    }

    if (!isNotAdd) {
      mAppManager.addActivity(activity);
    }
  }

  @Override
  public void onActivityStarted(Activity activity) {

  }

  @Override
  public void onActivityResumed(Activity activity) {
    mAppManager.setCurrentActivity(activity);
  }

  @Override
  public void onActivityPaused(Activity activity) {
    if (mAppManager.getCurrentActivity() == activity) {
      mAppManager.setCurrentActivity(null);
    }
  }

  @Override
  public void onActivityStopped(Activity activity) {

  }

  @Override
  public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

  }

  @Override
  public void onActivityDestroyed(Activity activity) {
    mAppManager.removeActivity(activity);
  }
}
