package com.rabtman.acgclub.base;

import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import com.rabtman.acgclub.api.AcgService;
import com.rabtman.acgclub.base.constant.HtmlConstant;
import com.rabtman.common.base.delegate.AppDelegate.Lifecycle;
import com.rabtman.common.di.module.GlobeConfigModule.Builder;
import com.rabtman.common.integration.ConfigModule;
import com.rabtman.common.integration.IRepositoryManager;
import java.util.List;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;

/**
 * @author Rabtman
 */

public class AppConfig implements ConfigModule {

  @Override
  public void applyOptions(Context context, Builder builder) {
    builder.baseurl(HtmlConstant.BASE_URL)
        .addInterceptor(new HttpLoggingInterceptor().setLevel(Level.BODY));
  }

  @Override
  public void registerComponents(Context context, IRepositoryManager repositoryManager) {
    repositoryManager.injectRetrofitService(AcgService.class);
  }

  @Override
  public void injectAppLifecycle(Context context, List<Lifecycle> lifecycles) {

  }

  @Override
  public void injectActivityLifecycle(Context context,
      List<ActivityLifecycleCallbacks> lifecycles) {
  }
}
