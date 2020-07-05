package com.rabtman.acgschedule.mvp.presenter

import com.rabtman.acgschedule.base.constant.HtmlConstant
import com.rabtman.acgschedule.mvp.contract.ScheduleNewContract
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleNew
import com.rabtman.common.base.CommonSubscriber
import com.rabtman.common.base.mvp.BasePresenter
import com.rabtman.common.di.scope.ActivityScope
import com.rabtman.common.utils.RxUtil
import java.util.*
import javax.inject.Inject

/**
 * @author Rabtman
 */
@ActivityScope
class ScheduleNewPresenter @Inject constructor(model: ScheduleNewContract.Model, rootView: ScheduleNewContract.View) : BasePresenter<ScheduleNewContract.Model, ScheduleNewContract.View>(model, rootView) {//String url = getScheduleNewUrl();
    /**
     * 获取本周动漫排行
     */
    fun getScheduleWeekRank() {
        //String url = getScheduleNewUrl();
        addSubscribe(
                mModel.getScheduleNew(HtmlConstant.YHDM_URL)
                        .compose(RxUtil.rxSchedulerHelper())
                        .subscribeWith(object : CommonSubscriber<ScheduleNew>(mView) {
                            override fun onComplete() {
                                mView.hideLoading()
                            }

                            override fun onError(e: Throwable) {
                                super.onError(e)
                                mView.showPageError()
                            }

                            override fun onNext(scheduleNew: ScheduleNew) {
                                mView.showScheduleNew(scheduleNew)
                                if (scheduleNew.scheduleNewItems!!.size > 0) {
                                    mView.showPageContent()
                                } else {
                                    mView.showPageEmpty()
                                }
                            }
                        })
        )
    }//十月新番//七月新番//四月新番//一月新番

    //获取往季最近新番列表地址
    private fun getScheduleNewUrl(): String {
        val urlBuilder = StringBuilder(HtmlConstant.YHDM_URL).append("/")
        val cal = Calendar.getInstance()
        cal.timeInMillis = System.currentTimeMillis()
        val month = cal[Calendar.MONTH] + 1 - 3
        if (1 <= month && month < 4) { //一月新番
            urlBuilder.append(cal[Calendar.YEAR])
            urlBuilder.append("01")
        } else if (4 <= month && month < 7) { //四月新番
            urlBuilder.append(cal[Calendar.YEAR])
            urlBuilder.append("04")
        } else if (7 <= month && month < 10) { //七月新番
            urlBuilder.append(cal[Calendar.YEAR])
            urlBuilder.append("07")
        } else { //十月新番
            urlBuilder.append(cal[Calendar.YEAR] - 1)
            urlBuilder.append("10")
        }
        return urlBuilder.toString()
    }
}