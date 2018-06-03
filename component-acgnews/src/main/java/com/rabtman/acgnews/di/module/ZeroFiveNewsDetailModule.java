package com.rabtman.acgnews.di.module;

import com.rabtman.acgnews.mvp.contract.ZeroFiveNewsDetailContract;
import com.rabtman.acgnews.mvp.model.ZeroFiveNewsDetailModel;
import com.rabtman.common.di.scope.ActivityScope;
import dagger.Module;
import dagger.Provides;

/**
 * @author Rabtman
 */
@Module
public class ZeroFiveNewsDetailModule {

  private ZeroFiveNewsDetailContract.View view;

  public ZeroFiveNewsDetailModule(ZeroFiveNewsDetailContract.View view) {
    this.view = view;
  }

  @ActivityScope
  @Provides
  ZeroFiveNewsDetailContract.View providerZeroFiveNewsDetailView() {
    return this.view;
  }

  @ActivityScope
  @Provides
  ZeroFiveNewsDetailContract.Model providerZeroFiveNewsDetailModel(ZeroFiveNewsDetailModel model) {
    return model;
  }
}
