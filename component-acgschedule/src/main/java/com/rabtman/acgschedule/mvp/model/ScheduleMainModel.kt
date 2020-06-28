package com.rabtman.acgschedule.mvp.model

import com.fcannizzaro.jsoup.annotations.JP
import com.rabtman.acgschedule.base.constant.HtmlConstant
import com.rabtman.acgschedule.mvp.contract.ScheduleMainContract
import com.rabtman.acgschedule.mvp.model.jsoup.DilidiliInfo
import com.rabtman.common.base.mvp.BaseModel
import com.rabtman.common.di.scope.FragmentScope
import com.rabtman.common.integration.IRepositoryManager
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import javax.inject.Inject

/**
 * @author Rabtman
 */
@FragmentScope
class ScheduleMainModel @Inject constructor(repositoryManager: IRepositoryManager?) : BaseModel(repositoryManager), ScheduleMainContract.Model {
    override fun getDilidiliInfo(): Flowable<DilidiliInfo> {
        return Flowable.create({ e ->
            val html: Element? = Jsoup.connect(HtmlConstant.YHDM_M_URL).get()
            if (html == null) {
                e.onError(Throwable("element html is null"))
            } else {
                val dilidiliInfo = JP.from(html, DilidiliInfo::class.java)
                e.onNext(dilidiliInfo)
                e.onComplete()
            }
        }, BackpressureStrategy.BUFFER)
    }
}