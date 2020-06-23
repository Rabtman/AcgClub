package com.rabtman.acgpicture.mvp.presenter

import com.rabtman.acgpicture.mvp.AcgPictureMainContract
import com.rabtman.acgpicture.mvp.model.entity.AcgPictureType
import com.rabtman.common.base.CommonSubscriber
import com.rabtman.common.base.mvp.BasePresenter
import com.rabtman.common.di.scope.FragmentScope
import com.rabtman.common.utils.RxUtil
import javax.inject.Inject

/**
 * @author Rabtman
 */
@FragmentScope
class AcgPictureMainPresenter
@Inject constructor(model: AcgPictureMainContract.Model,
                    view: AcgPictureMainContract.View) : BasePresenter<AcgPictureMainContract.Model, AcgPictureMainContract.View>(model, view) {

    fun getAcgPictures() {
        addSubscribe(
                mModel.getAcgPictureType()
                        .compose(RxUtil.rxSchedulerHelper())
                        .subscribeWith(object : CommonSubscriber<List<AcgPictureType>>(mView) {

                            override fun onComplete() {
                                super.onComplete()
                                mView.hideLoading()
                            }

                            override fun onError(e: Throwable) {
                                super.onError(e)
                                mView.hideLoading()
                            }

                            override fun onNext(types: List<AcgPictureType>) {
                                if (types.isEmpty()) {
                                    mView.showPageEmpty()
                                } else {
                                    mView.showPictureType(types)
                                    mView.showPageContent()
                                }
                            }
                        })
        )
    }
}