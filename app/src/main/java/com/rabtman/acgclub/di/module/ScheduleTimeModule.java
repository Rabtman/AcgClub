package com.rabtman.acgclub.di.module;

import com.rabtman.acgclub.mvp.contract.ScheduleTimeContract;
import com.rabtman.acgclub.mvp.model.ScheduleModel;
import com.rabtman.common.di.scope.ActivityScope;
import dagger.Module;
import dagger.Provides;

/**
 * @author Rabtman
 */

@Module
public class ScheduleTimeModule {

  private ScheduleTimeContract.View view;

  public ScheduleTimeModule(ScheduleTimeContract.View view) {
    this.view = view;
  }

  @ActivityScope
  @Provides
  ScheduleTimeContract.View providerScheduleView() {
    return this.view;
  }

  @ActivityScope
  @Provides
  ScheduleTimeContract.Model providerScheduleModel(ScheduleModel model) {
    return model;
  }
}
