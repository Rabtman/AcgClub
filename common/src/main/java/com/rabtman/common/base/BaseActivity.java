package com.rabtman.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.rabtman.common.base.mvp.BasePresenter;
import com.rabtman.common.base.mvp.IView;
import com.rabtman.common.di.component.AppComponent;
import javax.inject.Inject;


public abstract class BaseActivity<P extends BasePresenter> extends SimpleActivity implements
    IView {

  @Inject
  protected P mPresenter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayout());

    setupActivityComponent(mApplication.getAppComponent());//依赖注入
    initData();
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

  /**
   * 提供AppComponent(提供所有的单例对象)给子类，进行Component依赖
   */
  protected abstract void setupActivityComponent(AppComponent appComponent);

  protected abstract int getLayout();

  protected abstract void initData();

}