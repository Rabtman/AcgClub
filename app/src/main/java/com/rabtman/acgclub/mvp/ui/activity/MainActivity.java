package com.rabtman.acgclub.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import butterknife.BindView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.jaeger.library.StatusBarUtil;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.di.component.DaggerMainComponent;
import com.rabtman.acgclub.di.module.MainModule;
import com.rabtman.acgclub.mvp.contract.MainContract;
import com.rabtman.acgclub.mvp.presenter.MainPresenter;
import com.rabtman.acgclub.service.UpdateAppService;
import com.rabtman.common.base.BaseActivity;
import com.rabtman.common.base.NullFragment;
import com.rabtman.common.di.component.AppComponent;
import com.rabtman.common.utils.KeyboardUtils;
import com.rabtman.common.utils.constant.StatusBarConstants;
import com.rabtman.router.RouterConstants;
import com.rabtman.router.RouterUtils;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import java.util.HashMap;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_ACGCLUB_MAIN)
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
  private String hideFragment = RouterConstants.PATH_SCHEDULE_MAIN;
  private String showFragment = RouterConstants.PATH_SCHEDULE_MAIN;
  //关闭软件提醒
  private long exitTime;

  @Override
  protected void onDestroy() {
    KeyboardUtils.fixSoftInputLeaks(this);
    super.onDestroy();
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
  protected void setStatusBar() {
    StatusBarUtil.setColorForDrawerLayout(
        mContext,
        drawerLayout,
        mAppComponent.statusBarAttr().get(StatusBarConstants.COLOR),
        mAppComponent.statusBarAttr().get(StatusBarConstants.ALPHA)
    );
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
    if (loadFragments.get(RouterConstants.PATH_SCHEDULE_MAIN) == null) {
      loadMultipleRootFragment(R.id.main_content,
          0,
          getTargetFragment(RouterConstants.PATH_SCHEDULE_MAIN),
          //getTargetFragment(RouterConstants.PATH_COMIC_OACG),
          getTargetFragment(RouterConstants.PATH_PICTURE_MAIN),
          getTargetFragment(RouterConstants.PATH_ACGNEWS_MAIN));
    }

    bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
      @Override
      public void onTabSelected(@IdRes int tabId) {
        String title = null;
        switch (tabId) {
          case R.id.nav_schedule:
            showFragment = RouterConstants.PATH_SCHEDULE_MAIN;
            title = getString(R.string.nav_schedule);
            break;
          /*case R.id.nav_comic:
            showFragment = RouterConstants.PATH_COMIC_OACG;
            title = getString(R.string.nav_comic);
            break;*/
          case R.id.nav_news:
            showFragment = RouterConstants.PATH_ACGNEWS_MAIN;
            title = getString(R.string.nav_news);
            break;
          case R.id.nav_picture:
            showFragment = RouterConstants.PATH_PICTURE_MAIN;
            title = getString(R.string.nav_picture);
            break;
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
      exitApp();
    }
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK) {
      if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
        drawerLayout.closeDrawer(GravityCompat.START);
      } else {
        exitApp();
      }
      return false;
    }
    return super.onKeyDown(keyCode, event);
  }

  public void exitApp() {
    if ((System.currentTimeMillis() - exitTime) > 2000) {
      showMsg("再按一次退出软件");
      exitTime = System.currentTimeMillis();
    } else {
      finish();
    }
  }

  //检查app更新
  public void getAppVersionInfo() {
    startService(new Intent(this, UpdateAppService.class));
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {
    if (ev.getAction() == MotionEvent.ACTION_DOWN) {
      View v = getCurrentFocus();
      if (isShouldHideKeyboard(v, ev)) {
        InputMethodManager imm =
            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(),
            InputMethodManager.HIDE_NOT_ALWAYS
        );
      }
    }
    return super.dispatchTouchEvent(ev);
  }

  // Return whether touch the view.
  private boolean isShouldHideKeyboard(View v, MotionEvent event) {
    if (v != null && (v instanceof EditText)) {
      int[] l = {0, 0};
      v.getLocationInWindow(l);
      int left = l[0],
          top = l[1],
          bottom = top + v.getHeight(),
          right = left + v.getWidth();
      return !(event.getX() > left && event.getX() < right
          && event.getY() > top && event.getY() < bottom);
    }
    return false;
  }
}
