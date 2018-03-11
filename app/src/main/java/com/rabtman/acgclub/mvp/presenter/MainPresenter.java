package com.rabtman.acgclub.mvp.presenter;

import com.rabtman.acgclub.mvp.contract.MainContract;
import com.rabtman.common.base.mvp.BasePresenter;
import com.rabtman.common.di.scope.ActivityScope;
import javax.inject.Inject;

/**
 * @author Rabtman
 */

@ActivityScope
public class MainPresenter extends
    BasePresenter<MainContract.Model, MainContract.View> {

  @Inject
  public MainPresenter(MainContract.Model model,
      MainContract.View rootView) {
    super(model, rootView);
  }
}
