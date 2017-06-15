package com.rabtman.acgclub.di.component;

import com.rabtman.acgclub.di.module.AcgPicItemModule;
import com.rabtman.acgclub.mvp.ui.fragment.CartoonPicFragment;
import com.rabtman.common.di.component.AppComponent;
import com.rabtman.common.di.scope.FragmentScope;
import dagger.Component;

/**
 * @author Rabtman
 */
@FragmentScope
@Component(modules = AcgPicItemModule.class, dependencies = AppComponent.class)
public interface AcgPicComponent {

  void inject(CartoonPicFragment fragment);
}
