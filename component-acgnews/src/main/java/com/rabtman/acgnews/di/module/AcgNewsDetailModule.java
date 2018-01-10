package com.rabtman.acgnews.di.module;

import com.rabtman.acgnews.mvp.contract.AcgNewsDetailContract;
import com.rabtman.acgnews.mvp.model.AcgNewsDetailModel;
import com.rabtman.common.di.scope.ActivityScope;
import dagger.Module;
import dagger.Provides;

/**
 * @author Rabtman
 */
@Module
public class AcgNewsDetailModule {

  private AcgNewsDetailContract.View view;

  public AcgNewsDetailModule(AcgNewsDetailContract.View view) {
    this.view = view;
  }

  @ActivityScope
  @Provides
  AcgNewsDetailContract.View providerAcgNewsDetailView() {
    return this.view;
  }

  @ActivityScope
  @Provides
  AcgNewsDetailContract.Model providerAcgNewsDetailModel(AcgNewsDetailModel model) {
    return model;
  }
}
