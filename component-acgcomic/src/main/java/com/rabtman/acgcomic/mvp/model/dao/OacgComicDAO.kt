package com.rabtman.acgcomic.mvp.model.dao

import com.rabtman.acgcomic.mvp.model.entity.OacgComicItem
import io.reactivex.Flowable
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * @author Rabtman
 */
class OacgComicDAO(val config: RealmConfiguration) {

    fun saveOacgComicItem(item: OacgComicItem) {
        Realm.getInstance(config).use { realm ->
            realm.executeTransactionAsync { realm.copyToRealmOrUpdate(item) }
        }
    }

    fun deleteOacgComicItem(item: OacgComicItem) {
        Realm.getInstance(config).use { realm ->
            realm.executeTransactionAsync { item.deleteFromRealm() }
        }
    }

    fun getOacgComicItemById(id: String): Flowable<OacgComicItem> {
        Realm.getInstance(config).use { realm ->
            return realm.where(OacgComicItem::class.java)
                    .equalTo("id", id)
                    .findFirstAsync()
                    .asFlowable()
        }
    }
}