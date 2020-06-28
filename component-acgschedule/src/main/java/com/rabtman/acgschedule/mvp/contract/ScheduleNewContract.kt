package com.rabtman.acgschedule.mvp.contract

import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleNew
import com.rabtman.common.base.mvp.IModel
import com.rabtman.common.base.mvp.IView
import io.reactivex.Flowable

/**
 * @author Rabtman
 */
interface ScheduleNewContract {
    interface View : IView {
        fun showScheduleNew(scheduleNew: ScheduleNew?)
    }

    interface Model : IModel {
        fun getScheduleNew(url: String): Flowable<ScheduleNew>
    }
}