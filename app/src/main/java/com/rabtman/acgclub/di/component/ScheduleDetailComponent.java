package com.rabtman.acgclub.di.component;

import com.rabtman.acgclub.di.module.ScheduleDetailModule;
import com.rabtman.acgclub.mvp.ui.activity.ScheduleDetailActivity;
import com.rabtman.common.di.component.AppComponent;
import com.rabtman.common.di.scope.ActivityScope;
import dagger.Component;

/**
 * @author Rabtman
 */
@ActivityScope
@Component(modules = ScheduleDetailModule.class, dependencies = AppComponent.class)
public interface ScheduleDetailComponent {

  void inject(ScheduleDetailActivity activity);
}
