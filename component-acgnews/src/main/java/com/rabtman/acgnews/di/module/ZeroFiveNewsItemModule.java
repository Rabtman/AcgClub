package com.rabtman.acgnews.di.module;

import com.rabtman.acgnews.mvp.contract.ZeroFiveNewsContract;
import com.rabtman.acgnews.mvp.model.ZeroFiveNewsModel;
import com.rabtman.common.di.scope.FragmentScope;
import dagger.Module;
import dagger.Provides;

/**
 * @author Rabtman
 */
@Module
public class ZeroFiveNewsItemModule {

  private ZeroFiveNewsContract.View view;

  public ZeroFiveNewsItemModule(ZeroFiveNewsContract.View view) {
    this.view = view;
  }

  @FragmentScope
  @Provides
  ZeroFiveNewsContract.View providerZeroFiveNewsView() {
    return this.view;
  }

  @FragmentScope
  @Provides
  ZeroFiveNewsContract.Model providerZeroFiveNewsModel(ZeroFiveNewsModel model) {
    return model;
  }
}
