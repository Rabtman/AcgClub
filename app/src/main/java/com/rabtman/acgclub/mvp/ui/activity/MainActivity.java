package com.rabtman.acgclub.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import butterknife.BindView;
import com.rabtman.acgclub.R;
import com.rabtman.common.base.SimpleActivity;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author Rabtman
 */

public class MainActivity extends SimpleActivity {


  @BindView(R.id.toolbar)
  Toolbar mToolBar;
  @BindView(R.id.view_navigation)
  BottomNavigationView viewNavigation;
  @BindView(R.id.main_content)
  FrameLayout mainContent;
 /* @BindView(R.id.nav_view)
  NavigationView navigationView;
  @BindView(R.id.drawer_layout)
  DrawerLayout drawerLayout;*/

  //ActionBarDrawerToggle toggle;
  HealthNewsMainFragment healthNewsMainFragment;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }

  @Override
  protected int getLayout() {
    return R.layout.layout_main;
  }

  @Override
  protected void initData() {
    setToolBar(mToolBar, getString(R.string.nav_health));
    /*toggle = new ActionBarDrawerToggle(this, drawerLayout, mToolBar,
        R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawerLayout.addDrawerListener(toggle);
    toggle.syncState();*/
    //fragment
    healthNewsMainFragment = new HealthNewsMainFragment();
    loadMultipleRootFragment(R.id.main_content, 0, healthNewsMainFragment);
    navigationView.getMenu().findItem(R.id.nav_main).setChecked(true);

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
        });
  }

  private SupportFragment getTargetFragment(int tag) {
    switch (tag) {
      case R.id.nav_main:
        return healthNewsMainFragment;
    }
    return healthNewsMainFragment;
  }

  @Override
  public void onBackPressedSupport() {
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressedSupport();
    }
  }

}
