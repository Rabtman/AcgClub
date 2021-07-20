package com.rabtman.acgnews.mvp.presenter

import com.rabtman.acgnews.base.constant.HtmlConstant
import com.rabtman.acgnews.mvp.contract.ZeroFiveNewsContract
import com.rabtman.acgnews.mvp.model.jsoup.ZeroFiveNewsPage
import com.rabtman.business.base.CommonSubscriber
import com.rabtman.common.base.mvp.BasePresenter
import com.rabtman.common.di.scope.FragmentScope
import com.rabtman.common.utils.RxUtil
import javax.inject.Inject

/**
 * @author Rabtman
 */
@FragmentScope
class ZeroFiveNewsItemPresenter @Inject constructor(model: ZeroFiveNewsContract.Model,
                                                    rootView: ZeroFiveNewsContract.View) : BasePresenter<ZeroFiveNewsContract.Model, ZeroFiveNewsContract.View>(model, rootView) {
    //当前页面位置
    private var pageNo = 1
    fun getAcgNewsList() {
        pageNo = 1
        addSubscribe(
                mModel.getAcgNews(HtmlConstant.ZERO_FIVE_URL + mView.getNewsUrl(pageNo))
                        .compose(RxUtil.rxSchedulerHelper())
                        .subscribeWith(object : CommonSubscriber<ZeroFiveNewsPage>(mView) {
                            override fun onError(e: Throwable) {
                                super.onError(e)
                                mView.showPageError()
                            }

                            override fun onComplete() {
                                mView.hideLoading()
                            }

                            override fun onNext(zeroFiveNewsPage: ZeroFiveNewsPage) {
                                zeroFiveNewsPage.zeroFiveNewsList?.takeIf { it.isNotEmpty() }?.let {
                                    mView.showAcgNews(it)
                                    mView.showPageContent()
                                } ?: run {
                                    mView.showPageEmpty()
                                }
                            }
                        })
        )
    }

    fun getMoreAcgNewsList() {
        addSubscribe(
                mModel.getAcgNews(HtmlConstant.ZERO_FIVE_URL + mView.getNewsUrl(++pageNo))
                        .compose(RxUtil.rxSchedulerHelper())
                        .subscribeWith(object : CommonSubscriber<ZeroFiveNewsPage>(mView) {
                            override fun onNext(zeroFiveNewsPage: ZeroFiveNewsPage) {
                                mView.showMoreAcgNews(zeroFiveNewsPage.zeroFiveNewsList,
                                        pageNo < zeroFiveNewsPage.getPageCount())
                            }

                            override fun onError(e: Throwable) {
                                super.onError(e)
                                mView.onLoadMoreFail()
                                pageNo--
                            }
                        })
        )
    }
}