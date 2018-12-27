package com.rabtman.common.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.hss01248.dialog.StyledDialog;
import com.kingja.loadsir.callback.Callback.OnReloadListener;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.rabtman.common.base.mvp.IView;
import com.rabtman.common.base.widget.loadsir.EmptyCallback;
import com.rabtman.common.base.widget.loadsir.LoadingCallback;
import com.rabtman.common.base.widget.loadsir.RetryCallback;
import com.rabtman.common.di.component.AppComponent;
import es.dmoral.toasty.Toasty;
import me.yokeyword.fragmentation.SupportFragment;

public abstract class SimpleFragment extends SupportFragment implements
    IView {

  protected View mView;
  protected SimpleActivity mActivity;
  protected Context mContext;
  protected boolean isInited = false;
  protected boolean isVisible = false;
  protected LoadService mLoadService;
  private SwipeRefreshLayout mSwipeRefreshLayout;
  private Dialog mLoadingDialog;
  private Unbinder mUnBinder;

  @Override
  public void onAttach(Context context) {
    mActivity = (SimpleActivity) context;
    mContext = context;
    super.onAttach(context);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    mView = inflater.inflate(getLayoutId(), null);
    if (useLoadSir()) {
      mLoadService = LoadSir.getDefault().register(registerTarget(), new OnReloadListener() {
        @Override
        public void onReload(View v) {
          showPageLoading();
          onPageRetry(v);
        }
      });
      return mLoadService.getLoadLayout();
    } else {
      return mView;
    }
  }

  /**
   * loadsir注册目标
   */
  protected Object registerTarget() {
    return mView;
  }

  /**
   * 是否使用loadsir，默认不使用
   */
  protected boolean useLoadSir() {
    return false;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mUnBinder = ButterKnife.bind(this, view);
  }

  @Override
  public void onLazyInitView(@Nullable Bundle savedInstanceState) {
    super.onLazyInitView(savedInstanceState);
    isInited = true;
    initData();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    mUnBinder.unbind();
  }

  @Override
  public void onSupportVisible() {
    super.onSupportVisible();
    isVisible = true;
  }

  @Override
  public void onSupportInvisible() {
    super.onSupportInvisible();
    isVisible = false;
  }

  protected AppComponent getAppComponent() {
    return mActivity.mAppComponent;
  }

  protected void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
    mSwipeRefreshLayout = swipeRefreshLayout;
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
    if (mSwipeRefreshLayout != null) {
      if (!mSwipeRefreshLayout.isRefreshing()) {
        mSwipeRefreshLayout.setRefreshing(true);
      }
    } else {
      mLoadingDialog = StyledDialog.buildMdLoading().show();
    }
  }

  @Override
  public void hideLoading() {
    if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
      mSwipeRefreshLayout.setRefreshing(false);
    }
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
    Toasty.info(mContext, message, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void showError(int stringId) {
    showError(getString(stringId));
  }

  @Override
  public void showError(String message) {
    hideLoading();
    Toasty.error(mContext, message, Toast.LENGTH_SHORT).show();
  }

  protected abstract int getLayoutId();

  protected abstract void initData();
}
