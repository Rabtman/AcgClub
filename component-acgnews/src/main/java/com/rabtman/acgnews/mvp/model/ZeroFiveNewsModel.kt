package com.rabtman.acgnews.mvp.model

import com.fcannizzaro.jsoup.annotations.JP
import com.rabtman.acgnews.mvp.contract.ZeroFiveNewsContract
import com.rabtman.acgnews.mvp.model.jsoup.ZeroFiveNewsPage
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
class ZeroFiveNewsModel @Inject constructor(repositoryManager: IRepositoryManager?) : BaseModel(repositoryManager), ZeroFiveNewsContract.Model {
    override fun getAcgNews(typeUrl: String?): Flowable<ZeroFiveNewsPage> {
        return Flowable.create({ e ->
            val html: Element? = Jsoup.connect(typeUrl).get()
            if (html == null) {
                e.onError(Throwable("element html is null"))
            } else {
                val zeroFiveNewsPage = JP.from(html, ZeroFiveNewsPage::class.java)
                e.onNext(zeroFiveNewsPage)
                e.onComplete()
            }
        }, BackpressureStrategy.BUFFER)
    }
}