package com.rabtman.acgclub.mvp.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import butterknife.BindView;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.base.constant.IntentConstant;
import com.rabtman.acgclub.base.constant.SystemConstant;
import com.rabtman.acgclub.mvp.ui.adapter.AcgNewsMainPageAdapter;
import com.rabtman.common.base.SimpleFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rabtman
 */
public class AcgNewsMainFragment extends SimpleFragment {

  @BindView(R.id.tab_layout)
  TabLayout mTabLayout;
  @BindView(R.id.view_pager)
  ViewPager mViewPager;

  List<Fragment> fragments = new ArrayList<>();
  AcgNewsMainPageAdapter mAdapter;

  @Override
  protected int getLayoutId() {
    return R.layout.common_tab_vp;
  }

  @Override
  protected void initData() {
    for (String column : SystemConstant.ACG_NEWS_TITLE) {
      AcgNewsItemFragment fragment = new AcgNewsItemFragment();
      Bundle bundle = new Bundle();
      bundle.putString(IntentConstant.ACG_NEWS_TITLE, column);
      fragment.setArguments(bundle);
      fragments.add(fragment);
    }
    mAdapter = new AcgNewsMainPageAdapter(getFragmentManager(), fragments);
    mViewPager.setAdapter(mAdapter);
    mViewPager.setOffscreenPageLimit(1);
    mTabLayout.setupWithViewPager(mViewPager);
  }
}
