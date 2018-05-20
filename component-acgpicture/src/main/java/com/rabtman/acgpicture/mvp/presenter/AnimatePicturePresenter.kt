package com.rabtman.acgpicture.mvp.presenter

import com.rabtman.acgpicture.mvp.AnimatePictureContract
import com.rabtman.acgpicture.mvp.model.entity.AnimatePicturePage
import com.rabtman.common.base.CommonSubscriber
import com.rabtman.common.base.mvp.BasePresenter
import com.rabtman.common.di.scope.FragmentScope
import com.rabtman.common.utils.RxUtil
import javax.inject.Inject

/**
 * @author Rabtman
 */
@FragmentScope
class AnimatePicturePresenter
@Inject constructor(model: AnimatePictureContract.Model,
                    view: AnimatePictureContract.View) : BasePresenter<AnimatePictureContract.Model, AnimatePictureContract.View>(model, view) {
    /**
     * 当前页数
     */
    private var pageIndex = 0

    fun getAnimatePictures() {
        pageIndex = 0
        addSubscribe(
                mModel.getAnimatePictures(pageIndex)
                        .compose(RxUtil.rxSchedulerHelper<AnimatePicturePage>())
                        .subscribeWith(object : CommonSubscriber<AnimatePicturePage>(mView) {

                            override fun onComplete() {
                                super.onComplete()
                                mView.hideLoading()
                            }

                            override fun onError(e: Throwable?) {
                                super.onError(e)
                                mView.showPageError()
                            }

                            override fun onNext(animatePicturePage: AnimatePicturePage) {
                                if (animatePicturePage.animatePictureItems == null || animatePicturePage.animatePictureItems.isEmpty()) {
                                    mView.showPageEmpty()
                                } else {
                                    mView.showAnimatePictures(animatePicturePage.animatePictureItems)
                                    mView.showPageContent()
                                }
                            }
                        })
        )
    }

    fun getMoreComicInfos() {
        addSubscribe(
                mModel.getAnimatePictures(++pageIndex)
                        .compose(RxUtil.rxSchedulerHelper<AnimatePicturePage>())
                        .subscribeWith(object : CommonSubscriber<AnimatePicturePage>(mView) {
                            override fun onNext(animatePicturePage: AnimatePicturePage) {
                                mView.showMoreAnimatePictures(animatePicturePage.animatePictureItems,
                                        animatePicturePage.pageNumber < animatePicturePage.maxPages)
                            }

                            override fun onError(e: Throwable?) {
                                super.onError(e)
                                mView.onLoadMoreFail()
                                pageIndex--
                            }
                        })
        )
    }
}