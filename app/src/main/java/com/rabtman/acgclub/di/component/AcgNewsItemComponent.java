package com.rabtman.acgclub.di.component;

import com.rabtman.acgclub.di.module.AcgNewsItemModule;
import com.rabtman.acgclub.mvp.ui.fragment.AcgNewsItemFragment;
import com.rabtman.common.di.component.AppComponent;
import com.rabtman.common.di.scope.FragmentScope;
import dagger.Component;

/**
 * @author Rabtman
 */
@FragmentScope
@Component(modules = AcgNewsItemModule.class, dependencies = AppComponent.class)
public interface AcgNewsItemComponent {

  void inject(AcgNewsItemFragment fragment);
}
