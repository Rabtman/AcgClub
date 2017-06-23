package com.rabtman.acgclub.mvp.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import butterknife.BindView;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.mvp.ui.adapter.ScheduleMainPageAdapter;
import com.rabtman.common.base.SimpleFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rabtman
 */

public class ScheduleMainFragment extends SimpleFragment {

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
    //ScheduleTimeFragment fragment = new ScheduleTimeFragment();
    //fragments.add(fragment);

    mAdapter = new ScheduleMainPageAdapter(getFragmentManager(), fragments);
    mViewPager.setAdapter(mAdapter);
    mViewPager.setOffscreenPageLimit(1);
    mTabLayout.setupWithViewPager(mViewPager);
  }

}
