package com.rabtman.acgclub.di.component;

import com.rabtman.acgclub.di.module.APicDetailModule;
import com.rabtman.acgclub.mvp.ui.activity.MoePicDetailActivity;
import com.rabtman.common.di.component.AppComponent;
import com.rabtman.common.di.scope.ActivityScope;
import dagger.Component;

/**
 * @author Rabtman
 */
@ActivityScope
@Component(modules = APicDetailModule.class, dependencies = AppComponent.class)
public interface APicDetailComponent {

  void inject(MoePicDetailActivity activity);
}
