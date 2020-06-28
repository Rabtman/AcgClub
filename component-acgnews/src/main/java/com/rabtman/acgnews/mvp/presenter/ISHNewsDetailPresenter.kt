package com.rabtman.acgnews.mvp.presenter

import android.Manifest
import com.rabtman.acgnews.R
import com.rabtman.acgnews.mvp.contract.ISHNewsDetailContract
import com.rabtman.acgnews.mvp.model.entity.SHPostDetail
import com.rabtman.common.base.CommonSubscriber
import com.rabtman.common.base.mvp.BasePresenter
import com.rabtman.common.di.scope.ActivityScope
import com.rabtman.common.utils.RxUtil
import com.tbruyelle.rxpermissions2.RxPermissions
import javax.inject.Inject

/**
 * @author Rabtman
 */
@ActivityScope
class ISHNewsDetailPresenter @Inject constructor(model: ISHNewsDetailContract.Model,
                                                 rootView: ISHNewsDetailContract.View) : BasePresenter<ISHNewsDetailContract.Model, ISHNewsDetailContract.View>(model, rootView) {
    fun start2Share(rxPermissions: RxPermissions) {
        rxPermissions.request(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe { aBoolean ->
                    if (aBoolean) {
                        mView.showShareView()
                    } else {
                        mView.showError(R.string.msg_error_check_permission)
                    }
                }
    }

    fun getNewsDetail(postId: Int) {
        if (postId == -1) {
            mView.showError(R.string.msg_error_url_null)
            return
        }
        addSubscribe(
                mModel.getAcgNewsDetail(postId)
                        .compose(RxUtil.rxSchedulerHelper())
                        .subscribeWith(object : CommonSubscriber<SHPostDetail>(mView) {
                            override fun onError(e: Throwable) {
                                super.onError(e)
                                mView.showPageError()
                            }

                            override fun onNext(ISHNewsDetail: SHPostDetail) {
                                mView.showNewsDetail(ISHNewsDetail)
                                mView.showPageContent()
                            }
                        })
        )
    }
}