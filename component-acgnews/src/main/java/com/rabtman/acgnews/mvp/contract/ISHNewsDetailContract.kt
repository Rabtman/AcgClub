package com.rabtman.acgnews.mvp.contract

import com.rabtman.acgnews.mvp.model.entity.SHPostDetail
import com.rabtman.common.base.mvp.IModel
import com.rabtman.common.base.mvp.IView
import io.reactivex.Flowable

/**
 * @author Rabtman
 */
interface ISHNewsDetailContract {
    interface View : IView {
        fun showNewsDetail(zeroFiveNewsDetail: SHPostDetail)
        fun showShareView()
    }

    interface Model : IModel {
        fun getAcgNewsDetail(postId: Int): Flowable<SHPostDetail>
    }
}