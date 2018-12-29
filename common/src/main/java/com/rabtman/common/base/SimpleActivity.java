package com.rabtman.common.base;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.hss01248.dialog.StyledDialog;
import com.jaeger.library.StatusBarUtil;
import com.kingja.loadsir.callback.Callback.OnReloadListener;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.rabtman.common.base.mvp.IView;
import com.rabtman.common.base.widget.loadsir.EmptyCallback;
import com.rabtman.common.base.widget.loadsir.LoadingCallback;
import com.rabtman.common.base.widget.loadsir.RetryCallback;
import com.rabtman.common.di.component.AppComponent;
import com.rabtman.common.utils.ToastUtil;
import com.rabtman.common.utils.Utils;
import com.rabtman.common.utils.constant.StatusBarConstants;
import com.umeng.analytics.MobclickAgent;
import me.yokeyword.fragmentation.SupportActivity;

public abstract class SimpleActivity extends SupportActivity implements
    IView {

  protected AppComponent mAppComponent;
  protected Activity mContext;
  protected LoadService mLoadService;
  private Dialog mLoadingDialog;
  private Unbinder mUnBinder;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutId());
    mUnBinder = ButterKnife.bind(this);
    mContext = this;
    mAppComponent = Utils.getAppComponent();
    setStatusBar();
    if (useLoadSir()) {
      mLoadService = LoadSir.getDefault().register(registerTarget(), new OnReloadListener() {
        @Override
        public void onReload(View v) {
          showPageLoading();
          onPageRetry(v);
        }
      });
    }
    onViewCreated();
    initData();
  }

  /**
   * loadsir注册目标，默认为自身acitivity
   */
  protected Object registerTarget() {
    return this;
  }

  /**
   * 是否使用loadsir，默认不使用
   */
  protected boolean useLoadSir() {
    return false;
  }

  @Override
  protected void onResume() {
    super.onResume();
    MobclickAgent.onResume(this);
  }

  @Override
  protected void onPause() {
    super.onPause();
    MobclickAgent.onPause(this);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (mUnBinder != Unbinder.EMPTY) {
      mUnBinder.unbind();
    }
    this.mUnBinder = null;
    this.mAppComponent = null;
  }

  protected void setToolBar(Toolbar toolbar, String title) {
    toolbar.setTitle(title);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        onBackPressedSupport();
      }
    });
  }

  @Override
  public void showPageLoading() {
    if (mLoadService != null) {
      mLoadService.showCallback(LoadingCallback.class);
    }
  }

  @Override
  public void showPageEmpty() {
    if (mLoadService != null) {
      mLoadService.showCallback(EmptyCallback.class);
    }
  }

  @Override
  public void showPageError() {
    if (mLoadService != null) {
      mLoadService.showCallback(RetryCallback.class);
    }
  }

  @Override
  public void showPageContent() {
    if (mLoadService != null) {
      mLoadService.showSuccess();
    }
  }

  /**
   * 页面重试
   */
  protected void onPageRetry(View v) {

  }

  @Override
  public void showLoading() {
    mLoadingDialog = StyledDialog.buildMdLoading().show();
  }

  @Override
  public void hideLoading() {
    if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
      StyledDialog.dismiss(mLoadingDialog);
    }
  }

  @Override
  public void showMsg(int stringId) {
    showMsg(getString(stringId));
  }

  @Override
  public void showMsg(String message) {
    ToastUtil.show(mContext, message);
  }

  @Override
  public void showError(int stringId) {
    showError(getString(stringId));
  }

  @Override
  public void showError(String message) {
    hideLoading();
    ToastUtil.show(mContext, message);
  }

  protected void setStatusBar() {
    StatusBarUtil.setColor(mContext,
        ContextCompat.getColor(mContext,
            mAppComponent.statusBarAttr().get(StatusBarConstants.COLOR)),
        mAppComponent.statusBarAttr().get(StatusBarConstants.ALPHA)
    );
  }

  protected void onViewCreated() {

  }

  protected abstract int getLayoutId();

  protected abstract void initData();
}
