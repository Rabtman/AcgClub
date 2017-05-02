package com.rabtman.acgclub.mvp.presenter;

import com.rabtman.acgclub.mvp.contract.ScheduleContract;
import com.rabtman.common.base.mvp.BasePresenter;
import com.rabtman.common.di.scope.FragmentScope;
import javax.inject.Inject;

/**
 * @author Rabtman
 */

@FragmentScope
public class SchedulePresenter extends
    BasePresenter<ScheduleContract.Model, ScheduleContract.View> {

  @Inject
  public SchedulePresenter(ScheduleContract.Model model, ScheduleContract.View rootView) {
    super(model, rootView);
  }
}
