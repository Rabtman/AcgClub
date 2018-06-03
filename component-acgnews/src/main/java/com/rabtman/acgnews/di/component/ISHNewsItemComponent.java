package com.rabtman.acgnews.di.component;

import com.rabtman.acgnews.di.module.ISHNewsItemModule;
import com.rabtman.common.di.component.AppComponent;
import com.rabtman.common.di.scope.FragmentScope;
import dagger.Component;

/**
 * @author Rabtman
 */
@FragmentScope
@Component(modules = ISHNewsItemModule.class, dependencies = AppComponent.class)
public interface ISHNewsItemComponent {

}
