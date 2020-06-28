package com.rabtman.acgschedule.mvp.contract

import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleOtherPage
import com.rabtman.common.base.mvp.IModel
import com.rabtman.common.base.mvp.IView
import io.reactivex.Flowable

/**
 * @author Rabtman
 */
interface ScheduleOtherContract {
    interface View : IView {
        fun showScheduleOther(scheduleOtherPage: ScheduleOtherPage?)
        fun showMoreScheduleOther(scheduleOtherPage: ScheduleOtherPage?, canLoadMore: Boolean)
        fun onLoadMoreFail()
    }

    interface Model : IModel {
        fun getScheduleOtherPage(url: String): Flowable<ScheduleOtherPage>
    }
}