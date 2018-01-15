package com.rabtman.acgnews.mvp.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import butterknife.BindView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.rabtman.acgnews.R;
import com.rabtman.acgnews.base.constant.IntentConstant;
import com.rabtman.acgnews.base.constant.SystemConstant;
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
    return R.layout.fragment_news_main;
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
