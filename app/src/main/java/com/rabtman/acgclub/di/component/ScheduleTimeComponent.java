package com.rabtman.acgclub.di.component;

import com.rabtman.acgclub.di.module.ScheduleTimeModule;
import com.rabtman.acgclub.mvp.ui.activity.ScheduleTimeActivity;
import com.rabtman.common.di.component.AppComponent;
import com.rabtman.common.di.scope.ActivityScope;
import dagger.Component;

/**
 * @author Rabtman
 */
@ActivityScope
@Component(modules = ScheduleTimeModule.class, dependencies = AppComponent.class)
public interface ScheduleTimeComponent {

  void inject(ScheduleTimeActivity activity);
}
