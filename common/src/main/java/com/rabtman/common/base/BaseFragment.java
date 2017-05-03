package com.rabtman.common.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.rabtman.common.base.mvp.BasePresenter;
import com.rabtman.common.base.mvp.IView;
import com.rabtman.common.di.component.AppComponent;
import es.dmoral.toasty.Toasty;
import javax.inject.Inject;
import me.yokeyword.fragmentation.SupportFragment;


public abstract class BaseFragment<T extends BasePresenter> extends SupportFragment implements
    IView {

  @Inject
  protected T mPresenter;
  protected View mView;
  protected BaseActivity mActivity;
  protected Context mContext;
  protected boolean isInited = false;
  private Unbinder mUnBinder;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    mView = inflater.inflate(getLayoutId(), null);
    mUnBinder = ButterKnife.bind(this, mView);
    return mView;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mActivity = (BaseActivity) getActivity();
    setupFragmentComponent(mActivity.mApplication.getAppComponent());
    initData();
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (savedInstanceState == null) {
      if (!isHidden()) {
        isInited = true;
        initData();
      }
    } else {
      if (!isSupportHidden()) {
        isInited = true;
        initData();
      }
    }
  }

  @Override
  public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    if (!isInited && !hidden) {
      isInited = true;
      initData();
    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    if (mPresenter != null) {
      mPresenter.onDestroy();
    }
    mUnBinder.unbind();
  }


  @Override
  public void showLoading() {

  }

  @Override
  public void hideLoading() {

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