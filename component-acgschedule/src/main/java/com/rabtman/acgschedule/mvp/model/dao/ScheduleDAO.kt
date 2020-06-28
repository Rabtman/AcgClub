package com.rabtman.acgschedule.mvp.model.dao

import com.rabtman.acgschedule.mvp.model.entity.ScheduleCache
import com.rabtman.common.utils.RxRealmUtils
import io.reactivex.Flowable
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * @author Rabtman
 */
class ScheduleDAO(private val realmConfiguration: RealmConfiguration) {
    fun addScheduleCache(item: ScheduleCache): Flowable<ScheduleCache> {
        return RxRealmUtils
                .flowableExec(realmConfiguration) { pair ->
                    pair.second.executeTransaction { r ->
                        pair.first.onNext(r.copyFromRealm(r.copyToRealmOrUpdate(item)))
                        pair.first.onComplete()
                    }
                }
    }

    fun deleteScheduleCacheByUrl(url: String?): Flowable<Boolean> {
        return RxRealmUtils
                .flowableExec(realmConfiguration) { pair ->
                    pair.second.executeTransactionAsync { r ->
                        val isSuccess = r.where(ScheduleCache::class.java)
                                .equalTo("scheduleUrl", url)
                                .findAll()
                                .deleteAllFromRealm()
                        pair.first.onNext(isSuccess)
                        pair.first.onComplete()
                    }
                }
    }

    fun getScheduleCacheByUrl(url: String?): Flowable<ScheduleCache> {
        Realm.getInstance(realmConfiguration).use { realm ->
            val queryResult = realm.where(ScheduleCache::class.java)
                    .equalTo("scheduleUrl", url)
                    .findFirst()
            return Flowable.just(
                    if (queryResult == null) ScheduleCache("", -1) else realm.copyFromRealm(queryResult)
            )
        }
    }

    val scheduleCollectCaches: Flowable<List<ScheduleCache>>
        get() {
            Realm.getInstance(realmConfiguration).use { realm ->
                val queryResult = realm.where(ScheduleCache::class.java)
                        .equalTo("isCollect", true)
                        .findAll()
                return if (queryResult != null) {
                    Flowable.just(realm.copyFromRealm(queryResult))
                } else {
                    Flowable.empty()
                }
            }
        }

}