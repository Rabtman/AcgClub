package com.rabtman.acgclub.di.module;

import com.rabtman.acgclub.mvp.contract.ScheduleContract;
import com.rabtman.acgclub.mvp.model.ScheduleModel;
import com.rabtman.common.di.scope.FragmentScope;
import dagger.Module;
import dagger.Provides;

/**
 * @author Rabtman
 */

@Module
public class ScheduleModule {

  private ScheduleContract.View view;

  public ScheduleModule(ScheduleContract.View view) {
    this.view = view;
  }

  @FragmentScope
  @Provides
  ScheduleContract.View providerScheduleView() {
    return this.view;
  }

  @FragmentScope
  @Provides
  ScheduleContract.Model providerScheduleModel(ScheduleModel model) {
    return model;
  }
}
