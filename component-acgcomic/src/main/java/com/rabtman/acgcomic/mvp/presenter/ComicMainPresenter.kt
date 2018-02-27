package com.rabtman.acgcomic.mvp.presenter

import com.rabtman.acgcomic.mvp.ComicMainContract
import com.rabtman.acgcomic.mvp.model.entity.AcgComicItem
import com.rabtman.common.base.CommonSubscriber
import com.rabtman.common.base.mvp.BasePresenter
import com.rabtman.common.di.scope.FragmentScope
import com.rabtman.common.utils.LogUtil
import com.rabtman.common.utils.RxUtil
import javax.inject.Inject

/**
 * @author Rabtman
 */
@FragmentScope
class ComicMainPresenter
@Inject constructor(model: ComicMainContract.Model,
                    view: ComicMainContract.View) : BasePresenter<ComicMainContract.Model, ComicMainContract.View>(model, view) {
    /**
     * 当前菜单的选择结果
     * 按位置顺序分别对应：
     *  题材
     *  读者群
     *  进度
     *  地域
     *  排序
     *  当前页面位置
     */
    private var selected = arrayOf(0, 0, 0, 0, 0, 0)

    /**
     * 记录选择的菜单项，并刷新数据
     */
    fun changeMenuSelected(index: Int, selectedPos: Int) {
        selected[index] = selectedPos
        getComicInfos()
    }

    fun getComicInfos() {
        selected[5] = 0
        addSubscribe(
                mModel.getComicInfos(selected.joinToString(separator = "-"))
                        .compose(RxUtil.rxSchedulerHelper<List<AcgComicItem>>())
                        .subscribeWith(object : CommonSubscriber<List<AcgComicItem>>(mView) {
                            override fun onStart() {
                                super.onStart()
                                mView.showLoading()
                            }

                            override fun onComplete() {
                                mView.hideLoading()
                            }

                            override fun onNext(comicItems: List<AcgComicItem>) {
                                LogUtil.d("getComicInfos" + comicItems.toString())
                                mView.showComicInfos(comicItems)
                            }
                        })
        )
    }

    fun getMoreComicInfos() {
        selected[5] = selected[5]++
        addSubscribe(
                mModel.getComicInfos(selected.joinToString(separator = "-"))
                        .compose(RxUtil.rxSchedulerHelper<List<AcgComicItem>>())
                        .subscribeWith(object : CommonSubscriber<List<AcgComicItem>>(mView) {
                            override fun onStart() {
                                super.onStart()
                                mView.showLoading()
                            }

                            override fun onComplete() {
                                mView.hideLoading()
                            }

                            override fun onNext(comicItems: List<AcgComicItem>) {
                                LogUtil.d("getMoreComicInfos" + comicItems.toString())
                                mView.showMoreComicInfos(comicItems, comicItems.isNotEmpty())
                            }

                            override fun onError(e: Throwable?) {
                                super.onError(e)
                                mView.onLoadMoreFail()
                                selected[5] = selected[5]--
                            }
                        })
        )
    }
}