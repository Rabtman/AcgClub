package com.rabtman.common.integration

import io.realm.RealmConfiguration

/**
 * @author Rabtman
 */
interface IRepositoryManager {
    /**
     * 注入RetrofitService,在[ConfigModule.registerComponents]中进行注入
     */
    fun injectRetrofitService(vararg services: Class<*>)

    /**
     * 注入CacheService,在[ConfigModule.registerComponents]中进行注入
     */
    fun injectCacheService(vararg services: Class<*>)

    /**
     * 注入RealmConfigs,在[ConfigModule.registerComponents]中进行注入
     */
    fun injectRealmConfigs(vararg realmConfigurations: RealmConfiguration)

    /**
     * 根据传入的Class获取对应的Retrift service
     */
    fun <T> obtainRetrofitService(service: Class<T>): T

    /**
     * 根据传入的Class获取对应的RxCache service
     */
    fun <T> obtainCacheService(cache: Class<T>): T

    /**
     * 根据传入的RealmFileName获取对应的Realm配置
     */
    fun obtainRealmConfig(realmFileName: String): RealmConfiguration?
}