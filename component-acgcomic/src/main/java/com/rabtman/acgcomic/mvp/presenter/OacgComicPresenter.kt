package com.rabtman.acgcomic.mvp.presenter

import com.rabtman.acgcomic.mvp.OacgComicContract
import com.rabtman.acgcomic.mvp.model.entity.OacgComicPage
import com.rabtman.common.base.CommonSubscriber
import com.rabtman.common.base.mvp.BasePresenter
import com.rabtman.common.di.scope.FragmentScope
import com.rabtman.common.utils.RxUtil
import javax.inject.Inject

/**
 * @author Rabtman
 */
@FragmentScope
class OacgComicPresenter
@Inject constructor(model: OacgComicContract.Model,
                    view: OacgComicContract.View) : BasePresenter<OacgComicContract.Model, OacgComicContract.View>(model, view) {
    /**
     * 当前菜单的选择结果
     * 取值范围：-1（全部），1~15
     */
    private var selectedType = -1
    /**
     * 当前页数
     */
    private var pageNo = 0

    /**
     * 记录选择的菜单项，并刷新数据
     */
    fun getComicInfosByMenuSelected(selectedType: Int) {
        this.selectedType = selectedType
        getComicInfos()
    }

    fun getComicInfos() {
        pageNo = 0
        addSubscribe(
                mModel.getComicInfos(selectedType, pageNo)
                        .compose(RxUtil.rxSchedulerHelper<OacgComicPage>())
                        .subscribeWith(object : CommonSubscriber<OacgComicPage>(mView) {

                            override fun onComplete() {
                                super.onComplete()
                                mView.hideLoading()
                            }

                            override fun onError(e: Throwable?) {
                                super.onError(e)
                                mView.showPageError()
                            }

                            override fun onNext(oacgComicPage: OacgComicPage) {
                                if (oacgComicPage.oacgComicItems == null || oacgComicPage.oacgComicItems.isEmpty()) {
                                    mView.showPageEmpty()
                                } else {
                                    mView.showComicInfos(oacgComicPage.oacgComicItems)
                                    mView.showPageContent()
                                }
                            }
                        })
        )
    }

    fun getMoreComicInfos() {
        addSubscribe(
                mModel.getComicInfos(selectedType, ++pageNo)
                        .compose(RxUtil.rxSchedulerHelper<OacgComicPage>())
                        .subscribeWith(object : CommonSubscriber<OacgComicPage>(mView) {
                            override fun onNext(oacgComicPage: OacgComicPage) {
                                mView.showMoreComicInfos(oacgComicPage.oacgComicItems, oacgComicPage.oacgComicItems?.isNotEmpty())
                            }

                            override fun onError(e: Throwable?) {
                                super.onError(e)
                                mView.onLoadMoreFail()
                                pageNo--
                            }
                        })
        )
    }

    fun searchComicInfos(keyword: String) {
        pageNo = 0
        addSubscribe(
                mModel.getSearchComicInfos(keyword)
                        .compose(RxUtil.rxSchedulerHelper<OacgComicPage>())
                        .subscribeWith(object : CommonSubscriber<OacgComicPage>(mView) {
                            override fun onStart() {
                                super.onStart()
                                mView.showLoading()
                            }

                            override fun onComplete() {
                                mView.hideLoading()
                            }

                            override fun onError(e: Throwable?) {
                                super.onError(e)
                                mView.hideLoading()
                            }

                            override fun onNext(oacgComicPage: OacgComicPage) {
                                mView.showSearchComicInfos(oacgComicPage.oacgComicItems)
                            }
                        })
        )
    }
}