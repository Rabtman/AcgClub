package com.rabtman.acgclub.di.module;

import com.rabtman.acgclub.mvp.contract.ScheduleVideoContract;
import com.rabtman.acgclub.mvp.model.ScheduleVideoModel;
import com.rabtman.common.di.scope.ActivityScope;
import dagger.Module;
import dagger.Provides;

/**
 * @author Rabtman
 */
@Module
public class ScheduleVideoModule {

  private ScheduleVideoContract.View view;

  public ScheduleVideoModule(ScheduleVideoContract.View view) {
    this.view = view;
  }

  @ActivityScope
  @Provides
  ScheduleVideoContract.View providerScheduleVideoView() {
    return this.view;
  }

  @ActivityScope
  @Provides
  ScheduleVideoContract.Model providerScheduleVideoModel(ScheduleVideoModel model) {
    return model;
  }
}
