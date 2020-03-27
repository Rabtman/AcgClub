package com.rabtman.acgcomic.mvp.presenter

import com.rabtman.acgcomic.mvp.QiMIaoComicEpisodeDetailContract
import com.rabtman.acgcomic.mvp.model.dto.ComicReadRecord
import com.rabtman.acgcomic.mvp.model.entity.db.ComicCache
import com.rabtman.acgcomic.mvp.model.entity.jsoup.QiMiaoComicEpisodeDetail
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
class QiMiaoComicEpisodeDetailPresenter @Inject
constructor(model: QiMIaoComicEpisodeDetailContract.Model,
            rootView: QiMIaoComicEpisodeDetailContract.View) : BasePresenter<QiMIaoComicEpisodeDetailContract.Model, QiMIaoComicEpisodeDetailContract.View>(model, rootView) {
    //当前漫画id
    private var curComicId = "-1"
    //当前话对应的位置
    private var curChapterPos = 0
    //当前话
    private var curIndex = ""
    //上一话
    private var preIndex = ""
    //下一话
    private var nextIndex = ""

    fun setIntentData(detailUrl: String, comicChapterPos: Int, episodeUrl: String) {
        curComicId = detailUrl
        curChapterPos = comicChapterPos
        curIndex = episodeUrl
    }

    /**
     * 加载上一话
     */
    fun getPreEpisodeDetail() {
        getEpisodeDetail(preIndex)
    }

    /**
     * 加载下一话
     */
    fun getNextEpisodeDetail() {
        getEpisodeDetail(nextIndex)
    }

    /**
     * 加载漫画详细内容
     */
    private fun getEpisodeDetail(episodeUrl: String) {
        addSubscribe(
                mModel.getEpisodeDetail(episodeUrl)
                        .compose(RxUtil.rxSchedulerHelper<QiMiaoComicEpisodeDetail>())
                        .subscribeWith(object : CommonSubscriber<QiMiaoComicEpisodeDetail>(mView) {
                            override fun onStart() {
                                super.onStart()
                                mView.showLoading()
                            }

                            override fun onComplete() {
                                mView.hideLoading()
                            }

                            override fun onError(e: Throwable?) {
                                //curChapterPos -= (chapterIndex - curIndex)
                                super.onError(e)
                                mView.hideLoading()
                            }

                            override fun onNext(episodeDetail: QiMiaoComicEpisodeDetail) {
                                /*if (curIndex != chapterIndex) { //改变章节，则更新数据库记录
                                    updateScheduleReadRecord(chapterPos, 0)
                                }*/
                                preIndex = episodeDetail.preEpisode
                                curIndex = episodeUrl
                                nextIndex = episodeDetail.nextEpisode
                                mView.showEpisodeDetail(episodeDetail, 0)
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
                        mModel.getEpisodeDetail(curIndex),
                        BiFunction<ComicCache, QiMiaoComicEpisodeDetail, ComicReadRecord> { comicCache, pageData ->
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
                                preIndex = pageData.preEpisode
                                curIndex = pageData.nextEpisode
                                nextIndex = pageData.nextEpisode
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