package com.rabtman.acgclub.di.module;

import com.rabtman.acgclub.mvp.contract.FictionSearchContract;
import com.rabtman.acgclub.mvp.model.FictionSearchModel;
import com.rabtman.common.di.scope.ActivityScope;
import dagger.Module;
import dagger.Provides;

/**
 * @author Rabtman
 */
@Module
public class FictionSearchModule {

  private FictionSearchContract.View view;

  public FictionSearchModule(FictionSearchContract.View view) {
    this.view = view;
  }

  @ActivityScope
  @Provides
  FictionSearchContract.View providerFictionSearchView() {
    return this.view;
  }

  @ActivityScope
  @Provides
  FictionSearchContract.Model providerFictionSearchModel(FictionSearchModel model) {
    return model;
  }
}
