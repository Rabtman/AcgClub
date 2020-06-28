package com.rabtman.acgschedule.mvp.model

import com.fcannizzaro.jsoup.annotations.JP
import com.rabtman.acgschedule.mvp.contract.ScheduleNewContract
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleNew
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
class ScheduleNewModel @Inject constructor(repositoryManager: IRepositoryManager?) : BaseModel(repositoryManager), ScheduleNewContract.Model {
    override fun getScheduleNew(url: String): Flowable<ScheduleNew> {
        return Flowable.create({ e ->
            val html: Element? = Jsoup.connect(url).get()
            if (html == null) {
                e.onError(Throwable("element html is null"))
            } else {
                val scheduleNew = JP.from(html, ScheduleNew::class.java)
                e.onNext(scheduleNew)
                e.onComplete()
            }
        }, BackpressureStrategy.BUFFER)
    }
}