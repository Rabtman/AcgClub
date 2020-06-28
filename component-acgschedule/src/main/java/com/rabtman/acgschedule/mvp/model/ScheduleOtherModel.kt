package com.rabtman.acgschedule.mvp.model

import com.fcannizzaro.jsoup.annotations.JP
import com.rabtman.acgschedule.mvp.contract.ScheduleOtherContract
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleOtherPage
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
class ScheduleOtherModel @Inject constructor(repositoryManager: IRepositoryManager?) : BaseModel(repositoryManager), ScheduleOtherContract.Model {
    override fun getScheduleOtherPage(url: String): Flowable<ScheduleOtherPage> {
        return Flowable.create({ e ->
            val html: Element? = Jsoup.connect(url).get()
            if (html == null) {
                e.onError(Throwable("element html is null"))
            } else {
                val scheduleOtherPage = JP.from(html, ScheduleOtherPage::class.java)
                e.onNext(scheduleOtherPage)
                e.onComplete()
            }
        }, BackpressureStrategy.BUFFER)
    }
}