package com.rabtman.acgpicture.mvp.presenter


import com.rabtman.acgpicture.base.constant.HtmlConstant
import com.rabtman.acgpicture.mvp.APictureContract
import com.rabtman.acgpicture.mvp.model.entity.APicturePage
import com.rabtman.common.base.CommonSubscriber
import com.rabtman.common.base.mvp.BasePresenter
import com.rabtman.common.di.scope.FragmentScope
import com.rabtman.common.utils.RxUtil
import javax.inject.Inject

/**
 * @author Rabtman
 */
@FragmentScope
class APicturePresenter @Inject
constructor(model: APictureContract.Model, rootView: APictureContract.View) : BasePresenter<APictureContract.Model, APictureContract.View>(model, rootView) {

    //当前页面位置
    private var pageNo = 1

    fun getAcgPicList() {
        pageNo = 1
        addSubscribe(
                mModel.getAPictures(HtmlConstant.APICTURE_ANIME_URL + pageNo)
                        .compose(RxUtil.rxSchedulerHelper())
                        .subscribeWith(object : CommonSubscriber<APicturePage>(mView) {
                            override fun onStart() {
                                super.onStart()
                                mView.showLoading()
                            }

                            override fun onComplete() {
                                mView.hideLoading()
                            }

                            override fun onNext(aPicturePagePage: APicturePage) {
                                if (aPicturePagePage.items == null || aPicturePagePage.items!!.isEmpty()) {
                                    mView.showPageEmpty()
                                } else {
                                    mView.showPictures(aPicturePagePage.items!!)
                                    mView.showPageContent()
                                }
                            }
                        })
        )
    }

    fun getMoreAcgPicList() {
        pageNo++
        addSubscribe(
                mModel.getAPictures(HtmlConstant.APICTURE_ANIME_URL + pageNo)
                        .compose(RxUtil.rxSchedulerHelper())
                        .subscribeWith(object : CommonSubscriber<APicturePage>(mView) {
                            override fun onNext(aPicturePagePage: APicturePage) {
                                if (aPicturePagePage.items == null || aPicturePagePage.items!!.isEmpty()) {
                                    mView.showPageEmpty()
                                } else {
                                    mView.showMorePictures(aPicturePagePage.items!!,
                                            pageNo < aPicturePagePage.getPageCount())
                                    mView.showPageContent()
                                }
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
