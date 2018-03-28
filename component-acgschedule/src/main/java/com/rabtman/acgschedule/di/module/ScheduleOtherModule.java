package com.rabtman.acgschedule.di.module;

import com.rabtman.acgschedule.mvp.contract.ScheduleOtherContract;
import com.rabtman.acgschedule.mvp.model.ScheduleOtherModel;
import com.rabtman.common.di.scope.ActivityScope;
import dagger.Module;
import dagger.Provides;

/**
 * @author Rabtman
 */
@Module
public class ScheduleOtherModule {

  private ScheduleOtherContract.View view;

  public ScheduleOtherModule(ScheduleOtherContract.View view) {
    this.view = view;
  }

  @ActivityScope
  @Provides
  ScheduleOtherContract.View providerScheduleOtherView() {
    return this.view;
  }

  @ActivityScope
  @Provides
  ScheduleOtherContract.Model providerScheduleOtherModel(ScheduleOtherModel model) {
    return model;
  }
}
