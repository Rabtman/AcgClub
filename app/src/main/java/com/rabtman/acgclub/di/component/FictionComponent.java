package com.rabtman.acgclub.di.component;

import com.rabtman.acgclub.di.module.FictionModule;
import com.rabtman.acgclub.mvp.ui.fragment.FictionFragment;
import com.rabtman.common.di.component.AppComponent;
import com.rabtman.common.di.scope.FragmentScope;
import dagger.Component;

/**
 * @author Rabtman
 */
@FragmentScope
@Component(modules = FictionModule.class, dependencies = AppComponent.class)
public interface FictionComponent {

  void inject(FictionFragment fragment);
}
