package com.rabtman.common.base.mvp;


import com.rabtman.common.http.BaseCacheManager;
import com.rabtman.common.http.BaseServiceManager;

/**
 * Created by jess on 8/5/16 12:55
 * contact with jess.yan.effort@gmail.com
 */
public class BaseModel<S extends BaseServiceManager, C extends BaseCacheManager> implements IModel {

  protected S mServiceManager;//服务管理类,用于网络请求
  protected C mCacheManager;//缓存管理类,用于管理本地或者内存缓存

  public BaseModel(S serviceManager, C cacheManager) {
    this.mServiceManager = serviceManager;
    this.mCacheManager = cacheManager;
  }

  @Override
  public void onDestory() {
    if (mServiceManager != null) {
      mServiceManager = null;
    }
    if (mCacheManager != null) {
      mCacheManager = null;
    }
  }
}
