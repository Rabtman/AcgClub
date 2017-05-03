package com.rabtman.acgclub.mvp.ui.fragment;

import com.rabtman.acgclub.di.component.DaggerScheduleComponent;
import com.rabtman.acgclub.di.module.ScheduleModule;
import com.rabtman.acgclub.mvp.contract.ScheduleContract;
import com.rabtman.acgclub.mvp.presenter.SchedulePresenter;
import com.rabtman.common.base.BaseFragment;
import com.rabtman.common.di.component.AppComponent;

/**
 * @author Rabtman
 */

public class ScheduleFragment extends BaseFragment<SchedulePresenter> implements
    ScheduleContract.View {

  @Override
  protected int getLayoutId() {
    return 0;
  }

  @Override
  protected void initData() {

  }

  @Override
  protected void setupFragmentComponent(AppComponent appComponent) {
    DaggerScheduleComponent
        .builder()
        .appComponent(appComponent)
        .scheduleModule(new ScheduleModule(this))
        .build()
        .inject(this);
  }

}
