package com.rabtman.acgclub.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import butterknife.BindView;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.mvp.ui.fragment.AcgNewsMainFragment;
import com.rabtman.acgclub.mvp.ui.fragment.AcgPicMainFragment;
import com.rabtman.acgclub.mvp.ui.fragment.ScheduleMainFragment;
import com.rabtman.common.base.SimpleActivity;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author Rabtman
 */

public class MainActivity extends SimpleActivity {


  @BindView(R.id.toolbar)
  Toolbar mToolBar;
  @BindView(R.id.view_navigation)
  BottomBar bottomBar;
  @BindView(R.id.main_content)
  FrameLayout mainContent;
 /* @BindView(R.id.nav_view)
  NavigationView navigationView;
  @BindView(R.id.drawer_layout)
  DrawerLayout drawerLayout;*/

  //ActionBarDrawerToggle toggle;
  AcgNewsMainFragment acgNewsMainFragment;
  ScheduleMainFragment scheduleMainFragment;
  AcgPicMainFragment acgPicMainFragment;

  private int hideFragment = R.id.nav_main;
  private int showFragment = R.id.nav_main;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }

  @Override
  protected int getLayoutId() {
    return R.layout.layout_main;
  }

  @Override
  protected void initData() {
    setToolBar(mToolBar, getString(R.string.nav_main));
    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    /*toggle = new ActionBarDrawerToggle(this, drawerLayout, mToolBar,
        R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawerLayout.addDrawerListener(toggle);
    toggle.syncState();*/
    //fragment
    acgNewsMainFragment = new AcgNewsMainFragment();
    scheduleMainFragment = new ScheduleMainFragment();
    acgPicMainFragment = new AcgPicMainFragment();
    loadMultipleRootFragment(R.id.main_content, 0, acgNewsMainFragment, scheduleMainFragment,
        acgPicMainFragment);

    bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
      @Override
      public void onTabSelected(@IdRes int tabId) {
        String title = null;
        switch (tabId) {
          case R.id.nav_main:
            showFragment = R.id.nav_main;
            title = getString(R.string.nav_main);
            break;
          case R.id.nav_schedule:
            showFragment = R.id.nav_schedule;
            title = getString(R.string.nav_schedule);
            break;
          case R.id.nav_picture:
            showFragment = R.id.nav_picture;
            title = getString(R.string.nav_picture);
            break;
        }
        showHideFragment(getTargetFragment(showFragment),
            getTargetFragment(hideFragment));
        mToolBar.setTitle(title);
        hideFragment = showFragment;
      }
    });
    /*navigationView.getMenu().findItem(R.id.nav_main).setChecked(true);
    navigationView.setNavigationItemSelectedListener(
        new OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
              case R.id.nav_main:
                showFragment = R.id.nav_main;
                searchView.setVisible(false);
                break;
            }
            showHideFragment(getTargetFragment(showFragment),
                getTargetFragment(hideFragment));
            mToolBar.setTitle(item.getTitle());
            hideFragment = showFragment;
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
          }
        });*/
  }

  private SupportFragment getTargetFragment(int tag) {
    switch (tag) {
      case R.id.nav_main:
        return acgNewsMainFragment;
      case R.id.nav_schedule:
        return scheduleMainFragment;
      case R.id.nav_picture:
        return acgPicMainFragment;
    }
    return acgNewsMainFragment;
  }

  @Override
  public void onBackPressedSupport() {
    /*if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawer(GravityCompat.START);
    } else {*/
    super.onBackPressedSupport();
    //}
  }

}
