package com.rabtman.common.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Toast;
import com.hss01248.dialog.StyledDialog;
import com.rabtman.common.base.mvp.BasePresenter;
import com.rabtman.common.base.mvp.IView;
import com.rabtman.common.di.component.AppComponent;
import es.dmoral.toasty.Toasty;
import javax.inject.Inject;


public abstract class BaseFragment<T extends BasePresenter> extends SimpleFragment implements
    IView {

  @Inject
  protected T mPresenter;
  protected View mView;
  private SwipeRefreshLayout mSwipeRefreshLayout;
  private Dialog mLoadingDialog;
  private AppComponent mAppComponent;

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mAppComponent = mActivity.mApplication.getAppComponent();
    setupFragmentComponent(mAppComponent);
  }


  @Override
  public void onDestroyView() {
    if (mPresenter != null) {
      mPresenter.onDestroy();
    }
    super.onDestroyView();
  }

  protected void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
    mSwipeRefreshLayout = swipeRefreshLayout;
  }

  @Override
  public void showLoading() {
    if (mSwipeRefreshLayout != null && !mSwipeRefreshLayout.isRefreshing()) {
      mSwipeRefreshLayout.setRefreshing(true);
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
  public void showError(String message) {
    Toasty.error(mContext, message, Toast.LENGTH_SHORT).show();
  }

  public AppComponent getAppComponent() {
    return mAppComponent;
  }

  /**
   * 提供AppComponent(提供所有的单例对象)给子类，进行Component依赖
   */
  protected abstract void setupFragmentComponent(AppComponent appComponent);

  protected abstract int getLayoutId();

  protected abstract void initData();

}