package com.rabtman.acgclub.mvp.ui.activity;

import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import butterknife.BindView;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.base.constant.IntentConstant;
import com.rabtman.acgclub.base.view.X5VideoWebView;
import com.rabtman.acgclub.di.component.DaggerScheduleVideoComponent;
import com.rabtman.acgclub.di.module.ScheduleVideoModule;
import com.rabtman.acgclub.mvp.contract.ScheduleVideoContract;
import com.rabtman.acgclub.mvp.presenter.ScheduleVideoPresenter;
import com.rabtman.common.base.BaseActivity;
import com.rabtman.common.di.component.AppComponent;
import com.tencent.smtt.sdk.QbSdk;

/**
 * @author Rabtman
 */
public class ScheduleVideoActivity extends BaseActivity<ScheduleVideoPresenter> implements
    ScheduleVideoContract.View {

  @BindView(R.id.webview)
  X5VideoWebView webView;
  @BindView(R.id.progress_video)
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
    getWindow().setFormat(PixelFormat.TRANSLUCENT);
    try {
      if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 11) {
        getWindow()
            .setFlags(
                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
      }
    } catch (Exception e) {
    }
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
    return R.layout.activity_browser;
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

  private void hideSystemNavigationBar() {
    if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
      View view = this.getWindow().getDecorView();
      view.setSystemUiVisibility(View.GONE);
    } else if (Build.VERSION.SDK_INT >= 19) {
      View decorView = getWindow().getDecorView();
      int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
          | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
      decorView.setSystemUiVisibility(uiOptions);
    }
  }

  @Override
  public void showScheduleVideo(String videoLink, String videoUrl) {
    if (TextUtils.isEmpty(videoLink)) {
      webView.loadUrl(videoUrl);
    } else {
      webView.loadUrl(videoLink);
    }
  }
}
