package com.rabtman.common.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.rabtman.common.base.mvp.BasePresenter;
import javax.inject.Inject;
import me.yokeyword.fragmentation.SupportFragment;


public abstract class BaseFragment<T extends BasePresenter> extends SupportFragment{

  @Inject
  protected T mPresenter;
  protected View mView;
  protected Activity mActivity;
  protected Context mContext;
  protected boolean isInited = false;
  private Unbinder mUnBinder;

  @Override
  public void onAttach(Context context) {
    mActivity = (Activity) context;
    mContext = context;
    super.onAttach(context);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    mView = inflater.inflate(getLayoutId(), null);
    initInject();
    return mView;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mUnBinder = ButterKnife.bind(this, view);
    if (savedInstanceState == null) {
      if (!isHidden()) {
        isInited = true;
        initEventAndData();
      }
    } else {
      if (!isSupportHidden()) {
        isInited = true;
        initEventAndData();
      }
    }
  }

  @Override
  public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    if (!isInited && !hidden) {
      isInited = true;
      initEventAndData();
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

  protected abstract void initInject();

  protected abstract int getLayoutId();

  protected abstract void initEventAndData();
}