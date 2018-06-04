package com.rabtman.acgnews.di.module;

import com.rabtman.acgnews.mvp.contract.ISHNewsDetailContract;
import com.rabtman.acgnews.mvp.model.ISHNewsDetailModel;
import com.rabtman.common.di.scope.ActivityScope;
import dagger.Module;
import dagger.Provides;

/**
 * @author Rabtman
 */
@Module
public class ISHNewsDetailModule {

  private ISHNewsDetailContract.View view;

  public ISHNewsDetailModule(ISHNewsDetailContract.View view) {
    this.view = view;
  }

  @ActivityScope
  @Provides
  ISHNewsDetailContract.View providerISHNewsDetailView() {
    return this.view;
  }

  @ActivityScope
  @Provides
  ISHNewsDetailContract.Model providerISHNewsDetailModel(ISHNewsDetailModel model) {
    return model;
  }
}
