package com.rabtman.acgnews.di.component;

import com.rabtman.acgnews.di.module.ZeroFiveNewsDetailModule;
import com.rabtman.acgnews.mvp.ui.activity.ZeroFiveNewsDetailActivity;
import com.rabtman.common.di.component.AppComponent;
import com.rabtman.common.di.scope.ActivityScope;
import dagger.Component;

/**
 * @author Rabtman
 */
@ActivityScope
@Component(modules = ZeroFiveNewsDetailModule.class, dependencies = AppComponent.class)
public interface ZeroFiveNewsDetailComponent {

  void inject(ZeroFiveNewsDetailActivity activity);
}
