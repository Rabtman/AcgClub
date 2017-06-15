package com.rabtman.acgclub.di.module;

import com.rabtman.acgclub.mvp.contract.APicDetailContract;
import com.rabtman.acgclub.mvp.model.APicDetailModel;
import com.rabtman.common.di.scope.ActivityScope;
import dagger.Module;
import dagger.Provides;

/**
 * @author Rabtman
 */
@Module
public class APicDetailModule {

  private APicDetailContract.View view;

  public APicDetailModule(APicDetailContract.View view) {
    this.view = view;
  }

  @ActivityScope
  @Provides
  APicDetailContract.View providerAPicDetailView() {
    return this.view;
  }

  @ActivityScope
  @Provides
  APicDetailContract.Model providerAPicDetailModel(APicDetailModel model) {
    return model;
  }
}
