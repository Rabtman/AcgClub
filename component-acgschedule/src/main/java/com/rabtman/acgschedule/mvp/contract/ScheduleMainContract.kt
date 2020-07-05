package com.rabtman.acgschedule.mvp.contract

import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleInfo
import com.rabtman.common.base.mvp.IModel
import com.rabtman.common.base.mvp.IView
import io.reactivex.Flowable

/**
 * @author Rabtman
 */
interface ScheduleMainContract {
    interface View : IView {
        fun showScheduleInfo(scheduleInfo: ScheduleInfo)
        fun start2ScheduleVideo(videoUrl: String)
    }

    interface Model : IModel {
        fun getScheduleInfo(): Flowable<ScheduleInfo>
    }
}