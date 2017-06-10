package com.rabtman.acgclub.di.component;

import com.rabtman.acgclub.di.module.AcgNewsDetailModule;
import com.rabtman.acgclub.mvp.ui.activity.AcgInfoDetailActivity;
import com.rabtman.common.di.component.AppComponent;
import com.rabtman.common.di.scope.ActivityScope;
import dagger.Component;

/**
 * @author Rabtman
 */
@ActivityScope
@Component(modules = AcgNewsDetailModule.class, dependencies = AppComponent.class)
public interface AcgNewsDetailComponent {

  void inject(AcgInfoDetailActivity activity);
}
