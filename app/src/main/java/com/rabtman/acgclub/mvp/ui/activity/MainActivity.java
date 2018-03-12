package com.rabtman.acgclub.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import butterknife.BindView;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.di.component.DaggerMainComponent;
import com.rabtman.acgclub.di.module.MainModule;
import com.rabtman.acgclub.mvp.contract.MainContract;
import com.rabtman.acgclub.mvp.presenter.MainPresenter;
import com.rabtman.acgclub.service.UpdateAppService;
import com.rabtman.common.base.BaseActivity;
import com.rabtman.common.base.NullFragment;
import com.rabtman.common.di.component.AppComponent;
import com.rabtman.router.RouterConstants;
import com.rabtman.router.RouterUtils;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import java.util.HashMap;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author Rabtman
 */

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {


  @BindView(R.id.toolbar)
  Toolbar mToolBar;
  @BindView(R.id.view_navigation)
  BottomBar bottomBar;
  @BindView(R.id.main_content)
  FrameLayout mainContent;
  @BindView(R.id.nav_view)
  NavigationView navigationView;
  @BindView(R.id.drawer_layout)
  DrawerLayout drawerLayout;

  ActionBarDrawerToggle toggle;
  //需要加载的fragment
  HashMap<String, Class<? extends SupportFragment>> loadFragments = new HashMap<>();
  private String hideFragment = RouterConstants.PATH_ACGNEWS_MAIN;
  private String showFragment = RouterConstants.PATH_ACGNEWS_MAIN;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setTheme(R.style.AppTheme);
    super.onCreate(savedInstanceState);
  }

  @Override
  protected void setupActivityComponent(AppComponent appComponent) {
    DaggerMainComponent.builder()
        .appComponent(appComponent)
        .mainModule(new MainModule(this))
        .build()
        .inject(this);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.activity_main;
  }

  @Override
  protected void initData() {
    setToolBar(mToolBar, getString(R.string.nav_news));

    getAppVersionInfo();

    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    toggle = new ActionBarDrawerToggle(this, drawerLayout, mToolBar,
        R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawerLayout.addDrawerListener(toggle);
    toggle.syncState();
    if (loadFragments.get(RouterConstants.PATH_ACGNEWS_MAIN) == null) {
      loadMultipleRootFragment(R.id.main_content,
          0,
          getTargetFragment(RouterConstants.PATH_ACGNEWS_MAIN),
          getTargetFragment(RouterConstants.PATH_SCHEDULE_MAIN),
          getTargetFragment(RouterConstants.PATH_COMIC_OACG));
    }

    bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
      @Override
      public void onTabSelected(@IdRes int tabId) {
        String title = null;
        switch (tabId) {
          case R.id.nav_news:
            showFragment = RouterConstants.PATH_ACGNEWS_MAIN;
            title = getString(R.string.nav_news);
            break;
          case R.id.nav_schedule:
            showFragment = RouterConstants.PATH_SCHEDULE_MAIN;
            title = getString(R.string.nav_schedule);
            break;
          case R.id.nav_comic:
            showFragment = RouterConstants.PATH_COMIC_OACG;
            title = getString(R.string.nav_comic);
            break;
          /*case R.id.nav_picture:
            showFragment = R.id.nav_picture;
            title = getString(R.string.nav_picture);
            break;*/
          /*case R.id.nav_fiction:
            showFragment = R.id.nav_fiction;
            title = getString(R.string.nav_fiction);
            break;*/
          /*case R.id.nav_setting:
            showFragment = RouterConstants.PATH_SETTING;
            title = getString(R.string.nav_setting);
            break;*/
        }
        showHideFragment(getTargetFragment(showFragment),
            getTargetFragment(hideFragment));
        mToolBar.setTitle(title);
        hideFragment = showFragment;
      }
    });
    //navigationView.getMenu().findItem(R.id.nav_news).setChecked(true);
    navigationView.setNavigationItemSelectedListener(
        new OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
              case R.id.nav_collection:
                RouterUtils.getInstance()
                    .build(RouterConstants.PATH_COLLECTION)
                    .navigation();
                break;
              case R.id.nav_setting:
                RouterUtils.getInstance()
                    .build(RouterConstants.PATH_SETTING)
                    .navigation();
                break;
            }
            //mToolBar.setTitle(item.getTitle());
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
          }
        });
  }

  /**
   * 根据目标路径找到对应的页面
   */
  private SupportFragment getTargetFragment(String path) {
    if (loadFragments.get(path) != null && findFragment(loadFragments.get(path)) != null) {
      return findFragment(loadFragments.get(path));
    } else {
      SupportFragment fragment = (SupportFragment) (RouterUtils.getInstance()
          .build(path)
          .navigation());
      if (fragment == null) {
        fragment = new NullFragment();
      }
      loadFragments.put(path, fragment.getClass());
      return fragment;
    }
  }


  @Override
  public void onBackPressedSupport() {
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressedSupport();
    }
  }

  //检查app更新
  public void getAppVersionInfo() {
    startService(new Intent(this, UpdateAppService.class));
  }
}
