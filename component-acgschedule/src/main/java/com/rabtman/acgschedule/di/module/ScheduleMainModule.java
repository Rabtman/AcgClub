package com.rabtman.acgschedule.di.module;

import com.rabtman.acgschedule.mvp.contract.ScheduleMainContract;
import com.rabtman.acgschedule.mvp.model.ScheduleMainModel;
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
  ScheduleMainContract.View providerScheduleMainView() {
    return this.view;
  }

  @FragmentScope
  @Provides
  ScheduleMainContract.Model providerScheduleMainModel(ScheduleMainModel model) {
    return model;
  }
}
