package com.rabtman.acgschedule.mvp.ui.activity

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import butterknife.BindView
import com.alibaba.android.arouter.facade.annotation.Route
import com.rabtman.acgschedule.R
import com.rabtman.acgschedule.R2
import com.rabtman.acgschedule.base.constant.IntentConstant
import com.rabtman.acgschedule.di.component.DaggerScheduleVideoComponent
import com.rabtman.acgschedule.di.module.ScheduleVideoModule
import com.rabtman.acgschedule.mvp.contract.ScheduleVideoContract
import com.rabtman.acgschedule.mvp.presenter.ScheduleVideoPresenter
import com.rabtman.business.base.widget.X5VideoWebView
import com.rabtman.business.router.RouterConstants
import com.rabtman.common.base.BaseActivity
import com.rabtman.common.di.component.AppComponent
import com.tencent.smtt.sdk.QbSdk

/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_SCHEDULE_VIDEO)
class ScheduleVideoActivity : BaseActivity<ScheduleVideoPresenter>(), ScheduleVideoContract.View {
    @BindView(R2.id.webview)
    lateinit var webView: X5VideoWebView

    @BindView(R2.id.progress_video)
    lateinit var progressVideo: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
    }

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerScheduleVideoComponent.builder()
                .appComponent(appComponent)
                .scheduleVideoModule(ScheduleVideoModule(this))
                .build()
                .inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_browser
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            val decorView = window.decorView
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

    //去掉状态栏着色
    override fun setStatusBar() {}
    override fun initData() {
        if (!QbSdk.isTbsCoreInited()) {
            // preinit只需要调用一次，如果已经完成了初始化，那么就直接构造view
            QbSdk.preInit(this@ScheduleVideoActivity, null) // 设置X5初始化完成的回调接口
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
        mPresenter.getScheduleVideo(intent.getStringExtra(IntentConstant.SCHEDULE_EPISODE_URL))
    }

    override fun onBackPressedSupport() {
        webView.takeIf { it.canGoBack() }?.goBack() ?: run {
            super.onBackPressedSupport()
        }
    }

    override fun onDestroy() {
        webView.destroy()
        super.onDestroy()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        try {
            super.onConfigurationChanged(newConfig)
            /*if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            } else if (resources.configuration.orientation
                    == Configuration.ORIENTATION_PORTRAIT) {
            }*/
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun hideLoading() {
        progressVideo.visibility = View.GONE
    }

    override fun showScheduleVideo(videoUrl: String?, isVideo: Boolean) {
        if (isVideo) {
            /*Bundle extraData = new Bundle();
      extraData.putInt("screenMode", 102);
      TbsVideo.openVideo(this, videoUrl, extraData);
      finish();*/
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        webView.loadUrl(videoUrl)
    }
}