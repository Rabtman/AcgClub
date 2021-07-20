package com.rabtman.acgcomic.mvp.presenter

import com.rabtman.acgcomic.mvp.QiMiaoComicContract
import com.rabtman.acgcomic.mvp.model.entity.jsoup.QiMiaoComicPage
import com.rabtman.business.base.CommonSubscriber
import com.rabtman.common.base.mvp.BasePresenter
import com.rabtman.common.di.scope.FragmentScope
import com.rabtman.common.utils.LogUtil
import com.rabtman.common.utils.RxUtil
import javax.inject.Inject

/**
 * @author Rabtman
 */
@FragmentScope
class QiMiaoComicPresenter
@Inject constructor(model: QiMiaoComicContract.Model,
                    view: QiMiaoComicContract.View) : BasePresenter<QiMiaoComicContract.Model, QiMiaoComicContract.View>(model, view) {
    /**
     * 当前菜单的选择结果或搜索关键字
     * 取值范围：空字符（全部），7~28
     */
    private var keyWord = ""
    /**
     * 当前页数
     */
    private var pageNo = 1

    private var isSearch = false;

    /**
     * 记录选择的菜单项，并刷新数据
     */
    fun getComicInfosByMenuSelected(selectedType: String) {
        if (!isSearch && selectedType == this.keyWord) return
        this.keyWord = selectedType
        getComicInfos()
    }

    fun getComicInfos() {
        pageNo = 1
        isSearch = false
        addSubscribe(
                mModel.getComicInfo(keyWord, pageNo, isSearch)
                        .compose(RxUtil.rxSchedulerHelper<QiMiaoComicPage>())
                        .subscribeWith(object : CommonSubscriber<QiMiaoComicPage>(mView) {

                            override fun onComplete() {
                                super.onComplete()
                                mView.hideLoading()
                            }

                            override fun onError(e: Throwable) {
                                super.onError(e)
                                mView.showPageError()
                            }

                            override fun onNext(qiMiaoComicPage: QiMiaoComicPage) {
                                LogUtil.d("getComicInfos")
                                if (qiMiaoComicPage.comicItems == null || qiMiaoComicPage.comicItems!!.isEmpty()) {
                                    mView.showPageEmpty()
                                } else {
                                    mView.showComicInfo(qiMiaoComicPage.comicItems)
                                    mView.showPageContent()
                                }
                            }
                        })
        )
    }

    fun getMoreComicInfos() {
        addSubscribe(
                mModel.getComicInfo(keyWord, ++pageNo, isSearch)
                        .compose(RxUtil.rxSchedulerHelper<QiMiaoComicPage>())
                        .subscribeWith(object : CommonSubscriber<QiMiaoComicPage>(mView) {
                            override fun onNext(comicPage: QiMiaoComicPage) {
                                mView.showMoreComicInfo(comicPage.comicItems, comicPage.hasMore())
                            }

                            override fun onError(e: Throwable) {
                                super.onError(e)
                                mView.onLoadMoreFail()
                                pageNo--
                            }
                        })
        )
    }

    fun searchComicInfos(key: String) {
        //重置菜单栏
        mView.resetComicMenu()
        pageNo = 1
        isSearch = true
        keyWord = key
        addSubscribe(
                mModel.getComicInfo(keyWord, pageNo, isSearch)
                        .compose(RxUtil.rxSchedulerHelper<QiMiaoComicPage>())
                        .subscribeWith(object : CommonSubscriber<QiMiaoComicPage>(mView) {
                            override fun onStart() {
                                super.onStart()
                                mView.showLoading()
                            }

                            override fun onComplete() {
                                mView.hideLoading()
                            }

                            override fun onError(e: Throwable) {
                                super.onError(e)
                                mView.hideLoading()
                            }

                            override fun onNext(comicPage: QiMiaoComicPage) {
                                if (comicPage.comicItems == null || comicPage.comicItems!!.isEmpty()) {
                                    mView.showMsg("找不到相关资源/(ㄒoㄒ)/~~")
                                }
                                mView.showSearchComicInfo(comicPage.comicItems, comicPage.hasMore())
                            }
                        })
        )
    }
}