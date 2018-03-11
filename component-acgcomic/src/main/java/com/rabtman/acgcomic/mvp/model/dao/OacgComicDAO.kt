package com.rabtman.acgcomic.mvp.model.dao

import com.rabtman.acgcomic.mvp.model.entity.OacgComicItem
import com.rabtman.acgcomic.mvp.model.entity.TestObject
import com.rabtman.common.utils.RxRealmUtils
import io.reactivex.Completable
import io.reactivex.Flowable
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmObject

/**
 * @author Rabtman
 */
class OacgComicDAO(val config: RealmConfiguration) {

    fun saveOacgComicItem(item: OacgComicItem): Completable {
        return RxRealmUtils.exec(config, { realm ->
            realm.executeTransactionAsync { realm.copyToRealmOrUpdate(item) }
        })
    }

    fun deleteOacgComicItem(item: OacgComicItem): Completable {
        return RxRealmUtils.exec(config, { realm ->
            realm.executeTransactionAsync { RealmObject.deleteFromRealm(item) }
        })
    }

    fun getOacgComicItemById(id: String): Flowable<OacgComicItem> {
        Realm.getInstance(config).use { realm ->
            return RealmObject.asFlowable(realm.where(OacgComicItem::class.java)
                    .equalTo("id", id)
                    .findFirstAsync()
            )
        }
    }

    fun getTest(id: String): Flowable<TestObject> {
        Realm.getDefaultInstance().use { realm ->
            return RealmObject.asFlowable(realm.where(TestObject::class.java)
                    .equalTo("id", id)
                    .findFirstAsync()
            )
        }
    }
}