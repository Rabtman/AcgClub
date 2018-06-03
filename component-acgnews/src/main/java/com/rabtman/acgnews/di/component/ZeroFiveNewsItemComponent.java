package com.rabtman.acgnews.di.component;

import com.rabtman.acgnews.di.module.ZeroFiveNewsItemModule;
import com.rabtman.acgnews.mvp.ui.fragment.ZeroFiveNewsFragment;
import com.rabtman.common.di.component.AppComponent;
import com.rabtman.common.di.scope.FragmentScope;
import dagger.Component;

/**
 * @author Rabtman
 */
@FragmentScope
@Component(modules = ZeroFiveNewsItemModule.class, dependencies = AppComponent.class)
public interface ZeroFiveNewsItemComponent {

  void inject(ZeroFiveNewsFragment fragment);
}
