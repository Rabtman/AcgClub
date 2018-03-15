package com.rabtman.acgcomic.mvp.model.dao

import com.rabtman.acgcomic.mvp.model.entity.OacgComicItem
import com.rabtman.common.utils.RxRealmUtils
import io.reactivex.Completable
import io.reactivex.Flowable
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * @author Rabtman
 */
class OacgComicDAO(val config: RealmConfiguration) {

    fun saveOacgComicItem(item: OacgComicItem): Completable {
        return RxRealmUtils.exec(config, { realm ->
            realm.executeTransactionAsync { r -> r.copyToRealmOrUpdate(item) }
        })
    }

    fun deleteById(id: String): Completable {
        return RxRealmUtils.exec(config, { realm ->
            realm.executeTransactionAsync { r ->
                r.where(OacgComicItem::class.java)
                        .equalTo("id", id)
                        .findAll()
                        .deleteAllFromRealm()
            }
        })
    }

    fun getOacgComicItemById(id: String): Flowable<OacgComicItem> {
        Realm.getInstance(config).use { realm ->
            val queryResult = realm.where(OacgComicItem::class.java)
                    .equalTo("id", id)
                    .findFirst()
            return if (queryResult != null) {
                Flowable.just(realm.copyFromRealm(queryResult))
            } else {
                Flowable.empty()
            }
        }
    }

    fun getOacgComicItems(): Flowable<List<OacgComicItem>> {
        Realm.getInstance(config).use { realm ->
            val queryResult = realm.where(OacgComicItem::class.java)
                    .findAll()
            return if (queryResult != null) {
                Flowable.just(realm.copyFromRealm(queryResult))
            } else {
                Flowable.empty()
            }
        }
    }
}