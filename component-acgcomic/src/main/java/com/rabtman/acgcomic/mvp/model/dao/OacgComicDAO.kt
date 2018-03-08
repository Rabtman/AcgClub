package com.rabtman.acgcomic.mvp.model.dao

import com.rabtman.acgcomic.mvp.model.entity.OacgComicItem
import io.reactivex.Flowable
import io.realm.Realm

/**
 * @author Rabtman
 */
class OacgComicDAO {

    fun saveOacgComicItem(item: OacgComicItem) {
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransactionAsync { realm.copyToRealmOrUpdate(item) }
        }
    }

    fun getOacgComicItemById(id: String): Flowable<OacgComicItem> {
        Realm.getDefaultInstance().use { realm ->
            return realm.where(OacgComicItem::class.java)
                    .equalTo("id", id)
                    .findFirstAsync()
                    .asFlowable()
        }
    }
}