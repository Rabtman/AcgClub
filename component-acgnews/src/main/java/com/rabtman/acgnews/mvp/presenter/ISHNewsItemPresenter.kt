package com.rabtman.acgnews.mvp.presenter

import com.rabtman.acgnews.mvp.contract.ISHNewsContract
import com.rabtman.acgnews.mvp.model.entity.SHPage
import com.rabtman.common.base.CommonSubscriber
import com.rabtman.common.base.mvp.BasePresenter
import com.rabtman.common.di.scope.FragmentScope
import com.rabtman.common.utils.RxUtil
import javax.inject.Inject

/**
 * @author Rabtman
 */
@FragmentScope
class ISHNewsItemPresenter @Inject constructor(model: ISHNewsContract.Model,
                                               rootView: ISHNewsContract.View) : BasePresenter<ISHNewsContract.Model, ISHNewsContract.View>(model, rootView) {
    //当前页面位置
    private var pageNo = 1
    fun getAcgNewsList() {
        pageNo = 1
        addSubscribe(
                mModel.getAcgNews(pageNo)
                        .compose(RxUtil.rxSchedulerHelper())
                        .subscribeWith(object : CommonSubscriber<SHPage>(mView) {
                            override fun onError(e: Throwable) {
                                super.onError(e)
                                mView.showPageError()
                            }

                            override fun onComplete() {
                                mView!!.hideLoading()
                            }

                            override fun onNext(page: SHPage) {
                                page.postItems?.takeIf { it.isNotEmpty() }?.let {
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
                mModel.getAcgNews(++pageNo)
                        .compose(RxUtil.rxSchedulerHelper())
                        .subscribeWith(object : CommonSubscriber<SHPage>(mView) {
                            override fun onNext(page: SHPage) {
                                mView.showMoreAcgNews(page.postItems,
                                        page.currentPage < page.totalPages)
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