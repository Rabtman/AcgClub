package com.rabtman.acgschedule.mvp.presenter

import android.Manifest.permission
import com.rabtman.acgschedule.R
import com.rabtman.acgschedule.mvp.contract.ScheduleMainContract
import com.rabtman.acgschedule.mvp.model.jsoup.DilidiliInfo
import com.rabtman.common.base.CommonSubscriber
import com.rabtman.common.base.mvp.BasePresenter
import com.rabtman.common.di.scope.FragmentScope
import com.rabtman.common.utils.RxUtil
import com.tbruyelle.rxpermissions2.RxPermissions
import javax.inject.Inject

/**
 * @author Rabtman
 */
@FragmentScope
class ScheduleMainPresenter @Inject constructor(model: ScheduleMainContract.Model,
                                                rootView: ScheduleMainContract.View) : BasePresenter<ScheduleMainContract.Model, ScheduleMainContract.View>(model, rootView) {
    fun getDilidiliInfo() {
        addSubscribe(
                mModel.getDilidiliInfo()
                        .compose(RxUtil.rxSchedulerHelper())
                        .subscribeWith(object : CommonSubscriber<DilidiliInfo>(mView) {
                            override fun onComplete() {
                                mView.hideLoading()
                            }

                            override fun onError(e: Throwable) {
                                super.onError(e)
                                mView.showPageError()
                            }

                            override fun onNext(dilidiliInfo: DilidiliInfo) {
                                mView.showDilidiliInfo(dilidiliInfo)
                                mView.showPageContent()
                            }
                        })
        )
    }

    /**
     * 视频观看权限申请
     */
    fun checkPermission2ScheduleVideo(rxPermissions: RxPermissions, videoUrl: String?) {
        videoUrl?.takeIf { it.isNotBlank() }?.let {
            rxPermissions.request(permission.WRITE_EXTERNAL_STORAGE,
                    permission.READ_PHONE_STATE,
                    permission.ACCESS_NETWORK_STATE,
                    permission.ACCESS_WIFI_STATE)
                    .subscribe { aBoolean ->
                        if (aBoolean) {
                            mView.start2ScheduleVideo(it)
                        } else {
                            mView.showError(R.string.msg_error_check_permission)
                        }
                    }
        } ?: run {
            mView.showError(R.string.msg_error_url_null)
        }
    }
}