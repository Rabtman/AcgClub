package com.rabtman.acgschedule.mvp.model

import android.text.TextUtils
import com.fcannizzaro.jsoup.annotations.JP
import com.rabtman.acgschedule.base.constant.HtmlConstant
import com.rabtman.acgschedule.base.constant.SystemConstant
import com.rabtman.acgschedule.mvp.contract.ScheduleDetailContract
import com.rabtman.acgschedule.mvp.model.dao.ScheduleDAO
import com.rabtman.acgschedule.mvp.model.entity.ScheduleCache
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleDetail
import com.rabtman.common.base.mvp.BaseModel
import com.rabtman.common.di.scope.ActivityScope
import com.rabtman.common.integration.IRepositoryManager
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import javax.inject.Inject

/**
 * @author Rabtman
 */
@ActivityScope
class ScheduleDetailModel @Inject constructor(repositoryManager: IRepositoryManager?) : BaseModel(repositoryManager), ScheduleDetailContract.Model {
    private val DAO = ScheduleDAO(
            mRepositoryManager.obtainRealmConfig(SystemConstant.DB_NAME))

    override fun getScheduleDetail(url: String): Flowable<ScheduleDetail> {
        return Flowable.create({ e ->
            var scheduleLink = url
            if (!url.contains("http")) {
                scheduleLink = HtmlConstant.YHDM_M_URL + url
            }
            val html: Element? = Jsoup.connect(scheduleLink).get()
            if (html == null) {
                e.onError(Throwable("element html is null"))
            } else {
                val scheduleDetail = JP.from(html, ScheduleDetail::class.java)
                e.onNext(scheduleDetail)
                e.onComplete()
            }
        }, BackpressureStrategy.BUFFER)
    }

    override fun getScheduleCacheByUrl(scheduleUrl: String): Flowable<ScheduleCache> {
        return DAO.getScheduleCacheByUrl(scheduleUrl)
    }

    override fun updateScheduleWatchRecord(item: ScheduleCache,
                                           lastWatchPos: Int): Flowable<ScheduleCache> {
        return DAO.getScheduleCacheByUrl(item.scheduleUrl)
                .flatMap { scheduleCache ->
                    var scheduleCache = scheduleCache
                    if (TextUtils.isEmpty(scheduleCache.scheduleUrl)) {
                        scheduleCache = item
                    }
                    scheduleCache.lastWatchPos = lastWatchPos
                    DAO.addScheduleCache(scheduleCache)
                }
    }

    override fun collectSchedule(item: ScheduleCache, isAdd: Boolean): Flowable<ScheduleCache> {
        return DAO.getScheduleCacheByUrl(item.scheduleUrl)
                .flatMap { scheduleCache ->
                    var scheduleCache = scheduleCache
                    if (TextUtils.isEmpty(scheduleCache.scheduleUrl)) {
                        scheduleCache = item
                    }
                    scheduleCache.isCollect = isAdd
                    DAO.addScheduleCache(scheduleCache)
                }
    }
}