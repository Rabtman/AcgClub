package com.rabtman.acgclub.di.component;

import com.rabtman.acgclub.di.module.ScheduleTimeModule;
import com.rabtman.acgclub.mvp.ui.fragment.ScheduleTimeFragment;
import com.rabtman.common.di.component.AppComponent;
import com.rabtman.common.di.scope.FragmentScope;
import dagger.Component;

/**
 * @author Rabtman
 */
@FragmentScope
@Component(modules = ScheduleTimeModule.class, dependencies = AppComponent.class)
public interface ScheduleTimeComponent {

  void inject(ScheduleTimeFragment fragment);
}
