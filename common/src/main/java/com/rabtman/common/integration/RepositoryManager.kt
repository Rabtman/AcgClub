package com.rabtman.common.integration;

import android.content.Context;
import io.realm.RealmConfiguration;
import io.rx_cache2.internal.RxCache;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import retrofit2.Retrofit;

/**
 * 用来管理网络请求层,以及数据缓存层,以后可以添加数据库请求层
 * 需要在{@link ConfigModule}的实现类中先inject需要的服务
 * Created by jess on 13/04/2017 09:52
 * Contact with jess.yan.effort@gmail.com
 */
@Singleton
public class RepositoryManager implements IRepositoryManager {

  private final Map<String, Object> mRetrofitServiceCache = new LinkedHashMap<>();
  private final Map<String, Object> mCacheServiceCache = new LinkedHashMap<>();
  private final Map<String, RealmConfiguration> mRealmConfigs = new LinkedHashMap<>();
  private Retrofit mRetrofit;
  private RxCache mRxCache;

  @Inject
  public RepositoryManager(Retrofit retrofit, RxCache rxCache) {
    this.mRetrofit = retrofit;
    this.mRxCache = rxCache;
  }

  /**
   * 注入RetrofitService,在{@link ConfigModule#registerComponents(Context, IRepositoryManager)}中进行注入
   */
  @Override
  public void injectRetrofitService(Class<?>... services) {
    for (Class<?> service : services) {
      if (mRetrofitServiceCache.containsKey(service.getName())) {
        continue;
      }
      mRetrofitServiceCache.put(service.getName(), mRetrofit.create(service));
    }

  }

  /**
   * 注入CacheService,在{@link ConfigModule#registerComponents(Context, IRepositoryManager)}中进行注入
   */
  @Override
  public void injectCacheService(Class<?>... services) {
    for (Class<?> service : services) {
      if (mCacheServiceCache.containsKey(service.getName())) {
        continue;
      }
      mCacheServiceCache.put(service.getName(), mRxCache.using(service));
    }
  }

  @Override
  public void injectRealmConfigs(RealmConfiguration... realmConfigurations) {
    for (RealmConfiguration realmConfiguration : realmConfigurations) {
      if (mRealmConfigs.containsKey(realmConfiguration.getRealmFileName())) {
        continue;
      }
      mRealmConfigs.put(realmConfiguration.getRealmFileName(), realmConfiguration);
    }
  }

  /**
   * 根据传入的Class获取对应的Retrift service
   */
  @Override
  public <T> T obtainRetrofitService(Class<T> service) {
    return (T) mRetrofitServiceCache.get(service.getName());
  }

  /**
   * 根据传入的Class获取对应的RxCache service
   */
  @Override
  public <T> T obtainCacheService(Class<T> cache) {
    return (T) mCacheServiceCache.get(cache.getName());
  }

  @Override
  public RealmConfiguration obtainRealmConfig(String realmFileName) {
    return mRealmConfigs.get(realmFileName);
  }
}
