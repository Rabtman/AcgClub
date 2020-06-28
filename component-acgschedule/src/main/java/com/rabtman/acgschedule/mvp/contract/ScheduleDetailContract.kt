package com.rabtman.acgschedule.mvp.contract

import com.rabtman.acgschedule.mvp.model.entity.ScheduleCache
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleDetail
import com.rabtman.common.base.mvp.IModel
import com.rabtman.common.base.mvp.IView
import io.reactivex.Flowable

/**
 * @author Rabtman
 */
interface ScheduleDetailContract {
    interface View : IView {
        fun showScheduleDetail(scheduleDetail: ScheduleDetail?)
        fun start2ScheduleVideo(videoUrl: String?)
        fun showScheduleCacheStatus(scheduleCache: ScheduleCache?)
    }

    interface Model : IModel {
        fun getScheduleDetail(url: String): Flowable<ScheduleDetail>
        fun getScheduleCacheByUrl(scheduleUrl: String): Flowable<ScheduleCache>
        fun collectSchedule(item: ScheduleCache, isAdd: Boolean): Flowable<ScheduleCache>
        fun updateScheduleWatchRecord(item: ScheduleCache, lastWatchPos: Int): Flowable<ScheduleCache>
    }
}