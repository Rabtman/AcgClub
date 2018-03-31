package com.rabtman.common.base.pagestatusmanager;

import android.view.View;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.rabtman.common.utils.NetworkUtils;

public abstract class PageStatusListener {

  public void receiveRetryEvent(View retryView) {
    if (!NetworkUtils.isConnected()) {
      onNetWorkDisconnect();
    } else {
      onRetry(retryView);
    }
  }

  public void onLoading(View loadingView) {
  }

  public void onEmpty(View emptyView) {
  }

  public void onRetry(View retryView) {

  }

  public int generateLoadingLayoutId() {
    return PageStatusManager.NO_LAYOUT_ID;
  }

  public int generateRetryLayoutId() {
    return PageStatusManager.NO_LAYOUT_ID;
  }

  public int generateEmptyLayoutId() {
    return PageStatusManager.NO_LAYOUT_ID;
  }

  public View generateLoadingLayout() {
    return null;
  }

  public View generateRetryLayout() {
    return null;
  }

  public View generateEmptyLayout() {
    return null;
  }

  public boolean isSetLoadingLayout() {
    if (generateLoadingLayoutId() != PageStatusManager.NO_LAYOUT_ID
        || generateLoadingLayout() != null) {
      return true;
    }
    return false;
  }

  public boolean isSetRetryLayout() {
    if (generateRetryLayoutId() != PageStatusManager.NO_LAYOUT_ID
        || generateRetryLayout() != null) {
      return true;
    }
    return false;
  }

  public boolean isSetEmptyLayout() {
    if (generateEmptyLayoutId() != PageStatusManager.NO_LAYOUT_ID
        || generateEmptyLayout() != null) {
      return true;
    }
    return false;
  }

  /**
   * 当前网络不可用时进行的操作
   */
  private void onNetWorkDisconnect() {
    StyledDialog.buildMdAlert("提示", "当前无网络", new MyDialogListener() {
      @Override
      public void onFirst() {
        NetworkUtils.openWirelessSettings();
      }

      @Override
      public void onSecond() {

      }
    }).setBtnText("去设置", "知道了").show();
  }

}