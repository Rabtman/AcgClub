package com.rabtman.acgnews.di.module;

import com.rabtman.acgnews.mvp.contract.ISHNewsContract;
import com.rabtman.acgnews.mvp.model.ISHNewsModel;
import com.rabtman.common.di.scope.FragmentScope;
import dagger.Module;
import dagger.Provides;

/**
 * @author Rabtman
 */
@Module
public class ISHNewsItemModule {

  private ISHNewsContract.View view;

  public ISHNewsItemModule(ISHNewsContract.View view) {
    this.view = view;
  }

  @FragmentScope
  @Provides
  ISHNewsContract.View providerISHNewsView() {
    return this.view;
  }

  @FragmentScope
  @Provides
  ISHNewsContract.Model providerISHNewsModel(ISHNewsModel model) {
    return model;
  }
}
