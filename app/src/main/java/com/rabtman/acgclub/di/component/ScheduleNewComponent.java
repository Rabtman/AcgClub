package com.rabtman.acgclub.di.component;

import com.rabtman.acgclub.di.module.ScheduleNewModule;
import com.rabtman.acgclub.mvp.ui.activity.ScheduleNewActivity;
import com.rabtman.common.di.component.AppComponent;
import com.rabtman.common.di.scope.ActivityScope;
import dagger.Component;

/**
 * @author Rabtman
 */
@ActivityScope
@Component(modules = ScheduleNewModule.class, dependencies = AppComponent.class)
public interface ScheduleNewComponent {

  void inject(ScheduleNewActivity activity);
}
