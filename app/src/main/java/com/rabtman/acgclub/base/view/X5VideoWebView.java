package com.rabtman.acgclub.base.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.utils.WebViewJavaScriptFunction;
import com.rabtman.common.utils.LogUtil;
import com.tencent.smtt.export.external.interfaces.ConsoleMessage;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient.CustomViewCallback;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.sdk.WebSettings.ZoomDensity;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * @author Rabtman
 */
public class X5VideoWebView extends WebView {

  private Context mContext;
  private onChromeConsoleListener listener;

  @SuppressLint("SetJavaScriptEnabled")
  public X5VideoWebView(Context context, AttributeSet attributeSet) {
    super(context, attributeSet);
    this.mContext = context;
    initWebViewSettings();
    this.getView().setClickable(true);
  }

  public onChromeConsoleListener getOnChromeConsoleListener() {
    return listener;
  }

  public void setOnChromeConsoleListener(onChromeConsoleListener listener) {
    this.listener = listener;
  }

  private void initWebViewSettings() {
    this.setWebViewClient(new WebViewClient() {
      /**
       * 防止加载网页时调起系统浏览器
       */
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
      }
    });
    this.setWebChromeClient(new WebChromeClient() {

      View myVideoView;
      View myNormalView;
      CustomViewCallback callback;

      @Override
      public boolean onJsConfirm(WebView arg0, String arg1, String arg2,
          JsResult arg3) {
        return super.onJsConfirm(arg0, arg1, arg2, arg3);
      }

      @Override
      public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        if (listener != null) {
          listener.onConsoleMessage(consoleMessage);
        }
        return super.onConsoleMessage(consoleMessage);
      }

      /**
       * 全屏播放配置
       */
      @Override
      public void onShowCustomView(View view,
          CustomViewCallback customViewCallback) {
        FrameLayout normalView = (FrameLayout) findViewById(R.id.frame_browser);
        ViewGroup viewGroup = (ViewGroup) normalView.getParent();
        viewGroup.removeView(normalView);
        viewGroup.addView(view);
        myVideoView = view;
        myNormalView = normalView;
        callback = customViewCallback;
      }

      @Override
      public void onHideCustomView() {
        if (callback != null) {
          callback.onCustomViewHidden();
          callback = null;
        }
        if (myVideoView != null) {
          ViewGroup viewGroup = (ViewGroup) myVideoView.getParent();
          viewGroup.removeView(myVideoView);
          viewGroup.addView(myNormalView);
        }
      }

      @Override
      public boolean onJsAlert(WebView arg0, String arg1, String arg2,
          JsResult arg3) {
        /**
         * 这里写入你自定义的window alert
         */
        return super.onJsAlert(null, arg1, arg2, arg3);
      }

    });

    WebSettings webSetting = this.getSettings();
    //sdk大于等于21混合使用http,https需要做如下设置
    if (Build.VERSION.SDK_INT >= 21) {
      webSetting.setMixedContentMode(0);
    }
    webSetting.setJavaScriptEnabled(true);
    webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
    webSetting.setAllowFileAccess(true);
    webSetting.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
    webSetting.setDefaultZoom(ZoomDensity.CLOSE);
    webSetting.setSupportZoom(true);
    webSetting.setDisplayZoomControls(false);
    webSetting.setBuiltInZoomControls(true);
    //webSetting.setUseWideViewPort(true);
    webSetting.setSupportMultipleWindows(false);
    // webSetting.setLoadWithOverviewMode(true);
    webSetting.setAppCacheEnabled(true);
    // webSetting.setDatabaseEnabled(true);
    webSetting.setDomStorageEnabled(true);
    webSetting.setGeolocationEnabled(true);
    webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
    // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
    webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
    // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
    webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);

    this.getView().setOverScrollMode(View.OVER_SCROLL_ALWAYS);
    this.addJavascriptInterface(new WebViewJavaScriptFunction() {

      @Override
      public void onJsFunctionCalled(String tag) {
        LogUtil.d("onJsFunctionCalled:" + tag);
      }

      @JavascriptInterface
      public void onX5ButtonClicked() {
        enableX5FullscreenFunc();
      }

      @JavascriptInterface
      public void onCustomButtonClicked() {
        disableX5FullscreenFunc();
      }

      @JavascriptInterface
      public void onLiteWndButtonClicked() {
        enableLiteWndFunc();
      }

      @JavascriptInterface
      public void onPageVideoClicked() {
        enablePageVideoFunc();
      }
    }, "Android");
  }

  // 向webview发出信息
  private void enableX5FullscreenFunc() {

    if (this.getX5WebViewExtension() != null) {
      Toast.makeText(mContext, "开启X5全屏播放模式", Toast.LENGTH_LONG).show();
      Bundle data = new Bundle();

      data.putBoolean("standardFullScreen", false);// true表示标准全屏，false表示X5全屏；不设置默认false，

      data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，

      data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

      this.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
          data);
    }
  }

  private void disableX5FullscreenFunc() {
    if (this.getX5WebViewExtension() != null) {
      Toast.makeText(mContext, "恢复webkit初始状态", Toast.LENGTH_LONG).show();
      Bundle data = new Bundle();

      data.putBoolean("standardFullScreen",
          true);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，

      data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，

      data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

      this.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
          data);
    }
  }

  private void enableLiteWndFunc() {
    if (this.getX5WebViewExtension() != null) {
      Toast.makeText(mContext, "开启小窗模式", Toast.LENGTH_LONG).show();
      Bundle data = new Bundle();

      data.putBoolean("standardFullScreen",
          false);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，

      data.putBoolean("supportLiteWnd", true);// false：关闭小窗；true：开启小窗；不设置默认true，

      data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

      this.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
          data);
    }
  }

  private void enablePageVideoFunc() {
    if (this.getX5WebViewExtension() != null) {
      Toast.makeText(mContext, "页面内全屏播放模式", Toast.LENGTH_LONG).show();
      Bundle data = new Bundle();

      data.putBoolean("standardFullScreen",
          false);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，

      data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，

      data.putInt("DefaultVideoScreen", 1);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

      this.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
          data);
    }
  }

  public interface onChromeConsoleListener {

    void onConsoleMessage(ConsoleMessage consoleMessage);
  }
}
