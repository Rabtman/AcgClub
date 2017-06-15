package com.rabtman.acgclub.di.module;

import com.rabtman.acgclub.mvp.contract.AcgPicContract;
import com.rabtman.acgclub.mvp.model.AcgPicModel;
import com.rabtman.common.di.scope.FragmentScope;
import dagger.Module;
import dagger.Provides;

/**
 * @author Rabtman
 */
@Module
public class AcgPicItemModule {

  private AcgPicContract.View view;

  public AcgPicItemModule(AcgPicContract.View view) {
    this.view = view;
  }

  @FragmentScope
  @Provides
  AcgPicContract.View providerAcgPicView() {
    return this.view;
  }

  @FragmentScope
  @Provides
  AcgPicContract.Model providerAcgPicModel(AcgPicModel model) {
    return model;
  }
}
