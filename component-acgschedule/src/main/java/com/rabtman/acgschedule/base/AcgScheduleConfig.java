package com.rabtman.acgschedule.base;

import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import com.rabtman.acgschedule.base.constant.SystemConstant;
import com.rabtman.common.base.CommonApplicationLike.Lifecycle;
import com.rabtman.common.di.module.GlobeConfigModule.Builder;
import com.rabtman.common.integration.ConfigModule;
import com.rabtman.common.integration.IRepositoryManager;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import java.util.List;

/**
 * @author Rabtman
 */
public class AcgScheduleConfig implements ConfigModule {

  @Override
  public void applyOptions(Context context, Builder builder) {

  }

  @Override
  public void registerComponents(Context context, IRepositoryManager repositoryManager) {
    repositoryManager.injectRealmConfigs(
        new RealmConfiguration.Builder()
            .name(SystemConstant.DB_NAME)
            .schemaVersion(SystemConstant.DB_VERSION)
            .modules(Realm.getDefaultModule(), new AcgScheduleRealmModule())
            .build()
    );
  }

  @Override
  public void injectAppLifecycle(Context context, List<Lifecycle> lifecycles) {

  }

  @Override
  public void injectActivityLifecycle(Context context,
      List<ActivityLifecycleCallbacks> lifecycles) {

  }
}
