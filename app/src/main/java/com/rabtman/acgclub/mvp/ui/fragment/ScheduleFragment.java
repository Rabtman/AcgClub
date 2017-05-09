package com.rabtman.acgclub.mvp.ui.fragment;

import android.os.Bundle;
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

  public static ScheduleFragment newInstance() {
    Bundle args = new Bundle();
    ScheduleFragment fragment = new ScheduleFragment();
    fragment.setArguments(args);
    return fragment;
  }

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
