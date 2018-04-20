package com.rabtman.acgcomic.mvp.presenter

import com.rabtman.acgcomic.R
import com.rabtman.acgcomic.mvp.OacgComicDetailContract
import com.rabtman.acgcomic.mvp.OacgComicDetailContract.Model
import com.rabtman.acgcomic.mvp.OacgComicDetailContract.View
import com.rabtman.acgcomic.mvp.model.entity.OacgComicEpisode
import com.rabtman.acgcomic.mvp.model.entity.OacgComicItem
import com.rabtman.acgcomic.mvp.model.entity.db.ComicCache
import com.rabtman.common.base.CommonSubscriber
import com.rabtman.common.base.mvp.BasePresenter
import com.rabtman.common.di.scope.ActivityScope
import com.rabtman.common.utils.RxUtil
import io.reactivex.subscribers.ResourceSubscriber
import javax.inject.Inject

/**
 * @author Rabtman
 */
@ActivityScope
class OacgComicDetailPresenter @Inject
constructor(model: OacgComicDetailContract.Model,
            rootView: OacgComicDetailContract.View) : BasePresenter<Model, View>(model, rootView) {

    private var currentComicEpisodes: List<OacgComicEpisode>? = null
    private var currentComicCache: ComicCache = ComicCache()

    fun getOacgComicDetail(comicId: String) {
        addSubscribe(
                mModel.getComicDetail(comicId.toInt())
                        .compose(RxUtil.rxSchedulerHelper())
                        .subscribeWith(object : CommonSubscriber<List<OacgComicEpisode>>(mView) {
                            override fun onComplete() {
                                mView.hideLoading()
                            }

                            override fun onError(e: Throwable?) {
                                super.onError(e)
                                mView.showPageError()
                            }

                            override fun onNext(comicEpisodes: List<OacgComicEpisode>) {
                                currentComicEpisodes = comicEpisodes
                                mView.showComicDetail(comicEpisodes)
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
     * @param lastChapterPos 上一次观看章节位置
     */
    fun updateScheduleReadChapter(comicId: String, lastChapterPos: Int) {
        addSubscribe(
                mModel.updateComicLastChapter(comicId, lastChapterPos)
                        .compose(RxUtil.rxSchedulerHelper())
                        .subscribe({
                            currentComicCache.chapterPos = lastChapterPos
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
            currentComicEpisodes?.let { comicEpisodes ->
                mView.showComicCacheStatus(currentComicCache)
                mView.start2ComicRead(
                        comicId,
                        currentComicCache.chapterPos,
                        comicEpisodes[currentComicCache.chapterPos].orderIdx
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
                                    currentComicEpisodes?.let { comicEpisodes ->
                                        mView.showComicCacheStatus(currentComicCache)
                                        if (isManualClick) {
                                            mView.start2ComicRead(
                                                    comicId,
                                                    currentComicCache.chapterPos,
                                                    comicEpisodes[currentComicCache.chapterPos].orderIdx
                                            )
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
    fun collectOrCancelComic(comicInfo: OacgComicItem, isCollected: Boolean) {
        addSubscribe(
                mModel.collectComic(comicInfo, isCollected.not())
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
