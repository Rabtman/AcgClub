package com.rabtman.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setupFragmentComponent(mActivity.mApplication.getAppComponent());
  }


  @Override
  public void onDestroyView() {
    if (mPresenter != null) {
      mPresenter.onDestroy();
    }
    super.onDestroyView();
  }


  @Override
  public void showLoading() {
    StyledDialog.buildMdLoading();
  }

  @Override
  public void hideLoading() {
    StyledDialog.dismissLoading();
  }

  @Override
  public void showError(String message) {
    Toasty.error(mContext, message, Toast.LENGTH_SHORT).show();
  }

  /**
   * 提供AppComponent(提供所有的单例对象)给子类，进行Component依赖
   */
  protected abstract void setupFragmentComponent(AppComponent appComponent);

  protected abstract int getLayoutId();

  protected abstract void initData();

}