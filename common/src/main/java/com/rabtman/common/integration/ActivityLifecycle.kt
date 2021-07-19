package com.rabtman.common.integration

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import com.hss01248.dialog.ActivityStackManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActivityLifecycle @Inject constructor(private val mAppManager: AppManager) :
    ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle) {
        //如果intent包含了此字段,并且为true说明不加入到list
        // 默认为false,如果不需要管理(比如不需要在退出所有activity(killAll)时，退出此activity就在intent加此字段为true)
        val isNotAdd =
            activity.intent?.getBooleanExtra(AppManager.IS_NOT_ADD_ACTIVITY_LIST, false) ?: false

        if (!isNotAdd) {
            mAppManager.addActivity(activity)
        }
        ActivityStackManager.getInstance().addActivity(activity)
    }

    override fun onActivityStarted(activity: Activity) {}
    override fun onActivityResumed(activity: Activity) {
        mAppManager.currentActivity = activity
    }

    override fun onActivityPaused(activity: Activity) {
        if (mAppManager.currentActivity === activity) {
            mAppManager.currentActivity = null
        }
    }

    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {
        mAppManager.removeActivity(activity)
        ActivityStackManager.getInstance().removeActivity(activity)
    }
}