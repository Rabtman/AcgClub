package com.rabtman.acgclub.di.module;

import com.rabtman.acgclub.mvp.contract.AcgNewsDetailContract;
import com.rabtman.acgclub.mvp.model.AcgNewsDetailModel;
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
