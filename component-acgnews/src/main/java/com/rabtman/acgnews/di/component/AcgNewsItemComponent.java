package com.rabtman.acgnews.di.component;

import com.rabtman.acgnews.di.module.AcgNewsItemModule;
import com.rabtman.acgnews.mvp.ui.fragment.ZeroFiveNewsFragment;
import com.rabtman.common.di.component.AppComponent;
import com.rabtman.common.di.scope.FragmentScope;
import dagger.Component;

/**
 * @author Rabtman
 */
@FragmentScope
@Component(modules = AcgNewsItemModule.class, dependencies = AppComponent.class)
public interface AcgNewsItemComponent {

  void inject(ZeroFiveNewsFragment fragment);
}
