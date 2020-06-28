package com.rabtman.acgschedule.mvp.contract

import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleVideo
import com.rabtman.common.base.mvp.IModel
import com.rabtman.common.base.mvp.IView
import io.reactivex.Flowable

/**
 * @author Rabtman
 */
interface ScheduleVideoContract {
    interface View : IView {
        fun showScheduleVideo(videoUrl: String?, isVideo: Boolean)
    }

    interface Model : IModel {
        fun getScheduleVideo(url: String): Flowable<ScheduleVideo>
    }
}