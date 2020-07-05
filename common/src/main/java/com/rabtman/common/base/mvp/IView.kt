package com.rabtman.common.base.mvp

interface IView {
    /**
     * 显示加载
     */
    fun showLoading()

    /**
     * 隐藏加载
     */
    fun hideLoading()

    /**
     * 显示错误信息
     */
    fun showError(message: String?)

    /**
     * 显示错误信息
     */
    fun showError(stringId: Int)

    /**
     * 显示信息
     */
    fun showMsg(message: String?)

    /**
     * 显示信息
     */
    fun showMsg(stringId: Int)

    /**
     * 页面加载中
     */
    fun showPageLoading()

    /**
     * 空白页面
     */
    fun showPageEmpty()

    /**
     * 页面加载失败
     */
    fun showPageError()

    /**
     * 展示页面内容
     */
    fun showPageContent()
}