package com.rabtman.common.integration

import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.rabtman.common.bus.AppManagerEvent
import com.rabtman.common.bus.RxBus.Companion.default
import com.rabtman.common.utils.LogUtil
import com.rabtman.common.utils.RxUtil
import io.reactivex.Flowable
import io.reactivex.subscribers.DefaultSubscriber
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@SuppressLint("CheckResult")
@Singleton
class AppManager @Inject constructor(private var mApplication: Application?) {
    //管理所有activity
    var mActivityList = LinkedList<Activity>()

    private val mAppManagerEventFlowable: Flowable<AppManagerEvent> =
        default.toFlowable(AppManagerEvent::class.java)

    //当前在前台的activity
    var currentActivity: Activity? = null

    companion object {
        const val IS_NOT_ADD_ACTIVITY_LIST = "is_add_activity_list" //是否加入到activity的list，管理
    }

    init {
        mAppManagerEventFlowable.compose(RxUtil.rxSchedulerHelper())
            .filter { appManagerEvent -> appManagerEvent.type != 0 }
            .subscribeWith(object : DefaultSubscriber<AppManagerEvent>() {
                override fun onNext(appManagerEvent: AppManagerEvent) {
                    when (appManagerEvent.type) {
                        AppManagerEvent.START_ACTIVITY -> dispatchStart(appManagerEvent.msg)
                        AppManagerEvent.SHOW_SNACK_BAR -> {
                            (appManagerEvent.msg as? String)?.takeIf { it.isNotBlank() }
                                ?.let { msg ->
                                    showSnackbar(msg)
                                }
                        }
                        AppManagerEvent.KILL_ALL -> killAll()
                        AppManagerEvent.APP_EXIT -> AppExit()
                    }
                }

                override fun onError(t: Throwable) {}
                override fun onComplete() {}
            })
    }

    private fun dispatchStart(msg: Any) {
        if (msg is Intent) {
            startActivity(msg)
        } else if (msg is Class<*>) {
            startActivity(msg)
        }
        return
    }

    /**
     * 使用snackbar显示内容
     */
    fun showSnackbar(message: String) {
        currentActivity?.apply {
            val view = window.decorView.findViewById<View>(R.id.content)
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
        }
    }

    /**
     * 让在前台的activity,打开下一个activity
     */
    fun startActivity(intent: Intent) {
        currentActivity?.startActivity(intent) ?: run {
            LogUtil.w("mCurrentActivity == null when startActivity(Intent)")
            //如果没有前台的activity就使用new_task模式启动activity
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            mApplication?.startActivity(intent)
        }
    }

    /**
     * 让在前台的activity,打开下一个activity
     */
    fun startActivity(activityClass: Class<*>?) {
        startActivity(Intent(mApplication, activityClass))
    }

    /**
     * 释放资源
     */
    fun release() {
        /*if(mAppManagerEventFlowable != null) {
            mAppManagerEventFlowable.unsubscribeOn();
        }*/
        mActivityList.clear()
        currentActivity = null
        mApplication = null
    }

    /**
     * 添加Activity到集合
     */
    fun addActivity(activity: Activity) {
        synchronized(AppManager::class.java) {
            if (!mActivityList.contains(activity)) {
                mActivityList.add(activity)
            }
        }
    }

    /**
     * 删除集合里的指定activity
     */
    fun removeActivity(activity: Activity) {
        synchronized(AppManager::class.java) {
            if (mActivityList.contains(activity)) {
                mActivityList.remove(activity)
            }
        }
    }

    /**
     * 删除集合里的指定位置的activity
     */
    fun removeActivity(location: Int): Activity? {
        synchronized(AppManager::class.java) {
            if (location > 0 && location < mActivityList.size) {
                return mActivityList.removeAt(location)
            }
        }
        return null
    }

    /**
     * 关闭指定activity
     */
    fun killActivity(activityClass: Class<*>) {
        for (activity in mActivityList) {
            if (activity.javaClass == activityClass) {
                activity.finish()
            }
        }
    }

    /**
     * 指定的activity实例是否存活
     */
    fun activityInstanceIsLive(activity: Activity): Boolean {
        return mActivityList.contains(activity)
    }

    /**
     * 指定的activity class是否存活(一个activity可能有多个实例)
     */
    fun activityClassIsLive(activityClass: Class<*>): Boolean {
        for (activity in mActivityList) {
            if (activity.javaClass == activityClass) {
                return true
            }
        }
        return false
    }

    /**
     * 关闭所有activity
     */
    fun killAll() {
        val iterator = mActivityList.iterator()
        while (iterator.hasNext()) {
            iterator.next().finish()
            iterator.remove()
        }
    }

    /**
     * 退出应用程序
     */
    fun AppExit() {
        try {
            killAll()
            mActivityList.clear()
            (mApplication?.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager)?.let { activityMgr ->
                activityMgr.killBackgroundProcesses(mApplication?.packageName)
            }
            System.exit(0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}