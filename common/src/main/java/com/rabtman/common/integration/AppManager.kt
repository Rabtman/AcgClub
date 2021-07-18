package com.rabtman.common.integration;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import com.google.android.material.snackbar.Snackbar;
import com.rabtman.common.bus.AppManagerEvent;
import com.rabtman.common.bus.RxBus;
import com.rabtman.common.utils.LogUtil;
import com.rabtman.common.utils.RxUtil;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Predicate;
import io.reactivex.subscribers.DefaultSubscriber;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class AppManager {

  public static final String IS_NOT_ADD_ACTIVITY_LIST = "is_add_activity_list";//是否加入到activity的list，管理
  //管理所有activity
  public List<Activity> mActivityList;
  private Application mApplication;
  private Flowable<AppManagerEvent> mAppManagerEventFlowable;
  //当前在前台的activity
  private Activity mCurrentActivity;

  @Inject
  public AppManager(Application application) {
    this.mApplication = application;
    mAppManagerEventFlowable = RxBus.getDefault().toFlowable(AppManagerEvent.class);

    mAppManagerEventFlowable.compose(RxUtil.<AppManagerEvent>rxSchedulerHelper())
        .filter(new Predicate<AppManagerEvent>() {
          @Override
          public boolean test(@NonNull AppManagerEvent appManagerEvent) throws Exception {
            return appManagerEvent.type != 0;
          }
        })
        .subscribeWith(new DefaultSubscriber<AppManagerEvent>() {
          @Override
          public void onNext(AppManagerEvent appManagerEvent) {
            switch (appManagerEvent.type) {
              case AppManagerEvent.START_ACTIVITY:
                dispatchStart(appManagerEvent.msg);
                break;
              case AppManagerEvent.SHOW_SNACKBAR:
                if (appManagerEvent.msg instanceof String && TextUtils
                    .isEmpty((String) appManagerEvent.msg)) {
                  showSnackbar((String) appManagerEvent.msg);
                }
                break;
              case AppManagerEvent.KILL_ALL:
                killAll();
                break;
              case AppManagerEvent.APP_EXIT:
                AppExit();
                break;
            }
          }

          @Override
          public void onError(Throwable t) {

          }

          @Override
          public void onComplete() {

          }
        });
  }

  private void dispatchStart(Object msg) {
    if (msg instanceof Intent) {
      startActivity((Intent) msg);
    } else if (msg instanceof Class) {
      startActivity((Class) msg);
    }
    return;
  }


  /**
   * 使用snackbar显示内容
   */
  public void showSnackbar(String message) {
    if (getCurrentActivity() == null) {
      LogUtil.w("mCurrentActivity == null when showSnackbar(String,boolean)");
      return;
    }
    View view = getCurrentActivity().getWindow().getDecorView().findViewById(android.R.id.content);
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
  }


  /**
   * 让在前台的activity,打开下一个activity
   */
  public void startActivity(Intent intent) {
    if (getCurrentActivity() == null) {
      LogUtil.w("mCurrentActivity == null when startActivity(Intent)");
      //如果没有前台的activity就使用new_task模式启动activity
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      mApplication.startActivity(intent);
      return;
    }
    getCurrentActivity().startActivity(intent);
  }

  /**
   * 让在前台的activity,打开下一个activity
   */
  public void startActivity(Class activityClass) {
    startActivity(new Intent(mApplication, activityClass));
  }

  /**
   * 释放资源
   */
  public void release() {
        /*if(mAppManagerEventFlowable != null) {
            mAppManagerEventFlowable.unsubscribeOn();
        }*/
    mActivityList.clear();
    mActivityList = null;
    mCurrentActivity = null;
    mApplication = null;
  }

  /**
   * 获得当前在前台的activity
   */
  public Activity getCurrentActivity() {
    return mCurrentActivity;
  }

  /**
   * 将在前台的activity保存
   */
  public void setCurrentActivity(Activity currentActivity) {
    this.mCurrentActivity = currentActivity;
  }

  /**
   * 返回一个存储所有未销毁的activity的集合
   */
  public List<Activity> getActivityList() {
    if (mActivityList == null) {
      mActivityList = new LinkedList<>();
    }
    return mActivityList;
  }


  /**
   * 添加Activity到集合
   */
  public void addActivity(Activity activity) {
    if (mActivityList == null) {
      mActivityList = new LinkedList<>();
    }
    synchronized (AppManager.class) {
      if (!mActivityList.contains(activity)) {
        mActivityList.add(activity);
      }
    }
  }

  /**
   * 删除集合里的指定activity
   */
  public void removeActivity(Activity activity) {
    if (mActivityList == null) {
      LogUtil.w("mActivityList == null when removeActivity(Activity)");
      return;
    }
    synchronized (AppManager.class) {
      if (mActivityList.contains(activity)) {
        mActivityList.remove(activity);
      }
    }
  }

  /**
   * 删除集合里的指定位置的activity
   */
  public Activity removeActivity(int location) {
    if (mActivityList == null) {
      LogUtil.w("mActivityList == null when removeActivity(int)");
      return null;
    }
    synchronized (AppManager.class) {
      if (location > 0 && location < mActivityList.size()) {
        return mActivityList.remove(location);
      }
    }
    return null;
  }

  /**
   * 关闭指定activity
   */
  public void killActivity(Class<?> activityClass) {
    if (mActivityList == null) {
      LogUtil.w("mActivityList == null when killActivity");
      return;
    }
    for (Activity activity : mActivityList) {
      if (activity.getClass().equals(activityClass)) {
        activity.finish();
      }
    }
  }


  /**
   * 指定的activity实例是否存活
   */
  public boolean activityInstanceIsLive(Activity activity) {
    if (mActivityList == null) {
      LogUtil.w("mActivityList == null when activityInstanceIsLive");
      return false;
    }
    return mActivityList.contains(activity);
  }


  /**
   * 指定的activity class是否存活(一个activity可能有多个实例)
   */
  public boolean activityClassIsLive(Class<?> activityClass) {
    if (mActivityList == null) {
      LogUtil.w("mActivityList == null when activityClassIsLive");
      return false;
    }
    for (Activity activity : mActivityList) {
      if (activity.getClass().equals(activityClass)) {
        return true;
      }
    }

    return false;
  }


  /**
   * 关闭所有activity
   */
  public void killAll() {
    Iterator<Activity> iterator = getActivityList().iterator();
    while (iterator.hasNext()) {
      iterator.next().finish();
      iterator.remove();
    }

  }


  /**
   * 退出应用程序
   */
  public void AppExit() {
    try {
      killAll();
      if (mActivityList != null) {
        mActivityList = null;
      }
      ActivityManager activityMgr =
          (ActivityManager) mApplication.getSystemService(Context.ACTIVITY_SERVICE);
      activityMgr.killBackgroundProcesses(mApplication.getPackageName());
      System.exit(0);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
