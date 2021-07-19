package com.rabtman.common.utils

import android.util.Pair
import io.reactivex.*
import io.reactivex.functions.Consumer
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * @author Rabtman realm事务转换rx工具类
 */
object RxRealmUtils {
    fun completableExec(
        configuration: RealmConfiguration?,
        transaction: Consumer<Realm>
    ): Completable {
        return configuration?.let {
            Completable.fromAction {
                Realm.getInstance(configuration).use { realm ->
                    realm.executeTransaction { r ->
                        try {
                            transaction.accept(r)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        } ?: run {
            Completable.error(Throwable("realm config is null"))
        }
    }

    fun <T> singleExec(
        configuration: RealmConfiguration?,
        emitter: Consumer<Pair<SingleEmitter<T>, Realm>>
    ): Single<T> {
        return Single.create { e ->
            configuration?.let {
                Realm.getInstance(it).use { realm -> emitter.accept(Pair(e, realm)) }
            } ?: run {
                e.onError(
                    Throwable("realm config is null")
                )
            }
        }
    }

    fun <T> flowableExec(
        configuration: RealmConfiguration?,
        emitter: Consumer<Pair<FlowableEmitter<T>, Realm>>
    ): Flowable<T> {
        return Flowable.create({ e ->
            configuration?.let {
                Realm.getInstance(it).use { realm -> emitter.accept(Pair(e, realm)) }
            } ?: run {
                e.onError(
                    Throwable("realm config is null")
                )
            }
        }, BackpressureStrategy.BUFFER)
    }
}