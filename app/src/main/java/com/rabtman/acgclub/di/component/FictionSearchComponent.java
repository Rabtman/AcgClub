package com.rabtman.acgclub.di.component;

import com.rabtman.acgclub.di.module.FictionSearchModule;
import com.rabtman.acgclub.mvp.ui.activity.FictionSearchActivity;
import com.rabtman.common.di.component.AppComponent;
import com.rabtman.common.di.scope.ActivityScope;
import dagger.Component;

/**
 * @author Rabtman
 */
@ActivityScope
@Component(modules = FictionSearchModule.class, dependencies = AppComponent.class)
public interface FictionSearchComponent {

  void inject(FictionSearchActivity activity);
}
