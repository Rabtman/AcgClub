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

  /**
   * 显示错误信息
   */
  void showError(int stringId);

  /**
   * 显示信息
   */
  void showMsg(String message);

  /**
   * 显示信息
   */
  void showMsg(int stringId);
}
