package com.rabtman.acgclub.di.component;

import com.rabtman.acgclub.di.module.MainModule;
import com.rabtman.acgclub.mvp.ui.activity.MainActivity;
import com.rabtman.common.di.component.AppComponent;
import com.rabtman.common.di.scope.ActivityScope;
import dagger.Component;

/**
 * @author Rabtman
 */
@ActivityScope
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent {

  void inject(MainActivity activity);
}
