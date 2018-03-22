package com.rabtman.acgcomic.mvp.presenter

import com.rabtman.acgcomic.R
import com.rabtman.acgcomic.mvp.OacgComicDetailContract
import com.rabtman.acgcomic.mvp.OacgComicDetailContract.Model
import com.rabtman.acgcomic.mvp.OacgComicDetailContract.View
import com.rabtman.acgcomic.mvp.model.entity.ComicCache
import com.rabtman.acgcomic.mvp.model.entity.OacgComicEpisode
import com.rabtman.acgcomic.mvp.model.entity.OacgComicItem
import com.rabtman.common.base.CommonSubscriber
import com.rabtman.common.base.mvp.BasePresenter
import com.rabtman.common.di.scope.ActivityScope
import com.rabtman.common.utils.LogUtil
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
                        .compose(RxUtil.rxSchedulerHelper<List<OacgComicEpisode>>())
                        .subscribeWith(object : CommonSubscriber<List<OacgComicEpisode>>(mView) {
                            override fun onStart() {
                                super.onStart()
                                mView.showLoading()
                            }

                            override fun onComplete() {
                                mView.hideLoading()
                            }

                            override fun onNext(comicEpisodes: List<OacgComicEpisode>) {
                                LogUtil.d("getOacgComicDetail" + comicEpisodes.toString())
                                currentComicEpisodes = comicEpisodes
                                mView.showComicDetail(comicEpisodes)
                            }
                        })
        )
    }

    /**
     * 根据历史记录获取当前应播放番剧
     */
    private fun getNextChapterIndex(comicEpisodes: List<OacgComicEpisode>, lastChapterPos: Int): String {
        return if (!validScheduleDetail(comicEpisodes)) {
            ""
        } else {
            val nextChapterPos = getNextChapterPos(comicEpisodes, lastChapterPos)

            //获取地址的同时，更新历史记录
            updateScheduleReadRecord(comicEpisodes[nextChapterPos].comicId, nextChapterPos)

            comicEpisodes[nextChapterPos].orderIdx
        }
    }

    /**
     * 获取下一个观看位置
     */
    private fun getNextChapterPos(comicEpisodes: List<OacgComicEpisode>, lastPos: Int): Int {
        return if (!validScheduleDetail(comicEpisodes)) {
            -1
        } else {
            if (lastPos >= comicEpisodes.size - 1) {
                comicEpisodes.size - 1
            } else {
                lastPos + 1
            }
        }
    }

    /**
     * 章节信息空校验
     */
    private fun validScheduleDetail(comicEpisodes: List<OacgComicEpisode>?): Boolean {
        return comicEpisodes != null && comicEpisodes.isNotEmpty()
    }

    /**
     * 记录上一次漫画观看章节
     *
     * @param lastChapterPos 上一次观看章节位置
     */
    fun updateScheduleReadRecord(comicId: String, lastChapterPos: Int) {
        addSubscribe(
                mModel.updateComicLastChapter(comicId, lastChapterPos)
                        .subscribe({
                            currentComicCache.comicId = comicId
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
        if (currentComicCache.comicId.isEmpty()) {
            addSubscribe(
                    mModel.getComicCacheById(comicId)
                            .compose(RxUtil.rxSchedulerHelper())
                            .subscribeWith(object : ResourceSubscriber<ComicCache>() {

                                override fun onNext(item: ComicCache) {
                                    currentComicCache = item
                                    mView.showComicCacheStatus(item)
                                }

                                override fun onComplete() {
                                    currentComicEpisodes?.let { comicEpisodes ->
                                        if (isManualClick) {
                                            mView.start2ComicRead(
                                                    comicId,
                                                    getNextChapterIndex(
                                                            comicEpisodes,
                                                            getNextChapterPos(comicEpisodes, currentComicCache.chapterPos)
                                                    )
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
        } else {
            currentComicEpisodes?.let { comicEpisodes ->
                if (isManualClick) {
                    mView.start2ComicRead(
                            comicId,
                            getNextChapterIndex(
                                    comicEpisodes,
                                    getNextChapterPos(comicEpisodes, currentComicCache.chapterPos)
                            )
                    )
                }
            }
        }
    }

    /**
     * 漫画收藏、取消
     */
    fun collectOrCancelComic(comicInfo: OacgComicItem, isCollected: Boolean) {
        addSubscribe(
                mModel.collectComic(comicInfo, isCollected.not())
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
