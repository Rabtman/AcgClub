package com.rabtman.common.di.module;

import android.app.Application;
import com.google.gson.Gson;
import com.rabtman.common.integration.AppManager;
import com.rabtman.common.integration.IRepositoryManager;
import com.rabtman.common.integration.RepositoryManager;
import com.tbruyelle.rxpermissions2.RxPermissions;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;


@Module
public class AppModule {

  private Application mApplication;

  public AppModule(Application application) {
    this.mApplication = application;
  }

  @Singleton
  @Provides
  public Application provideApplication() {
    return mApplication;
  }

  @Singleton
  @Provides
  public Gson provideGson() {
    return new Gson();
  }

  @Singleton
  @Provides
  public IRepositoryManager provideRepositoryManager(RepositoryManager repositoryManager) {
    return repositoryManager;
  }

  @Provides
  public RxPermissions provideRxPermissions(AppManager appManager) {
    return new RxPermissions(appManager.getCurrentActivity());
  }
}
