package com.rabtman.acgnews.mvp.contract

import com.rabtman.acgnews.mvp.model.entity.SHPage
import com.rabtman.acgnews.mvp.model.entity.SHPostItem
import com.rabtman.common.base.mvp.IModel
import com.rabtman.common.base.mvp.IView
import io.reactivex.Flowable

/**
 * @author Rabtman
 */
interface ISHNewsContract {
    interface View : IView {
        fun showAcgNews(shPostItemList: List<SHPostItem>)
        fun showMoreAcgNews(shPostItemList: List<SHPostItem>?, canLoadMore: Boolean)
        fun onLoadMoreFail()
    }

    interface Model : IModel {
        fun getAcgNews(pageIndex: Int): Flowable<SHPage>
    }
}