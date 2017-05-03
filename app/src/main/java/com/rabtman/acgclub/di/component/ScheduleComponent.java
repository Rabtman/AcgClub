package com.rabtman.acgclub.di.component;

import com.rabtman.acgclub.di.module.ScheduleModule;
import com.rabtman.acgclub.mvp.ui.fragment.ScheduleFragment;
import com.rabtman.common.di.component.AppComponent;
import com.rabtman.common.di.scope.FragmentScope;
import dagger.Component;

/**
 * @author Rabtman
 */

@FragmentScope
@Component(modules = ScheduleModule.class, dependencies = AppComponent.class)
public interface ScheduleComponent {

  void inject(ScheduleFragment fragment);
}
