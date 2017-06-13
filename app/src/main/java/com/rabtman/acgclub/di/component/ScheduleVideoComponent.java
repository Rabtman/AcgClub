package com.rabtman.acgclub.di.component;

import com.rabtman.acgclub.di.module.ScheduleVideoModule;
import com.rabtman.acgclub.mvp.ui.activity.ScheduleVideoActivity;
import com.rabtman.common.di.component.AppComponent;
import com.rabtman.common.di.scope.ActivityScope;
import dagger.Component;

/**
 * @author Rabtman
 */
@ActivityScope
@Component(modules = ScheduleVideoModule.class, dependencies = AppComponent.class)
public interface ScheduleVideoComponent {

  void inject(ScheduleVideoActivity activity);
}
