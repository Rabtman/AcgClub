package com.rabtman.acgnews.di.component;

import com.rabtman.acgnews.di.module.ISHNewsDetailModule;
import com.rabtman.acgnews.mvp.ui.activity.ISHNewsDetailActivity;
import com.rabtman.common.di.component.AppComponent;
import com.rabtman.common.di.scope.ActivityScope;
import dagger.Component;

/**
 * @author Rabtman
 */
@ActivityScope
@Component(modules = ISHNewsDetailModule.class, dependencies = AppComponent.class)
public interface ISHNewsDetailComponent {

  void inject(ISHNewsDetailActivity activity);
}
