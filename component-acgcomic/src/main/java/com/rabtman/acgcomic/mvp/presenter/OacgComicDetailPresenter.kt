package com.rabtman.acgcomic.mvp.presenter

import com.rabtman.acgcomic.mvp.OacgComicDetailContract
import com.rabtman.acgcomic.mvp.OacgComicDetailContract.Model
import com.rabtman.acgcomic.mvp.OacgComicDetailContract.View
import com.rabtman.acgcomic.mvp.model.entity.OacgComicDetail
import com.rabtman.common.base.CommonSubscriber
import com.rabtman.common.base.mvp.BasePresenter
import com.rabtman.common.di.scope.ActivityScope
import com.rabtman.common.utils.LogUtil
import com.rabtman.common.utils.RxUtil
import javax.inject.Inject

/**
 * @author Rabtman
 */
@ActivityScope
class OacgComicDetailPresenter @Inject
constructor(model: OacgComicDetailContract.Model,
            rootView: OacgComicDetailContract.View) : BasePresenter<Model, View>(model, rootView) {

    fun getOacgComicDetail(comicId: String) {
        addSubscribe(
                mModel.getComicDetail(comicId.toInt())
                        .compose(RxUtil.rxSchedulerHelper<List<OacgComicDetail>>())
                        .subscribeWith(object : CommonSubscriber<List<OacgComicDetail>>(mView) {
                            override protected fun onStart() {
                                super.onStart()
                                mView.showLoading()
                            }

                            override fun onComplete() {
                                mView.hideLoading()
                            }

                            override fun onNext(comicDetails: List<OacgComicDetail>) {
                                LogUtil.d("getOacgComicDetail" + comicDetails.toString())
                                mView.showComicDetail(comicDetails)
                            }
                        })
        )
    }

}
