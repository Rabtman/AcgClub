package com.rabtman.acgclub.mvp.presenter;

import com.rabtman.acgclub.mvp.contract.AcgNewsContract;
import com.rabtman.common.base.mvp.BasePresenter;
import com.rabtman.common.di.scope.FragmentScope;
import javax.inject.Inject;

/**
 * @author Rabtman
 */
@FragmentScope
public class AcgNewsItemPresenter extends
    BasePresenter<AcgNewsContract.Model, AcgNewsContract.View> {

  @Inject
  public AcgNewsItemPresenter(AcgNewsContract.Model model, AcgNewsContract.View rootView) {
    super(model, rootView);
  }

  public void getAcgNewsList() {

  }

}
