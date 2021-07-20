package com.rabtman.acgcomic.mvp.presenter

import com.rabtman.acgcomic.R
import com.rabtman.acgcomic.mvp.QiMiaoComicDetailContract
import com.rabtman.acgcomic.mvp.QiMiaoComicDetailContract.Model
import com.rabtman.acgcomic.mvp.QiMiaoComicDetailContract.View
import com.rabtman.acgcomic.mvp.model.entity.db.ComicCache
import com.rabtman.acgcomic.mvp.model.entity.jsoup.QiMiaoComicDetail
import com.rabtman.acgcomic.mvp.model.entity.jsoup.QiMiaoComicItem
import com.rabtman.business.base.CommonSubscriber
import com.rabtman.common.base.mvp.BasePresenter
import com.rabtman.common.di.scope.ActivityScope
import com.rabtman.common.utils.RxUtil
import io.reactivex.subscribers.ResourceSubscriber
import javax.inject.Inject

/**
 * @author Rabtman
 */
@ActivityScope
class QiMiaoComicDetailPresenter @Inject
constructor(model: QiMiaoComicDetailContract.Model,
            rootView: QiMiaoComicDetailContract.View) : BasePresenter<Model, View>(model, rootView) {

    private var currentComicDetail: QiMiaoComicDetail? = null
    private var currentComicCache: ComicCache = ComicCache()

    fun getComicDetail(detailUrl: String) {
        addSubscribe(
                mModel.getComicDetail(detailUrl)
                        .compose(RxUtil.rxSchedulerHelper())
                        .subscribeWith(object : CommonSubscriber<QiMiaoComicDetail>(mView) {
                            override fun onComplete() {
                                mView.hideLoading()
                            }

                            override fun onError(e: Throwable) {
                                super.onError(e)
                                mView.showPageError()
                            }

                            override fun onNext(comicDetail: QiMiaoComicDetail) {
                                currentComicDetail = comicDetail
                                mView.showComicDetail(comicDetail)
                                mView.showPageContent()
                                if (currentComicCache.chapterPos != -1) {
                                    mView.showComicCacheStatus(currentComicCache)
                                }
                            }
                        })
        )
    }

    /**
     * 记录上一次漫画观看章节
     *
     * @param lastChapterId 上一次观看章节id
     */
    fun updateScheduleReadChapter(comicId: String, lastChapterId: Int) {
        addSubscribe(
                mModel.updateComicLastChapter(comicId, lastChapterId)
                        .compose(RxUtil.rxSchedulerHelper())
                        .subscribe({
                            currentComicCache.chapterPos = lastChapterId
                            mView.showComicCacheStatus(currentComicCache)
                        }, { throwable -> throwable.printStackTrace() })
        )
    }

    /**
     * 查询该漫画的缓存信息
     * @param isManualClick 是否主动点击
     */
    fun getCurrentComicCache(comicId: String, isManualClick: Boolean) {
        if (currentComicCache.comicId.isNotEmpty() && isManualClick) {
            mView.showComicCacheStatus(currentComicCache)
            currentComicDetail?.comicChapters?.let {
                mView.start2ComicRead(comicId,
                        if (currentComicCache.chapterPos == 0) {
                            it[it.size - 1].chapterId
                        } else {
                            currentComicCache.chapterPos.toString()
                        }
                )
            }
        } else {
            addSubscribe(
                    mModel.getComicCacheById(comicId)
                            .compose(RxUtil.rxSchedulerHelper())
                            .subscribeWith(object : ResourceSubscriber<ComicCache>() {

                                override fun onNext(item: ComicCache) {
                                    currentComicCache = item
                                }

                                override fun onComplete() {
                                    currentComicDetail?.let { comicEpisodes ->
                                        mView.showComicCacheStatus(currentComicCache)
                                        if (isManualClick) {
                                            mView.start2ComicRead(comicId, currentComicCache.chapterPos.toString())
                                        }
                                    }
                                }

                                override fun onError(t: Throwable) {
                                    t.printStackTrace()
                                    mView.showError(R.string.msg_error_unknown)
                                }

                            })
            )
        }
    }

    /**
     * 漫画收藏、取消
     */
    fun collectOrCancelComic(comicItem: QiMiaoComicItem, isCollected: Boolean) {
        addSubscribe(
                mModel.collectComic(comicItem, isCollected.not())
                        .compose(RxUtil.rxSchedulerHelper())
                        .subscribe({
                            currentComicCache.isCollect = isCollected.not()
                            mView.showComicCacheStatus(currentComicCache)
                            if (isCollected.not()) {
                                mView.showMsg(R.string.msg_success_collect_add)
                            } else {
                                mView.showMsg(R.string.msg_success_collect_cancel)
                            }
                        }, { throwable ->
                            throwable.printStackTrace()
                            if (isCollected.not()) {
                                mView.showError(R.string.msg_error_collect_add)
                            } else {
                                mView.showError(R.string.msg_error_collect_cancel)
                            }
                        })
        )
    }
}
