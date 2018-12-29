package com.rabtman.common.base;

import android.content.Context;
import android.text.TextUtils;
import com.rabtman.common.R;
import com.rabtman.common.base.mvp.IView;
import com.rabtman.common.http.ApiException;
import com.rabtman.common.utils.ToastUtil;
import io.reactivex.subscribers.ResourceSubscriber;
import java.net.SocketTimeoutException;
import retrofit2.HttpException;


public abstract class CommonSubscriber<T> extends ResourceSubscriber<T> {

  private Context mContext;
  private IView mView;
  private String mErrorMsg;
  private boolean isShowErrorState = true;

  protected CommonSubscriber(Context context) {
    this.mContext = context;
  }

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
    if (mView == null && mContext == null) {
      return;
    }
    showError(e);
  }

  private void showError(Throwable e) {
    if (mView != null) {
      if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
        mView.showError(mErrorMsg);
      } else if (e instanceof ApiException) {
        mView.showError(e.toString());
      } else if (e instanceof HttpException) {
        mView.showError(handleHttpExceptionTips(e));
      } else if (e instanceof SocketTimeoutException) {
        mView.showError(R.string.msg_error_network);
      } else {
        mView.showError(R.string.msg_error_unknown);
      }
    } else if (mContext != null) {
      if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
        ToastUtil.show(mContext, mErrorMsg);
      } else if (e instanceof ApiException) {
        ToastUtil.show(mContext, e.toString());
      } else if (e instanceof HttpException) {
        int resString = handleHttpExceptionTips(e);
        ToastUtil.show(mContext, mContext.getString(resString));
      }
    }
  }

  private int handleHttpExceptionTips(Throwable e) {
    int code = ((HttpException) e).code();
    if (code == 429) {
      return R.string.msg_error_too_fast;
    } else if (code == 404) {
      return R.string.msg_error_404;
    } else {
      return R.string.msg_error_network;
    }
  }

}
