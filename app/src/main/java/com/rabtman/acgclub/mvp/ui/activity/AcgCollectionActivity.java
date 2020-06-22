package com.rabtman.acgclub.mvp.ui.activity;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import butterknife.BindView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.tabs.TabLayout;
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

    //番剧收藏
    SimpleFragment scheduleCollection = (SimpleFragment) RouterUtils.getInstance()
        .build(RouterConstants.PATH_SCHEDULE_COLLECTION)
        .navigation();
    if (scheduleCollection == null) {
      scheduleCollection = new NullFragment();
    }
    //漫画收藏
    SimpleFragment comicCollection = (SimpleFragment) RouterUtils.getInstance()
        .build(RouterConstants.PATH_COMIC_COLLECTION)
        .navigation();
    if (comicCollection == null) {
      comicCollection = new NullFragment();
    }

    fragments.add(scheduleCollection);
    fragments.add(comicCollection);

    mAdapter = new AcgCollectionPageAdapter(getSupportFragmentManager(), fragments);
    mViewPager.setAdapter(mAdapter);
    mViewPager.setOffscreenPageLimit(1);
    mTabLayout.setupWithViewPager(mViewPager);
    mViewPager.addOnPageChangeListener(new OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override
      public void onPageSelected(int position) {

      }

      @Override
      public void onPageScrollStateChanged(int state) {

      }
    });
  }
}
