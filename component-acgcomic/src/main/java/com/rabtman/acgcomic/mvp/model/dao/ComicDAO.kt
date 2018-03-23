package com.rabtman.acgcomic.mvp.model.dao

import com.rabtman.acgcomic.mvp.model.entity.ComicCache
import com.rabtman.common.utils.RxRealmUtils
import io.reactivex.Completable
import io.reactivex.Flowable
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * @author Rabtman
 */
class ComicDAO(val config: RealmConfiguration) {

    fun addComicCache(item: ComicCache): Completable {
        return RxRealmUtils.exec(config, { realm ->
            realm.executeTransactionAsync { r -> r.copyToRealmOrUpdate(item) }
        })
    }

    fun deleteComicCacheById(id: String): Completable {
        return RxRealmUtils.exec(config, { realm ->
            realm.executeTransactionAsync { r ->
                r.where(ComicCache::class.java)
                        .equalTo("comicId", id)
                        .findAll()
                        .deleteAllFromRealm()
            }
        })
    }

    fun getComicCacheById(id: String): Flowable<ComicCache> {
        Realm.getInstance(config).use { realm ->
            val queryResult = realm.where(ComicCache::class.java)
                    .equalTo("comicId", id)
                    .findFirst()
            return if (queryResult != null) {
                Flowable.just(realm.copyFromRealm(queryResult))
            } else {
                Flowable.just(ComicCache(comicId = id))
            }
        }
    }

    fun getComicCacheList(): Flowable<List<ComicCache>> {
        Realm.getInstance(config).use { realm ->
            val queryResult = realm.where(ComicCache::class.java)
                    .findAll()
            return if (queryResult != null) {
                Flowable.just(realm.copyFromRealm(queryResult))
            } else {
                Flowable.empty()
            }
        }
    }
}