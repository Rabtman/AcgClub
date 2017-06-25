package com.rabtman.acgclub.di.component;

import com.rabtman.acgclub.di.module.ScheduleMainModule;
import com.rabtman.acgclub.mvp.ui.fragment.ScheduleMainFragment;
import com.rabtman.common.di.component.AppComponent;
import com.rabtman.common.di.scope.FragmentScope;
import dagger.Component;

/**
 * @author Rabtman
 */
@FragmentScope
@Component(modules = ScheduleMainModule.class, dependencies = AppComponent.class)
public interface ScheduleMainComponent {

  void inject(ScheduleMainFragment fragment);
}
