package com.rabtman.acgschedule.mvp.presenter

import com.rabtman.acgschedule.mvp.contract.ScheduleOtherContract
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleOtherPage
import com.rabtman.business.base.CommonSubscriber
import com.rabtman.common.base.mvp.BasePresenter
import com.rabtman.common.di.scope.ActivityScope
import com.rabtman.common.utils.RxUtil
import javax.inject.Inject

/**
 * @author Rabtman
 */
@ActivityScope
class ScheduleOtherPresenter @Inject constructor(model: ScheduleOtherContract.Model,
                                                 rootView: ScheduleOtherContract.View) : BasePresenter<ScheduleOtherContract.Model, ScheduleOtherContract.View>(model, rootView) {
    /**
     * 当前视频类别地址
     */
    private var curScheduleOtherUrl: String? = null

    /**
     * 当前页数
     */
    private var pageNo = 1
    fun setCurScheduleOtherUrl(curScheduleOtherUrl: String?) {
        this.curScheduleOtherUrl = curScheduleOtherUrl
    }

    fun getScheduleOther() {
        pageNo = 1
        addSubscribe(
                mModel.getScheduleOtherPage("$curScheduleOtherUrl$pageNo.html")
                        .compose(RxUtil.rxSchedulerHelper())
                        .subscribeWith(object : CommonSubscriber<ScheduleOtherPage>(mView) {
                            override fun onComplete() {
                                mView.hideLoading()
                            }

                            override fun onError(e: Throwable) {
                                super.onError(e)
                                mView.showPageError()
                            }

                            override fun onNext(scheduleOther: ScheduleOtherPage) {
                                mView.showScheduleOther(scheduleOther)
                                if (scheduleOther.scheduleOtherItems!!.size > 0) {
                                    mView.showPageContent()
                                } else {
                                    mView.showPageEmpty()
                                }
                            }
                        })
        )
    }

    fun getMoreScheduleOther() {
        addSubscribe(
                mModel.getScheduleOtherPage(curScheduleOtherUrl + ++pageNo + ".html")
                        .compose(RxUtil.rxSchedulerHelper())
                        .subscribeWith(object : CommonSubscriber<ScheduleOtherPage>(mView) {
                            override fun onNext(scheduleOther: ScheduleOtherPage) {
                                mView.showMoreScheduleOther(scheduleOther, pageNo < scheduleOther.getPageCount())
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