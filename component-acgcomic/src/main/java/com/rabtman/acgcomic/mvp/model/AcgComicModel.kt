package com.rabtman.acgcomic.mvp

import com.google.gson.Gson
import com.rabtman.acgcomic.api.AcgComicService
import com.rabtman.acgcomic.base.constant.SystemConstant
import com.rabtman.acgcomic.mvp.model.dao.ComicDAO
import com.rabtman.acgcomic.mvp.model.entity.*
import com.rabtman.common.base.mvp.BaseModel
import com.rabtman.common.di.scope.ActivityScope
import com.rabtman.common.di.scope.FragmentScope
import com.rabtman.common.integration.IRepositoryManager
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * @author Rabtman
 */
@FragmentScope
class DmzjComicModel @Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), DmzjComicContract.Model {

    override fun getComicInfos(selected: String): Flowable<List<DmzjComicItem>> {
        return mRepositoryManager.obtainRetrofitService(AcgComicService::class.java)
                .getDmzjComicList(selected)
    }
}

@FragmentScope
class OacgComicModel @Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), OacgComicContract.Model {

    override fun getSearchComicInfos(keyword: String): Flowable<OacgComicPage> {
        return mRepositoryManager.obtainRetrofitService(AcgComicService::class.java)
                .searchOacgComicInfos(keyword)
    }

    override fun getComicInfos(themeId: Int, pageNo: Int): Flowable<OacgComicPage> {
        return mRepositoryManager.obtainRetrofitService(AcgComicService::class.java)
                .getOacgComicList(themeId, pageNo)
    }
}

@ActivityScope
class OacgComicDetailModel @Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), OacgComicDetailContract.Model {
    private val DAO = ComicDAO(mRepositoryManager.obtainRealmConfig(SystemConstant.DB_NAME))

    override fun getComicDetail(comicId: Int): Flowable<List<OacgComicEpisode>> {
        return mRepositoryManager.obtainRetrofitService(AcgComicService::class.java)
                .getOacgComicDetail(comicId)
    }

    override fun getComicCacheById(comicId: String): Flowable<ComicCache> {
        return DAO.getComicCacheById(comicId)
    }

    override fun collectComic(comicItem: OacgComicItem, isAdd: Boolean): Completable {
        return DAO.getComicCacheById(comicItem.id)
                .flatMapCompletable({ comicCache ->
                    if (comicCache.comicId.isEmpty()) {
                        comicCache.comicId = comicItem.id
                        comicCache.comicName = comicItem.comicName
                        comicCache.comicImgUrl = comicItem.comicPicUrl
                        comicCache.comicDetailJson = Gson().toJson(comicItem)
                        comicCache.comicSource = SystemConstant.COMIC_SOURCE_OACG
                    }
                    comicCache.isCollect = isAdd
                    DAO.addComicCache(comicCache)
                })

    }

    override fun updateComicLastChapter(comicId: String, lastChapterPos: Int): Completable {
        return DAO.getComicCacheById(comicId)
                .flatMapCompletable({ comicCache ->
                    comicCache.chapterPos = lastChapterPos
                    comicCache.pagePos = 0
                    DAO.addComicCache(comicCache)
                })
    }
}

@ActivityScope
class OacgComicEpisodeDetailModel @Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), OacgComicEpisodeDetailContract.Model {
    private val DAO = ComicDAO(mRepositoryManager.obtainRealmConfig(SystemConstant.DB_NAME))

    override fun getEpisodeDetail(comicId: Int, chapterIndex: Int): Flowable<OacgComicEpisodePage> {
        return mRepositoryManager.obtainRetrofitService(AcgComicService::class.java)
                .getOacgEpisodeDetail(comicId, chapterIndex)
    }

    override fun getComicCacheById(comicId: String): Flowable<ComicCache> {
        return DAO.getComicCacheById(comicId)
    }

    override fun updateComicLastRecord(comicId: String, lastChapterPos: Int, lastPagePos: Int): Completable {
        return DAO.getComicCacheById(comicId)
                .flatMapCompletable({ comicCache ->
                    comicCache.chapterPos = lastChapterPos
                    comicCache.pagePos = lastPagePos
                    DAO.addComicCache(comicCache)
                })
    }
}
