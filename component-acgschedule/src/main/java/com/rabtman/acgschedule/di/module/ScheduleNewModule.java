package com.rabtman.acgschedule.di.module;

import com.rabtman.acgschedule.mvp.contract.ScheduleNewContract;
import com.rabtman.acgschedule.mvp.model.ScheduleNewModel;
import com.rabtman.common.di.scope.ActivityScope;
import dagger.Module;
import dagger.Provides;

/**
 * @author Rabtman
 */
@Module
public class ScheduleNewModule {

  private ScheduleNewContract.View view;

  public ScheduleNewModule(ScheduleNewContract.View view) {
    this.view = view;
  }

  @ActivityScope
  @Provides
  ScheduleNewContract.View providerScheduleNewView() {
    return this.view;
  }

  @ActivityScope
  @Provides
  ScheduleNewContract.Model providerScheduleNewModel(ScheduleNewModel model) {
    return model;
  }
}
