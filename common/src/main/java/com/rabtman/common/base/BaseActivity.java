package com.rabtman.common.base;

import android.app.Dialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import com.hss01248.dialog.StyledDialog;
import com.rabtman.common.base.mvp.BasePresenter;
import com.rabtman.common.base.mvp.IView;
import com.rabtman.common.di.component.AppComponent;
import es.dmoral.toasty.Toasty;
import javax.inject.Inject;


public abstract class BaseActivity<P extends BasePresenter> extends SimpleActivity implements
    IView {

  @Inject
  protected P mPresenter;
  private Dialog mLoadingDialog;

  @Override
  protected void onViewCreated() {
    super.onViewCreated();
    setupActivityComponent(mApplication.getAppComponent());//依赖注入
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
  protected void onDestroy() {
    super.onDestroy();
    if (mPresenter != null) {
      mPresenter.onDestroy();
    }
    this.mPresenter = null;
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
  public void showError(String message) {
    Toasty.error(mContext, message, Toast.LENGTH_SHORT).show();
  }

  /**
   * 提供AppComponent(提供所有的单例对象)给子类，进行Component依赖
   */
  protected abstract void setupActivityComponent(AppComponent appComponent);

  protected abstract int getLayout();

  protected abstract void initData();

}