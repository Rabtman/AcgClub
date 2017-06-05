package com.rabtman.acgclub.mvp.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import butterknife.BindView;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.base.constant.IntentConstant;
import com.rabtman.acgclub.di.component.DaggerScheduleMainComponent;
import com.rabtman.acgclub.di.module.ScheduleMainModule;
import com.rabtman.acgclub.mvp.contract.ScheduleMainContract;
import com.rabtman.acgclub.mvp.model.jsoup.AcgScheduleInfo;
import com.rabtman.acgclub.mvp.model.jsoup.ScheduleWeek;
import com.rabtman.acgclub.mvp.presenter.ScheduleMainPresenter;
import com.rabtman.acgclub.mvp.ui.adapter.ScheduleMainPageAdapter;
import com.rabtman.common.base.BaseFragment;
import com.rabtman.common.di.component.AppComponent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rabtman
 */

public class ScheduleMainFragment extends BaseFragment<ScheduleMainPresenter> implements
    ScheduleMainContract.View {

  @BindView(R.id.tab_schedule)
  TabLayout mTabLayout;
  @BindView(R.id.vp_schedule)
  ViewPager mViewPager;

  List<Fragment> fragments = new ArrayList<>();
  ScheduleMainPageAdapter mAdapter;

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_schedule_main;
  }

  @Override
  protected void initData() {
    mPresenter.getAcgSchedule();
  }

  @Override
  protected void setupFragmentComponent(AppComponent appComponent) {
    DaggerScheduleMainComponent
        .builder()
        .appComponent(appComponent)
        .scheduleMainModule(new ScheduleMainModule(this))
        .build()
        .inject(this);
  }

  @Override
  public void showAcgScheduleInfo(AcgScheduleInfo info) {
    for (ScheduleWeek scheduleWeek : info.getScheduleWeek()) {
      ScheduleItemFragment fragment = new ScheduleItemFragment();
      Bundle bundle = new Bundle();
      bundle.putParcelable(IntentConstant.SCHEDULE_WEEK, scheduleWeek);
      fragment.setArguments(bundle);
      fragments.add(fragment);
    }
    mAdapter = new ScheduleMainPageAdapter(getFragmentManager(), info.getWeekName(),
        fragments);
    mViewPager.setAdapter(mAdapter);
    mViewPager.setOffscreenPageLimit(1);
    mTabLayout.setupWithViewPager(mViewPager);
  }
}
