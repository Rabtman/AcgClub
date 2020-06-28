package com.rabtman.acgschedule.mvp.contract

import com.rabtman.acgschedule.mvp.model.jsoup.DilidiliInfo
import com.rabtman.common.base.mvp.IModel
import com.rabtman.common.base.mvp.IView
import io.reactivex.Flowable

/**
 * @author Rabtman
 */
interface ScheduleMainContract {
    interface View : IView {
        fun showDilidiliInfo(dilidiliInfo: DilidiliInfo)
        fun start2ScheduleVideo(videoUrl: String)
    }

    interface Model : IModel {
        fun getDilidiliInfo(): Flowable<DilidiliInfo>
    }
}