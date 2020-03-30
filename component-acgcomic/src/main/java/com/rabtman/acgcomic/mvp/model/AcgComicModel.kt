package com.rabtman.acgcomic.mvp.model

import com.fcannizzaro.jsoup.annotations.JP
import com.google.gson.Gson
import com.rabtman.acgcomic.api.AcgComicService
import com.rabtman.acgcomic.base.constant.HtmlConstant
import com.rabtman.acgcomic.base.constant.SystemConstant
import com.rabtman.acgcomic.mvp.DmzjComicContract
import com.rabtman.acgcomic.mvp.QiMIaoComicChapterDetailContract
import com.rabtman.acgcomic.mvp.QiMiaoComicContract
import com.rabtman.acgcomic.mvp.QiMiaoComicDetailContract
import com.rabtman.acgcomic.mvp.model.dao.ComicDAO
import com.rabtman.acgcomic.mvp.model.entity.DmzjComicItem
import com.rabtman.acgcomic.mvp.model.entity.QiMiaoChapterContent
import com.rabtman.acgcomic.mvp.model.entity.db.ComicCache
import com.rabtman.acgcomic.mvp.model.entity.jsoup.QiMiaoComicChapterDetail
import com.rabtman.acgcomic.mvp.model.entity.jsoup.QiMiaoComicDetail
import com.rabtman.acgcomic.mvp.model.entity.jsoup.QiMiaoComicItem
import com.rabtman.acgcomic.mvp.model.entity.jsoup.QiMiaoComicPage
import com.rabtman.common.base.mvp.BaseModel
import com.rabtman.common.di.scope.ActivityScope
import com.rabtman.common.di.scope.FragmentScope
import com.rabtman.common.integration.IRepositoryManager
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import javax.inject.Inject

/**
 * @author Rabtman
 */
@FragmentScope
class DmzjComicModel @Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), DmzjComicContract.Model {

    override fun getComicInfo(selected: String): Flowable<List<DmzjComicItem>> {
        return mRepositoryManager.obtainRetrofitService(AcgComicService::class.java)
                .getDmzjComicList(selected)
    }
}

@FragmentScope
class QiMiaoComicModel @Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), QiMiaoComicContract.Model {

    override fun getSearchComicInfo(keyword: String, pageNo: Int): Flowable<QiMiaoComicPage> {
        return Flowable.create<QiMiaoComicPage>({ emitter ->
            val html: Element? = Jsoup.connect(HtmlConstant.QIMIAO_URL + "/action/Search?keyword=$keyword&page=$pageNo").get()
            if (html == null) {
                emitter.onError(Throwable("element html is null"))
            } else {
                val qiMiaoComicPage: QiMiaoComicPage = JP.from(html, QiMiaoComicPage::class.java)
                emitter.onNext(qiMiaoComicPage)
                emitter.onComplete()
            }
        }, BackpressureStrategy.BUFFER)
    }

    override fun getComicInfo(keyword: String, pageNo: Int, isSearch: Boolean): Flowable<QiMiaoComicPage> {
        return Flowable.create<QiMiaoComicPage>({ emitter ->
            val html: Element? = if (isSearch) {
                Jsoup.connect(HtmlConstant.QIMIAO_URL + "/action/Search?keyword=$keyword&page=$pageNo").get()
            } else {
                Jsoup.connect(HtmlConstant.QIMIAO_URL + "/list-1-$keyword-----updatetime--$pageNo.html").get()
            }
            if (html == null) {
                emitter.onError(Throwable("element html is null"))
            } else {
                val qiMiaoComicPage: QiMiaoComicPage = JP.from(html, QiMiaoComicPage::class.java)
                emitter.onNext(qiMiaoComicPage)
                emitter.onComplete()
            }
        }, BackpressureStrategy.BUFFER)
    }
}

@ActivityScope
class QiMiaoComicDetailModel @Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), QiMiaoComicDetailContract.Model {
    private val DAO = ComicDAO(mRepositoryManager.obtainRealmConfig(SystemConstant.DB_NAME))

    override fun getComicDetail(detailUrl: String): Flowable<QiMiaoComicDetail> {
        return Flowable.create<QiMiaoComicDetail>({ emitter ->
            val html: Element? = Jsoup.connect(HtmlConstant.QIMIAO_URL + detailUrl).get()
            if (html == null) {
                emitter.onError(Throwable("element html is null"))
            } else {
                val comicDetail: QiMiaoComicDetail = JP.from(html, QiMiaoComicDetail::class.java)
                emitter.onNext(comicDetail)
                emitter.onComplete()
            }
        }, BackpressureStrategy.BUFFER)
    }

    override fun getComicCacheById(comicId: String): Flowable<ComicCache> {
        return DAO.getComicCacheById(comicId)
    }

    override fun collectComic(comicItem: QiMiaoComicItem, isAdd: Boolean): Flowable<ComicCache> {
        return DAO.getComicCacheById(comicItem.comicId)
                .flatMap({ comicCache ->
                    if (comicCache.comicDetailJson.isEmpty()) {
                        comicCache.comicId = comicItem.comicId
                        comicCache.comicName = comicItem.title
                        comicCache.comicImgUrl = comicItem.imgUrl
                        comicCache.comicDetailJson = Gson().toJson(comicItem)
                        comicCache.comicSource = SystemConstant.COMIC_SOURCE_QIMIAO
                    }
                    comicCache.isCollect = isAdd
                    DAO.addComicCache(comicCache)
                })

    }

    override fun updateComicLastChapter(comicId: String, lastChapterId: Int): Flowable<ComicCache> {
        return DAO.getComicCacheById(comicId)
                .flatMap({ comicCache ->
                    //如果不是不是上一次观看的章节，则重置观看的页面位置
                    if (comicCache.chapterPos != lastChapterId) {
                        comicCache.chapterPos = lastChapterId
                        comicCache.pagePos = 0
                    }
                    DAO.addComicCache(comicCache)
                })
    }
}

@ActivityScope
class QiMiaoComicEpisodeDetailModel @Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), QiMIaoComicChapterDetailContract.Model {
    private val DAO = ComicDAO(mRepositoryManager.obtainRealmConfig(SystemConstant.DB_NAME))

    override fun getChapterDetail(comicId: String, chapterId: String): Flowable<QiMiaoComicChapterDetail> {
        return Flowable.zip(mRepositoryManager.obtainRetrofitService(AcgComicService::class.java).getQiMiaoChapterContent(comicId, chapterId, Math.random()),
                Flowable.create<QiMiaoComicChapterDetail>({ emitter ->
                    val html: Element? = Jsoup.connect(HtmlConstant.QIMIAO_URL + "/manhua/$comicId/$chapterId.html").get()
                    if (html == null) {
                        emitter.onError(Throwable("element html is null"))
                    } else {
                        val chapterDetail: QiMiaoComicChapterDetail = JP.from(html, QiMiaoComicChapterDetail::class.java)
                        emitter.onNext(chapterDetail)
                        emitter.onComplete()
                    }
                }, BackpressureStrategy.BUFFER), BiFunction<QiMiaoChapterContent, QiMiaoComicChapterDetail, QiMiaoComicChapterDetail> { content, detail ->
            detail.listImg.addAll(content.listImg)
            detail
        })
    }

    override fun getComicCacheByChapter(comicId: String, chapterPos: Int): Flowable<ComicCache> {
        return DAO.getComicCacheByChapter(comicId, chapterPos)
    }

    override fun updateComicLastRecord(comicId: String, lastChapterPos: Int, lastPagePos: Int): Flowable<ComicCache> {
        return DAO.getComicCacheById(comicId)
                .flatMap({ comicCache ->
                    comicCache.chapterPos = lastChapterPos
                    comicCache.pagePos = lastPagePos
                    DAO.addComicCache(comicCache)
                })
    }
}
