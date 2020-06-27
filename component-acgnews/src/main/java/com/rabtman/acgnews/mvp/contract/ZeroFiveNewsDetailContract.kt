package com.rabtman.acgnews.mvp.contract

import com.rabtman.acgnews.mvp.model.jsoup.ZeroFiveNewsDetail
import com.rabtman.common.base.mvp.IModel
import com.rabtman.common.base.mvp.IView
import io.reactivex.Flowable

/**
 * @author Rabtman
 */
interface ZeroFiveNewsDetailContract {
    interface View : IView {
        fun showNewsDetail(zeroFiveNewsDetail: ZeroFiveNewsDetail)
        fun showShareView()
    }

    interface Model : IModel {
        fun getAcgNewsDetail(url: String?): Flowable<ZeroFiveNewsDetail>
    }
}