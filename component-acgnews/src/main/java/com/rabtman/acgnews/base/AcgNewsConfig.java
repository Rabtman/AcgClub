package com.rabtman.acgnews.base;

import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import com.rabtman.acgnews.api.AcgNewsService;
import com.rabtman.common.base.CommonApplicationLike.Lifecycle;
import com.rabtman.common.di.module.GlobeConfigModule.Builder;
import com.rabtman.common.integration.ConfigModule;
import com.rabtman.common.integration.IRepositoryManager;
import java.util.List;

/**
 * @author Rabtman
 */
public class AcgNewsConfig implements ConfigModule {

  @Override
  public void applyOptions(Context context, Builder builder) {

  }

  @Override
  public void registerComponents(Context context, IRepositoryManager repositoryManager) {
    repositoryManager.injectRetrofitService(AcgNewsService.class);
  }

  @Override
  public void injectAppLifecycle(Context context, List<Lifecycle> lifecycles) {

  }

  @Override
  public void injectActivityLifecycle(Context context,
      List<ActivityLifecycleCallbacks> lifecycles) {

  }
}
