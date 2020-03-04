package com.rabtman.acgschedule.mvp.ui.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import butterknife.BindView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.rabtman.acgschedule.R;
import com.rabtman.acgschedule.R2;
import com.rabtman.acgschedule.base.constant.IntentConstant;
import com.rabtman.acgschedule.di.component.DaggerScheduleVideoComponent;
import com.rabtman.acgschedule.di.module.ScheduleVideoModule;
import com.rabtman.acgschedule.mvp.contract.ScheduleVideoContract;
import com.rabtman.acgschedule.mvp.presenter.ScheduleVideoPresenter;
import com.rabtman.common.base.BaseActivity;
import com.rabtman.common.base.widget.X5VideoWebView;
import com.rabtman.common.di.component.AppComponent;
import com.rabtman.router.RouterConstants;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsVideo;

/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_SCHEDULE_VIDEO)
public class ScheduleVideoActivity extends BaseActivity<ScheduleVideoPresenter> implements
    ScheduleVideoContract.View {

  @BindView(R2.id.webview)
  X5VideoWebView webView;
  @BindView(R2.id.progress_video)
  ProgressBar progressVideo;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    super.onCreate(savedInstanceState);
  }

  @Override
  protected void setupActivityComponent(AppComponent appComponent) {
    DaggerScheduleVideoComponent.builder()
        .appComponent(appComponent)
        .scheduleVideoModule(new ScheduleVideoModule(this))
        .build()
        .inject(this);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.activity_browser;
  }

  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    if (hasFocus) {
      View decorView = getWindow().getDecorView();
      decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
          | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
          | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
          | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
          | View.SYSTEM_UI_FLAG_FULLSCREEN
          | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
  }

  //去掉状态栏着色
  @Override
  protected void setStatusBar() {
  }

  @Override
  protected void initData() {
    if (!QbSdk.isTbsCoreInited()) {
      // preinit只需要调用一次，如果已经完成了初始化，那么就直接构造view
      QbSdk.preInit(ScheduleVideoActivity.this, null);// 设置X5初始化完成的回调接口
    }
    //监听webview控制台日志
    /*webView.setOnChromeConsoleListener(new onChromeConsoleListener() {
      @Override
      public void onConsoleMessage(ConsoleMessage consoleMessage) {
        if (consoleMessage.messageLevel() == MessageLevel.ERROR) {
          showError(getString(R.string.msg_error_load_video));
          //onBackPressedSupport();
        }
      }
    });*/
    mPresenter.getScheduleVideo(getIntent().getStringExtra(IntentConstant.SCHEDULE_EPISODE_URL));
  }

  @Override
  public void onBackPressedSupport() {
    if (webView.canGoBack()) {
      webView.goBack();
    } else {
      super.onBackPressedSupport();
    }
  }

  @Override
  protected void onDestroy() {
    webView.destroy();
    super.onDestroy();
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    try {
      super.onConfigurationChanged(newConfig);
      if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
      } else if (getResources().getConfiguration().orientation
          == Configuration.ORIENTATION_PORTRAIT) {
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void hideLoading() {
    progressVideo.setVisibility(View.GONE);
  }

  @Override
  public void showScheduleVideo(String videoUrl, boolean isVideo) {
    if (isVideo) {
      Bundle extraData = new Bundle();
      extraData.putInt("screenMode", 102);
      TbsVideo.openVideo(this, videoUrl, extraData);
      finish();
    } else {
      webView.loadUrl(videoUrl);
    }
  }
}
