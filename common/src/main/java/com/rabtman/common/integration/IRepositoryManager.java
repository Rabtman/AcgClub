package com.rabtman.common.integration;

import android.content.Context;

/**
 * @author Rabtman
 */

public interface IRepositoryManager {

  /**
   * 注入RetrofitService,在{@link ConfigModule#registerComponents(Context, IRepositoryManager)}中进行注入
   */
  void injectRetrofitService(Class<?>... services);

  /**
   * 注入CacheService,在{@link ConfigModule#registerComponents(Context, IRepositoryManager)}中进行注入
   */
  void injectCacheService(Class<?>... services);


  /**
   * 根据传入的Class获取对应的Retrift service
   */
  <T> T obtainRetrofitService(Class<T> service);

  /**
   * 根据传入的Class获取对应的RxCache service
   */
  <T> T obtainCacheService(Class<T> cache);

}
