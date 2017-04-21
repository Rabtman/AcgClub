package com.rabtman.common.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.rabtman.common.base.mvp.BasePresenter;
import javax.inject.Inject;
import me.yokeyword.fragmentation.SupportActivity;


public abstract class BaseActivity<P extends BasePresenter> extends SupportActivity {

  public static final String IS_NOT_ADD_ACTIVITY_LIST = "is_add_activity_list";//是否加入到activity的list，管理
  protected BaseApplication mApplication;
  @Inject
  protected P mPresenter;
  protected Activity mContext;
  private Unbinder mUnBinder;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayout());
    //如果intent包含了此字段,并且为true说明不加入到list
    // 默认为false,如果不需要管理(比如不需要在退出所有activity(killAll)时，退出此activity就在intent加此字段为true)
    boolean isNotAdd = false;
    if (getIntent() != null) {
      isNotAdd = getIntent().getBooleanExtra(IS_NOT_ADD_ACTIVITY_LIST, false);
    }

    if (!isNotAdd) {
      mApplication.getAppManager().addActivity(this);
    }
    mUnBinder = ButterKnife.bind(this);
    mContext = this;
    initInject();
    initEventAndData();
  }

  @Override
  protected void onResume() {
    super.onResume();
    mApplication.getAppManager().setCurrentActivity(this);
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (mApplication.getAppManager().getCurrentActivity() == this) {
      mApplication.getAppManager().setCurrentActivity(null);
    }
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
    mApplication.getAppManager().removeActivity(this);
    if (mPresenter != null) {
      mPresenter.onDestroy();
    }
    if (mUnBinder != Unbinder.EMPTY) {
      mUnBinder.unbind();
    }
    this.mPresenter = null;
    this.mUnBinder = null;
    this.mApplication = null;
  }

  protected abstract void initInject();

  protected abstract int getLayout();

  protected abstract void initEventAndData();
}