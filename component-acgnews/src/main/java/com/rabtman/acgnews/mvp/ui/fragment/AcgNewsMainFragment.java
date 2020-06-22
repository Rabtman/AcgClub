package com.rabtman.acgnews.mvp.ui.fragment;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.tabs.TabLayout;
import com.rabtman.acgnews.R;
import com.rabtman.acgnews.R2;
import com.rabtman.acgnews.mvp.ui.adapter.AcgNewsMainPageAdapter;
import com.rabtman.common.base.SimpleFragment;
import com.rabtman.router.RouterConstants;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_ACGNEWS_MAIN)
public class AcgNewsMainFragment extends SimpleFragment {

  @BindView(R2.id.tab_news)
  TabLayout mTabLayout;
  @BindView(R2.id.vp_news)
  ViewPager mViewPager;

  List<Fragment> fragments = new ArrayList<>();
  AcgNewsMainPageAdapter mAdapter;

  @Override
  protected int getLayoutId() {
    return R.layout.acgnews_fragment_news_main;
  }

  @Override
  protected void initData() {

    ZeroFiveNewsFragment zeroFiveFragment = new ZeroFiveNewsFragment();
    fragments.add(zeroFiveFragment);
    ISHNewsFragment ishNewsFragment = new ISHNewsFragment();
    fragments.add(ishNewsFragment);

    mAdapter = new AcgNewsMainPageAdapter(getFragmentManager(), fragments);
    mViewPager.setAdapter(mAdapter);
    mViewPager.setOffscreenPageLimit(1);
    mTabLayout.setupWithViewPager(mViewPager);
  }
}
