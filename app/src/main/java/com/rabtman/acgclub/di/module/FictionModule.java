package com.rabtman.acgclub.di.module;

import com.rabtman.acgclub.mvp.contract.FictionContract;
import com.rabtman.acgclub.mvp.model.FictionModel;
import com.rabtman.common.di.scope.FragmentScope;
import dagger.Module;
import dagger.Provides;

/**
 * @author Rabtman
 */
@Module
public class FictionModule {

  private FictionContract.View view;

  public FictionModule(FictionContract.View view) {
    this.view = view;
  }

  @FragmentScope
  @Provides
  FictionContract.View providerFictionView() {
    return this.view;
  }

  @FragmentScope
  @Provides
  FictionContract.Model providerFictionModel(FictionModel model) {
    return model;
  }
}
