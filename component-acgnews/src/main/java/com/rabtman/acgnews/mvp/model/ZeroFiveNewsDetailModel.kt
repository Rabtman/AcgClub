package com.rabtman.acgnews.mvp.model

import com.fcannizzaro.jsoup.annotations.JP
import com.rabtman.acgnews.mvp.contract.ZeroFiveNewsDetailContract
import com.rabtman.acgnews.mvp.model.jsoup.ZeroFiveNewsDetail
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
class ZeroFiveNewsDetailModel @Inject constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), ZeroFiveNewsDetailContract.Model {
    override fun getAcgNewsDetail(url: String?): Flowable<ZeroFiveNewsDetail> {
        return Flowable.create({ e ->
            val html: Element? = Jsoup.connect(url).get()
            if (html == null) {
                e.onError(Throwable("element html is null"))
            } else {
                val zeroFiveNewsDetail = JP.from(html, ZeroFiveNewsDetail::class.java)
                e.onNext(zeroFiveNewsDetail)
                e.onComplete()
            }
        }, BackpressureStrategy.BUFFER)
    }
}