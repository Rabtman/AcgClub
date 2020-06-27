package com.rabtman.acgnews.mvp.contract

import com.rabtman.acgnews.mvp.model.jsoup.ZeroFiveNews
import com.rabtman.acgnews.mvp.model.jsoup.ZeroFiveNewsPage
import com.rabtman.common.base.mvp.IModel
import com.rabtman.common.base.mvp.IView
import io.reactivex.Flowable

/**
 * @author Rabtman
 */
interface ZeroFiveNewsContract {
    interface View : IView {
        fun getNewsUrl(pageNo: Int): String
        fun showAcgNews(zeroFiveNewsList: List<ZeroFiveNews>)
        fun showMoreAcgNews(zeroFiveNewsList: List<ZeroFiveNews>?, canLoadMore: Boolean)
        fun onLoadMoreFail()
    }

    interface Model : IModel {
        fun getAcgNews(typeUrl: String?): Flowable<ZeroFiveNewsPage>
    }
}