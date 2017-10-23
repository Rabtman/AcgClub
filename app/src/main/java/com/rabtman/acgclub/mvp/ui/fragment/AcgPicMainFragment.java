package com.rabtman.acgclub.mvp.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import butterknife.BindView;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.mvp.ui.adapter.AcgPicMainPageAdapter;
import com.rabtman.common.base.SimpleFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rabtman
 */
public class AcgPicMainFragment extends SimpleFragment {

  @BindView(R.id.tab_pic)
  TabLayout mTabLayout;
  @BindView(R.id.vp_pic)
  ViewPager mViewPager;

  List<Fragment> fragments = new ArrayList<>();
  AcgPicMainPageAdapter mAdapter;

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_pic_main;
  }

  @Override
  protected void initData() {
    APicFragment aPicFragment = new APicFragment();
    fragments.add(aPicFragment);

    mAdapter = new AcgPicMainPageAdapter(getFragmentManager(), fragments);
    mViewPager.setAdapter(mAdapter);
    mViewPager.setOffscreenPageLimit(1);
    mTabLayout.setupWithViewPager(mViewPager);
  }
}
