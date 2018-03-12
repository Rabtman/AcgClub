package com.rabtman.acgclub.mvp.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.mvp.ui.adapter.AcgCollectionPageAdapter;
import com.rabtman.common.base.NullFragment;
import com.rabtman.common.base.SimpleActivity;
import com.rabtman.common.base.SimpleFragment;
import com.rabtman.router.RouterConstants;
import com.rabtman.router.RouterUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_COLLECTION)
public class AcgCollectionActivity extends SimpleActivity {

  @BindView(R.id.toolbar)
  Toolbar mToolbar;
  @BindView(R.id.tab_news)
  TabLayout mTabLayout;
  @BindView(R.id.vp_news)
  ViewPager mViewPager;

  List<Fragment> fragments = new ArrayList<>();
  AcgCollectionPageAdapter mAdapter;

  @Override
  protected int getLayoutId() {
    return R.layout.activity_collection;
  }

  @Override
  protected void initData() {
    setToolBar(mToolbar, getString(R.string.nav_collection));

    SimpleFragment scheduleCollection = (SimpleFragment) RouterUtils.getInstance()
        .build(RouterConstants.PATH_COLLECTION_SCHEDULE)
        .navigation();
    if (scheduleCollection == null) {
      scheduleCollection = new NullFragment();
    }
    fragments.add(scheduleCollection);

    mAdapter = new AcgCollectionPageAdapter(getSupportFragmentManager(), fragments);
    mViewPager.setAdapter(mAdapter);
    mViewPager.setOffscreenPageLimit(1);
    mTabLayout.setupWithViewPager(mViewPager);
  }
}
