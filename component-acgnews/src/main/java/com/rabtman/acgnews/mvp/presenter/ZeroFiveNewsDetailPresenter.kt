package com.rabtman.acgnews.mvp.presenter

import android.Manifest
import android.text.TextUtils
import com.rabtman.acgnews.R
import com.rabtman.acgnews.mvp.contract.ZeroFiveNewsDetailContract
import com.rabtman.acgnews.mvp.model.jsoup.ZeroFiveNewsDetail
import com.rabtman.business.base.CommonSubscriber
import com.rabtman.common.base.mvp.BasePresenter
import com.rabtman.common.di.scope.ActivityScope
import com.rabtman.common.utils.RxUtil
import com.tbruyelle.rxpermissions2.RxPermissions
import javax.inject.Inject

/**
 * @author Rabtman
 */
@ActivityScope
class ZeroFiveNewsDetailPresenter @Inject constructor(model: ZeroFiveNewsDetailContract.Model,
                                                      rootView: ZeroFiveNewsDetailContract.View) : BasePresenter<ZeroFiveNewsDetailContract.Model, ZeroFiveNewsDetailContract.View>(model, rootView) {
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

    fun getNewsDetail(url: String?) {
        if (TextUtils.isEmpty(url)) {
            mView.showError(R.string.msg_error_url_null)
            return
        }
        addSubscribe(
                mModel.getAcgNewsDetail(url)
                        .compose(RxUtil.rxSchedulerHelper())
                        .subscribeWith(object : CommonSubscriber<ZeroFiveNewsDetail>(mView) {
                            override fun onError(e: Throwable) {
                                super.onError(e)
                                mView.showPageError()
                            }

                            override fun onNext(zeroFiveNewsDetail: ZeroFiveNewsDetail) {
                                mView.showNewsDetail(zeroFiveNewsDetail)
                                mView.showPageContent()
                            }
                        })
        )
    }
}