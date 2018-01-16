package com.rabtman.acgclub.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import butterknife.BindView;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.di.component.DaggerMainComponent;
import com.rabtman.acgclub.di.module.MainModule;
import com.rabtman.acgclub.mvp.contract.MainContract;
import com.rabtman.acgclub.mvp.model.entity.VersionInfo;
import com.rabtman.acgclub.mvp.presenter.MainPresenter;
import com.rabtman.common.base.BaseActivity;
import com.rabtman.common.di.component.AppComponent;
import com.rabtman.common.utils.IntentUtils;
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
 /* @BindView(R.id.nav_view)
  NavigationView navigationView;
  @BindView(R.id.drawer_layout)
  DrawerLayout drawerLayout;*/

  //ActionBarDrawerToggle toggle;
  //需要加载的fragment
  HashMap<String, Class<? extends SupportFragment>> loadFragments = new HashMap<>();
  //AcgNewsMainFragment acgNewsMainFragment;
  //ScheduleMainFragment scheduleMainFragment;
  //AcgPicMainFragment acgPicMainFragment;
  //FictionFragment fictionFragment;
  //SettingFragment settingFragment;

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
    return R.layout.layout_main;
  }

  @Override
  protected void initData() {
    setToolBar(mToolBar, getString(R.string.nav_news));

    getAppVersionInfo(false);

    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    /*toggle = new ActionBarDrawerToggle(this, drawerLayout, mToolBar,
        R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawerLayout.addDrawerListener(toggle);
    toggle.syncState();*/
    if (loadFragments.get(RouterConstants.PATH_ACGNEWS_MAIN) == null) {
      loadMultipleRootFragment(R.id.main_content,
          0,
          getTargetFragment(RouterConstants.PATH_ACGNEWS_MAIN),
          getTargetFragment(RouterConstants.PATH_SCHEDULE_MAIN),
          getTargetFragment(RouterConstants.PATH_SETTING));
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
          /*case R.id.nav_picture:
            showFragment = R.id.nav_picture;
            title = getString(R.string.nav_picture);
            break;*/
          /*case R.id.nav_fiction:
            showFragment = R.id.nav_fiction;
            title = getString(R.string.nav_fiction);
            break;*/
          case R.id.nav_setting:
            showFragment = RouterConstants.PATH_SETTING;
            title = getString(R.string.nav_setting);
            break;
        }
        showHideFragment(getTargetFragment(showFragment),
            getTargetFragment(hideFragment));
        mToolBar.setTitle(title);
        hideFragment = showFragment;
      }
    });
    /*navigationView.getMenu().findItem(R.id.nav_news).setChecked(true);
    navigationView.setNavigationItemSelectedListener(
        new OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
              case R.id.nav_news:
                showFragment = R.id.nav_news;
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

  /**
   * 根据目标路径找到对应的页面
   */
  private SupportFragment getTargetFragment(String path) {
    if (loadFragments.get(path) != null && findFragment(loadFragments.get(path)) != null) {
      return findFragment(loadFragments.get(path));
    } else {
      Object f = ARouter.getInstance().build(RouterConstants.PATH_ACGNEWS_DETAIL).navigation();
      SupportFragment fragment = (SupportFragment) (RouterUtils.getInstance()
          .build(path)
          .navigation());
      loadFragments.put(path, fragment.getClass());
      return fragment;
    }
  }


  @Override
  public void onBackPressedSupport() {
    /*if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawer(GravityCompat.START);
    } else {*/
    super.onBackPressedSupport();
    //}
  }

  //检查app更新
  public void getAppVersionInfo(boolean isManual) {
    mPresenter.getVersionInfo(isManual);
  }

  @Override
  public void showUpdateDialog(final VersionInfo versionInfo) {
    StyledDialog.buildMdAlert("版本更新", versionInfo.getDesc(), new MyDialogListener() {
      @Override
      public void onFirst() {
        //跳转更新APP版本
        if (versionInfo.getAppLink().startsWith("http")) {
          IntentUtils.go2Browser(getBaseContext(), versionInfo.getAppLink());
        } else if (versionInfo.getAppLink().startsWith("market")) {
          IntentUtils.go2Market(getBaseContext(), versionInfo.getAppLink());
        }
      }

      @Override
      public void onSecond() {
      }
    }).setBtnText("现在升级", "下次再说").show();
  }
}
