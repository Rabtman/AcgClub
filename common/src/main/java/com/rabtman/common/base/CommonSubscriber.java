package com.rabtman.common.base;

import android.text.TextUtils;
import com.rabtman.common.R;
import com.rabtman.common.base.mvp.IView;
import com.rabtman.common.http.ApiException;
import com.rabtman.common.utils.LogUtil;
import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.HttpException;


public abstract class CommonSubscriber<T> extends ResourceSubscriber<T> {

  private IView mView;
  private String mErrorMsg;
  private boolean isShowErrorState = true;

  protected CommonSubscriber(IView view) {
    this.mView = view;
  }

  protected CommonSubscriber(IView view, String errorMsg) {
    this.mView = view;
    this.mErrorMsg = errorMsg;
  }

  protected CommonSubscriber(IView view, boolean isShowErrorState) {
    this.mView = view;
    this.isShowErrorState = isShowErrorState;
  }

  protected CommonSubscriber(IView view, String errorMsg, boolean isShowErrorState) {
    this.mView = view;
    this.mErrorMsg = errorMsg;
    this.isShowErrorState = isShowErrorState;
  }

  @Override
  protected void onStart() {
    super.onStart();
  }

  @Override
  public void onComplete() {
  }

  @Override
  public void onError(Throwable e) {
    e.printStackTrace();
    if (mView == null) {
      return;
    }
    if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
      mView.showError(mErrorMsg);
    } else if (e instanceof ApiException) {
      mView.showError(e.toString());
    } else if (e instanceof HttpException) {
      mView.showError(R.string.msg_error_network);
    } else {
      mView.showError(R.string.msg_error_unknown);
      LogUtil.d(e.toString());
    }
    mView.hideLoading();
  }
}
