package com.rabtman.acgclub.di.module;

import com.rabtman.acgclub.mvp.contract.MainContract;
import com.rabtman.acgclub.mvp.model.MainModel;
import com.rabtman.common.di.scope.ActivityScope;
import dagger.Module;
import dagger.Provides;

/**
 * @author Rabtman
 */
@Module
public class MainModule {

  private MainContract.View view;

  public MainModule(MainContract.View view) {
    this.view = view;
  }

  @ActivityScope
  @Provides
  MainContract.View providerMainView() {
    return this.view;
  }

  @ActivityScope
  @Provides
  MainContract.Model providerMainModel(MainModel model) {
    return model;
  }
}
