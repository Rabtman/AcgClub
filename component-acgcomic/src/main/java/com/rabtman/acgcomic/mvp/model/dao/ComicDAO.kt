package com.rabtman.acgcomic.mvp.model.dao

import com.rabtman.acgcomic.mvp.model.entity.db.ComicCache
import com.rabtman.common.utils.RxRealmUtils
import io.reactivex.Flowable
import io.realm.RealmConfiguration

/**
 * @author Rabtman
 */
class ComicDAO(val config: RealmConfiguration) {

    fun addComicCache(item: ComicCache): Flowable<ComicCache> {
        return RxRealmUtils.flowableExec<ComicCache>(config, { pair ->
            pair.second.executeTransaction { r ->
                pair.first.onNext(r.copyFromRealm(r.copyToRealmOrUpdate(item)))
                pair.first.onComplete()
            }
        })
    }

    fun deleteComicCacheById(id: String): Flowable<Boolean> {
        return RxRealmUtils.flowableExec<Boolean>(config, { pair ->
            pair.second.executeTransaction { r ->
                val isSuccess = r.where(ComicCache::class.java)
                        .equalTo("comicId", id)
                        .findAll()
                        .deleteAllFromRealm()
                pair.first.onNext(isSuccess)
                pair.first.onComplete()
            }
        })
    }

    fun getComicCacheById(id: String): Flowable<ComicCache> {
        return RxRealmUtils.flowableExec(config, { pair ->
            val queryResult = pair.second.where(ComicCache::class.java)
                    .equalTo("comicId", id)
                    .findFirst()
            if (queryResult != null) {
                pair.first.onNext(pair.second.copyFromRealm(queryResult))
            } else {
                pair.first.onNext(ComicCache(comicId = id))
            }
            pair.first.onComplete()
        })
    }

    fun getComicCacheByChapter(id: String, chapterPos: Int): Flowable<ComicCache> {
        return RxRealmUtils.flowableExec(config, { pair ->
            val queryResult = pair.second.where(ComicCache::class.java)
                    .equalTo("comicId", id)
                    .equalTo("chapterPos", chapterPos)
                    .findFirst()
            if (queryResult != null) {
                pair.first.onNext(pair.second.copyFromRealm(queryResult))
            } else {
                pair.first.onNext(ComicCache(comicId = id, chapterPos = chapterPos))
            }
            pair.first.onComplete()
        })
    }

    fun getComicCollectCaches(): Flowable<List<ComicCache>> {
        return RxRealmUtils.flowableExec(config, { pair ->
            val queryResult = pair.second.where(ComicCache::class.java)
                    .equalTo("isCollect", true)
                    .findAll()
            if (queryResult != null) {
                pair.first.onNext(pair.second.copyFromRealm(queryResult))
            }
            pair.first.onComplete()
        })
    }
}