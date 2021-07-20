package com.rabtman.acgpicture.mvp.presenter

import com.rabtman.acgpicture.mvp.AcgPictureContract
import com.rabtman.acgpicture.mvp.model.entity.AcgPictureItem
import com.rabtman.business.base.CommonSubscriber
import com.rabtman.common.base.mvp.BasePresenter
import com.rabtman.common.di.scope.FragmentScope
import com.rabtman.common.utils.RxUtil
import javax.inject.Inject

/**
 * @author Rabtman
 */
@FragmentScope
class AcgPictureItemPresenter
@Inject constructor(model: AcgPictureContract.Model,
                    view: AcgPictureContract.View) : BasePresenter<AcgPictureContract.Model, AcgPictureContract.View>(model, view) {
    /**
     * 当前类型
     */
    private var curType = "moeimg"
    /**
     * 当前页数
     */
    private var pageNo = 1

    fun setAcgPictureType(type: String) {
        this.curType = type
    }

    fun getAcgPictures() {
        pageNo = 1
        addSubscribe(
                mModel.getAcgPictures(pageNo, curType)
                        .compose(RxUtil.rxSchedulerHelper())
                        .subscribeWith(object : CommonSubscriber<List<AcgPictureItem>>(mView) {

                            override fun onComplete() {
                                super.onComplete()
                                mView.hideLoading()
                            }

                            override fun onNext(pictureItems: List<AcgPictureItem>) {
                                if (pictureItems.isEmpty()) {
                                    mView.showPageEmpty()
                                } else {
                                    mView.showPictures(pictureItems)
                                    mView.showPageContent()
                                }
                            }
                        })
        )
    }

    fun getMoreAcgPictures() {
        addSubscribe(
                mModel.getAcgPictures(++pageNo, curType)
                        .compose(RxUtil.rxSchedulerHelper())
                        .subscribeWith(object : CommonSubscriber<List<AcgPictureItem>>(mView) {
                            override fun onNext(pictureItems: List<AcgPictureItem>) {
                                mView.showMorePictures(pictureItems,
                                        !pictureItems.isEmpty())
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