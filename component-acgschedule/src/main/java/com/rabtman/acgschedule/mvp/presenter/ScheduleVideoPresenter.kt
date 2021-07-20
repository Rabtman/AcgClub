package com.rabtman.acgschedule.mvp.presenter

import android.text.TextUtils
import com.rabtman.acgschedule.base.constant.HtmlConstant
import com.rabtman.acgschedule.mvp.contract.ScheduleVideoContract
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleVideo
import com.rabtman.business.base.CommonSubscriber
import com.rabtman.common.base.mvp.BasePresenter
import com.rabtman.common.di.scope.ActivityScope
import com.rabtman.common.utils.LogUtil
import com.rabtman.common.utils.RxUtil
import javax.inject.Inject

/**
 * @author Rabtman
 */
@ActivityScope
class ScheduleVideoPresenter @Inject constructor(model: ScheduleVideoContract.Model,
                                                 rootView: ScheduleVideoContract.View) : BasePresenter<ScheduleVideoContract.Model, ScheduleVideoContract.View>(model, rootView) {
    fun getScheduleVideo(videoUrl: String?) {
        if (videoUrl.isNullOrBlank()) {
            mView.showError("视频链接不见了/(ㄒoㄒ)/~~")
            return
        }

        val finalVideoUrl = if (!videoUrl.contains("http")) {
            HtmlConstant.SCHEDULE_M_URL + videoUrl
        } else {
            videoUrl
        }
        addSubscribe(
                mModel.getScheduleVideo(finalVideoUrl)
                        .compose(RxUtil.rxSchedulerHelper())
                        .subscribeWith(object : CommonSubscriber<ScheduleVideo>(mView) {
                            override fun onComplete() {
                                mView.hideLoading()
                            }

                            override fun onNext(scheduleVideo: ScheduleVideo) {
                                LogUtil.d("ScheduleVideo:$scheduleVideo")
                                if (!TextUtils.isEmpty(scheduleVideo.videoUrl)) {
                                    mView.showScheduleVideo(scheduleVideo.videoUrl, true)
                                } else {
                                    mView.showScheduleVideo(finalVideoUrl, false)
                                }
                            }
                        })
        )
    }
}