package com.rabtman.common.base.mvp;

public interface IView {

  /**
   * 显示加载
   */
  void showLoading();

  /**
   * 隐藏加载
   */
  void hideLoading();

  /**
   * 显示错误信息
   */
  void showError(String message);
}
