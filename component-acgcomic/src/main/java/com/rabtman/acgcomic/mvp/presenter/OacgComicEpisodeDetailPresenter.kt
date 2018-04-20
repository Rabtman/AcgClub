package com.rabtman.acgcomic.mvp.presenter

import com.rabtman.acgcomic.mvp.OacgComicEpisodeDetailContract
import com.rabtman.acgcomic.mvp.model.dto.ComicReadRecord
import com.rabtman.acgcomic.mvp.model.entity.OacgComicEpisodePage
import com.rabtman.acgcomic.mvp.model.entity.db.ComicCache
import com.rabtman.common.base.CommonSubscriber
import com.rabtman.common.base.mvp.BasePresenter
import com.rabtman.common.di.scope.ActivityScope
import com.rabtman.common.utils.RxUtil
import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

/**
 * @author Rabtman
 */
@ActivityScope
class OacgComicEpisodeDetailPresenter @Inject
constructor(model: OacgComicEpisodeDetailContract.Model,
            rootView: OacgComicEpisodeDetailContract.View) : BasePresenter<OacgComicEpisodeDetailContract.Model, OacgComicEpisodeDetailContract.View>(model, rootView) {
    //当前漫画id
    private var curComicId = "-1"
    //当前话对应的位置
    private var curChapterPos = 0
    //当前话
    private var curIndex = 0
    //上一话
    private var preIndex = 0
    //下一话
    private var nextIndex = 0

    fun setIntentData(comicId: String, comicChapterPos: Int, comicChapterIndex: String) {
        curComicId = comicId
        curChapterPos = comicChapterPos
        curIndex = comicChapterIndex.toInt()
    }

    /**
     * 加载上一话
     */
    fun getPreEpisodeDetail() {
        getEpisodeDetail(--curChapterPos, preIndex)
    }

    /**
     * 加载下一话
     */
    fun getNextEpisodeDetail() {
        getEpisodeDetail(++curChapterPos, nextIndex)
    }

    /**
     * 加载漫画详细内容
     */
    private fun getEpisodeDetail(chapterPos: Int, chapterIndex: Int) {
        addSubscribe(
                mModel.getEpisodeDetail(curComicId.toInt(), chapterIndex)
                        .compose(RxUtil.rxSchedulerHelper<OacgComicEpisodePage>())
                        .subscribeWith(object : CommonSubscriber<OacgComicEpisodePage>(mView) {
                            override fun onStart() {
                                super.onStart()
                                mView.showLoading()
                            }

                            override fun onComplete() {
                                mView.hideLoading()
                            }

                            override fun onError(e: Throwable?) {
                                curChapterPos -= (chapterIndex - curIndex)
                                super.onError(e)
                                mView.hideLoading()
                            }

                            override fun onNext(comicEpisodePage: OacgComicEpisodePage) {
                                if (curIndex != chapterIndex) { //改变章节，则更新数据库记录
                                    updateScheduleReadRecord(chapterPos, 0)
                                }
                                preIndex = comicEpisodePage.preIndex.toInt()
                                curIndex = comicEpisodePage.currIndex.toInt()
                                nextIndex = comicEpisodePage.nextIndex.toInt()
                                mView.showEpisodeDetail(comicEpisodePage, 0)
                            }
                        })
        )
    }

    /**
     * 加载漫画详情及其缓存信息
     */
    fun getEpisodeDetailAndCache() {
        addSubscribe(
                Flowable.zip(
                        mModel.getComicCacheByChapter(curComicId, curChapterPos),
                        mModel.getEpisodeDetail(curComicId.toInt(), curIndex),
                        BiFunction<ComicCache, OacgComicEpisodePage, ComicReadRecord> { comicCache, pageData ->
                            ComicReadRecord(pageData, comicCache)
                        }
                ).compose(RxUtil.rxSchedulerHelper())
                        .subscribeWith(object : CommonSubscriber<ComicReadRecord>(mView) {
                            override fun onStart() {
                                mView.showLoading()
                                super.onStart()
                            }

                            override fun onComplete() {
                                mView.hideLoading()
                            }

                            override fun onError(e: Throwable?) {
                                super.onError(e)
                                mView.hideLoading()
                            }

                            override fun onNext(comicReadRecord: ComicReadRecord) {
                                val pageData = comicReadRecord.pageData
                                preIndex = pageData.preIndex.toInt()
                                curIndex = pageData.currIndex.toInt()
                                nextIndex = pageData.nextIndex.toInt()
                                mView.showEpisodeDetail(pageData, comicReadRecord.cache.pagePos)
                            }
                        })
        )
    }

    fun updateScheduleReadRecord(lastPagePos: Int) {
        updateScheduleReadRecord(curChapterPos, lastPagePos)
    }

    /**
     * 记录上一次漫画观看章节页数
     *
     * @param lastChapterPos 上一次观看章节位置
     * @param lastPagePos 上一次观看页面位置
     */
    fun updateScheduleReadRecord(lastChapterPos: Int, lastPagePos: Int) {
        addSubscribe(
                mModel.updateComicLastRecord(curComicId, lastChapterPos, lastPagePos)
                        .compose(RxUtil.rxSchedulerHelper())
                        .subscribe({
                        }, { throwable -> throwable.printStackTrace() })
        )
    }

}