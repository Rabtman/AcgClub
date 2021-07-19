package com.rabtman.common.integration

import io.realm.RealmConfiguration
import io.rx_cache2.internal.RxCache
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 用来管理网络请求层,以及数据缓存层,以后可以添加数据库请求层
 * 需要在[ConfigModule]的实现类中先inject需要的服务
 * Created by jess on 13/04/2017 09:52
 * Contact with jess.yan.effort@gmail.com
 */
@Singleton
class RepositoryManager @Inject constructor(
    private val mRetrofit: Retrofit,
    private val mRxCache: RxCache
) : IRepositoryManager {
    private val mRetrofitServiceCache: MutableMap<String, Any> = mutableMapOf()
    private val mCacheServiceCache: MutableMap<String, Any> = mutableMapOf()
    private val mRealmConfigs: MutableMap<String, RealmConfiguration> = mutableMapOf()

    /**
     * 注入RetrofitService,在[ConfigModule.registerComponents]中进行注入
     */
    override fun injectRetrofitService(vararg services: Class<*>) {
        for (service in services) {
            if (mRetrofitServiceCache.containsKey(service.name)) {
                continue
            }
            mRetrofitServiceCache[service.name] = mRetrofit.create(service)
        }
    }

    /**
     * 注入CacheService,在[ConfigModule.registerComponents]中进行注入
     */
    override fun injectCacheService(vararg services: Class<*>) {
        for (service in services) {
            if (mCacheServiceCache.containsKey(service.name)) {
                continue
            }
            mCacheServiceCache[service.name] = mRxCache.using(service)
        }
    }

    override fun injectRealmConfigs(vararg realmConfigurations: RealmConfiguration) {
        for (realmConfiguration in realmConfigurations) {
            if (mRealmConfigs.containsKey(realmConfiguration.realmFileName)) {
                continue
            }
            mRealmConfigs[realmConfiguration.realmFileName] = realmConfiguration
        }
    }

    /**
     * 根据传入的Class获取对应的Retrift service
     */
    override fun <T> obtainRetrofitService(service: Class<T>): T {
        return mRetrofitServiceCache[service.name] as T
    }

    /**
     * 根据传入的Class获取对应的RxCache service
     */
    override fun <T> obtainCacheService(cache: Class<T>): T {
        return mCacheServiceCache[cache.name] as T
    }

    override fun obtainRealmConfig(realmFileName: String): RealmConfiguration? {
        return mRealmConfigs[realmFileName]
    }
}