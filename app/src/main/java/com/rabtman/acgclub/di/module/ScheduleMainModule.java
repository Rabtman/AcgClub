package com.rabtman.acgclub.di.module;

import com.rabtman.acgclub.mvp.contract.ScheduleMainContract;
import com.rabtman.acgclub.mvp.model.ScheduleModel;
import com.rabtman.common.di.scope.FragmentScope;
import dagger.Module;
import dagger.Provides;

/**
 * @author Rabtman
 */

@Module
public class ScheduleMainModule {

  private ScheduleMainContract.View view;

  public ScheduleMainModule(ScheduleMainContract.View view) {
    this.view = view;
  }

  @FragmentScope
  @Provides
  ScheduleMainContract.View providerScheduleView() {
    return this.view;
  }

  @FragmentScope
  @Provides
  ScheduleMainContract.Model providerScheduleModel(ScheduleModel model) {
    return model;
  }
}
