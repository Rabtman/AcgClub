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

  /**
   * 页面加载中
   */
  void showPageLoading();

  /**
   * 空白页面
   */
  void showPageEmpty();

  /**
   * 页面加载失败
   */
  void showPageError();

  /**
   * 展示页面内容
   */
  void showPageContent();
}
