package com.rabtman.acgschedule.mvp.presenter

import android.Manifest.permission
import android.text.TextUtils
import com.rabtman.acgschedule.R
import com.rabtman.acgschedule.mvp.contract.ScheduleDetailContract
import com.rabtman.acgschedule.mvp.model.entity.ScheduleCache
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleDetail
import com.rabtman.common.base.CommonSubscriber
import com.rabtman.common.base.mvp.BasePresenter
import com.rabtman.common.di.scope.ActivityScope
import com.rabtman.common.utils.RxUtil
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.subscribers.ResourceSubscriber
import javax.inject.Inject

/**
 * @author Rabtman
 */
@ActivityScope
class ScheduleDetailPresenter @Inject constructor(model: ScheduleDetailContract.Model,
                                                  rootView: ScheduleDetailContract.View) : BasePresenter<ScheduleDetailContract.Model, ScheduleDetailContract.View>(model, rootView) {
    /**
     * 当前番剧链接
     */
    private var currentScheduleUrl: String? = null

    /**
     * 当前番剧详情
     */
    private var currentScheduleDetail: ScheduleDetail? = null

    /**
     * 本地缓存加载标识
     */
    private var loadCache = false

    /**
     * 当前番剧的本地缓存
     */
    private val curScheduleCache = ScheduleCache()
    fun setCurrentScheduleUrl(url: String?) {
        currentScheduleUrl = url
    }

    fun getScheduleDetail() {
        currentScheduleUrl?.takeIf { it.isNotBlank() }?.let {
            addSubscribe(
                    mModel.getScheduleDetail(it)
                            .compose(RxUtil.rxSchedulerHelper())
                            .subscribeWith(object : CommonSubscriber<ScheduleDetail>(mView) {
                                override fun onComplete() {
                                    mView.hideLoading()
                                }

                                override fun onError(e: Throwable) {
                                    super.onError(e)
                                    mView.showPageError()
                                }

                                override fun onNext(scheduleDetail: ScheduleDetail) {
                                    if (TextUtils.isEmpty(scheduleDetail.scheduleTitle)) {
                                        mView.showError(R.string.msg_error_url_null)
                                    }
                                    curScheduleCache.scheduleUrl = currentScheduleUrl
                                    curScheduleCache.name = scheduleDetail.scheduleTitle
                                    curScheduleCache.imgUrl = scheduleDetail.getImgUrl()
                                    currentScheduleDetail = scheduleDetail
                                    mView.showScheduleDetail(scheduleDetail)
                                    mView.showPageContent()
                                    if (loadCache) {
                                        mView.showScheduleCacheStatus(curScheduleCache)
                                    }
                                }
                            })
            )
        } ?: run {
            mView.showError(R.string.msg_error_url_null)
        }
    }

    /**
     * 查询该番剧的缓存信息
     *
     * @param isManualClick 是否主动点击
     */
    fun getCurrentScheduleCache(rxPermissions: RxPermissions,
                                isManualClick: Boolean) {
        //如果播放记录还没加载成功，则先加载
        if (!TextUtils.isEmpty(curScheduleCache.scheduleUrl) && isManualClick) {
            checkPermission2ScheduleVideo(rxPermissions,
                    getNextScheduleUrl(currentScheduleDetail, curScheduleCache.lastWatchPos))
        } else {
            currentScheduleUrl?.takeIf { it.isNotBlank() }?.let {
                addSubscribe(
                        mModel.getScheduleCacheByUrl(it)
                                .compose(RxUtil.rxSchedulerHelper())
                                .subscribeWith(object : ResourceSubscriber<ScheduleCache>() {
                                    override fun onNext(scheduleCache: ScheduleCache) {
                                        curScheduleCache.isCollect = scheduleCache.isCollect
                                        curScheduleCache.lastWatchPos = scheduleCache.lastWatchPos
                                    }

                                    override fun onError(t: Throwable) {
                                        t.printStackTrace()
                                        mView.showError(R.string.msg_error_unknown)
                                    }

                                    override fun onComplete() {
                                        loadCache = true
                                        if (!TextUtils.isEmpty(curScheduleCache.scheduleUrl)) {
                                            mView.showScheduleCacheStatus(curScheduleCache)
                                        }
                                        //手动点击，则在加载完记录后跳转到视频播放
                                        if (isManualClick) {
                                            checkPermission2ScheduleVideo(
                                                    rxPermissions,
                                                    getNextScheduleUrl(currentScheduleDetail,
                                                            curScheduleCache.lastWatchPos))
                                        }
                                    }
                                })
                )
            } ?: run {
                mView.showError(R.string.msg_error_url_null)
            }
        }
    }

    /**
     * 根据历史记录获取当前应播放番剧
     */
    private fun getNextScheduleUrl(scheduleDetail: ScheduleDetail?, lastPos: Int): String? {
        if (!validScheduleDetail(scheduleDetail)) {
            return ""
        }
        val nextPos = getNextPos(scheduleDetail, lastPos)

        //获取地址的同时，更新历史记录
        updateScheduleReadRecord(nextPos)
        return scheduleDetail!!.scheduleEpisodes!![nextPos].link
    }

    /**
     * 获取下一个观看位置
     */
    private fun getNextPos(scheduleDetail: ScheduleDetail?, lastPos: Int): Int {
        if (!validScheduleDetail(scheduleDetail)) {
            return -1
        }
        val scheduleEpisodes = scheduleDetail!!.scheduleEpisodes
        return if (lastPos + 1 >= scheduleEpisodes!!.size - 2) {
            scheduleEpisodes.size - 2
        } else {
            lastPos + 1
        }
    }

    /**
     * 验证番剧信息有效性
     */
    private fun validScheduleDetail(scheduleDetail: ScheduleDetail?): Boolean {
        if (scheduleDetail == null) {
            return false
        }
        val scheduleEpisodes = scheduleDetail.scheduleEpisodes
        return scheduleEpisodes != null && scheduleEpisodes.size > 0
    }

    /**
     * 番剧收藏、取消
     */
    fun collectOrCancelSchedule(isCollected: Boolean) {
        addSubscribe(
                mModel.collectSchedule(curScheduleCache, !isCollected)
                        .compose(RxUtil.rxSchedulerHelper())
                        .subscribe({
                            curScheduleCache.isCollect = !isCollected
                            mView.showScheduleCacheStatus(curScheduleCache)
                            if (!isCollected) {
                                mView.showMsg(R.string.msg_success_collect_add)
                            } else {
                                mView.showMsg(R.string.msg_success_collect_cancel)
                            }
                        }) { throwable ->
                            throwable.printStackTrace()
                            if (!isCollected) {
                                mView.showError(R.string.msg_error_collect_add)
                            } else {
                                mView.showError(R.string.msg_error_collect_cancel)
                            }
                        }
        )
    }

    /**
     * 记录上一次番剧观看位置
     *
     * @param lastWatchPos 上一次观看位置
     */
    fun updateScheduleReadRecord(lastWatchPos: Int) {
        addSubscribe(
                mModel.updateScheduleWatchRecord(curScheduleCache, lastWatchPos)
                        .compose(RxUtil.rxSchedulerHelper())
                        .subscribe({
                            curScheduleCache.lastWatchPos = lastWatchPos
                            mView.showScheduleCacheStatus(curScheduleCache)
                        }) { throwable -> throwable.printStackTrace() }
        )
    }

    /**
     * 视频观看权限申请
     */
    fun checkPermission2ScheduleVideo(rxPermissions: RxPermissions, videoUrl: String?) {
        if (TextUtils.isEmpty(videoUrl)) {
            mView.showError(R.string.msg_error_url_null)
            return
        }
        rxPermissions.request(permission.WRITE_EXTERNAL_STORAGE,
                permission.READ_PHONE_STATE,
                permission.ACCESS_NETWORK_STATE,
                permission.ACCESS_WIFI_STATE)
                .subscribe { aBoolean ->
                    if (aBoolean) {
                        mView.start2ScheduleVideo(videoUrl)
                    } else {
                        mView.showError(R.string.msg_error_check_permission)
                    }
                }
    }
}