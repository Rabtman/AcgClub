package com.rabtman.acgschedule.di.component;

import com.rabtman.acgschedule.di.module.ScheduleVideoModule;
import com.rabtman.acgschedule.mvp.ui.activity.ScheduleVideoActivity;
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
