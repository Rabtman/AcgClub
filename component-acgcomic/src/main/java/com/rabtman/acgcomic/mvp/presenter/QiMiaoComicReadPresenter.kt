package com.rabtman.acgcomic.mvp.presenter

import com.rabtman.acgcomic.mvp.QiMIaoComicChapterDetailContract
import com.rabtman.acgcomic.mvp.model.dto.ComicReadRecord
import com.rabtman.acgcomic.mvp.model.entity.db.ComicCache
import com.rabtman.acgcomic.mvp.model.entity.jsoup.QiMiaoComicChapterDetail
import com.rabtman.business.base.CommonSubscriber
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
class QiMiaoComicReadPresenter @Inject
constructor(model: QiMIaoComicChapterDetailContract.Model,
            rootView: QiMIaoComicChapterDetailContract.View) : BasePresenter<QiMIaoComicChapterDetailContract.Model, QiMIaoComicChapterDetailContract.View>(model, rootView) {
    //当前漫画id
    private var curComicId = "-1"
    //当前章节id
    private var curChapterId = "-1"
    //上一话
    private var preChapterId = "-1"
    //下一话
    private var nextChapterId = "-1"

    fun setIntentData(comicId: String, chapterId: String) {
        curComicId = comicId
        curChapterId = chapterId
    }

    /**
     * 加载上一话
     */
    fun getPreChapterDetail() {
        getChapterDetail(preChapterId)
    }

    /**
     * 加载下一话
     */
    fun getNextChapterDetail() {
        getChapterDetail(nextChapterId)
    }

    /**
     * 加载漫画详细内容
     */
    private fun getChapterDetail(chapterId: String) {
        addSubscribe(
                mModel.getChapterDetail(curComicId, chapterId)
                        .compose(RxUtil.rxSchedulerHelper<QiMiaoComicChapterDetail>())
                        .subscribeWith(object : CommonSubscriber<QiMiaoComicChapterDetail>(mView) {
                            override fun onStart() {
                                super.onStart()
                                mView.showLoading()
                            }

                            override fun onComplete() {
                                mView.hideLoading()
                            }

                            override fun onError(e: Throwable) {
                                //curChapterPos -= (chapterIndex - curIndex)
                                super.onError(e)
                                mView.hideLoading()
                            }

                            override fun onNext(chapterDetail: QiMiaoComicChapterDetail) {
                                /*if (curIndex != chapterIndex) { //改变章节，则更新数据库记录
                                    updateScheduleReadRecord(chapterPos, 0)
                                }*/
                                preChapterId = chapterDetail.preChapterId
                                curChapterId = chapterId
                                nextChapterId = chapterDetail.nextChapterId
                                mView.showChapterDetail(chapterDetail, 0)
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
                        mModel.getComicCacheByChapter(curComicId, curChapterId.toInt()),
                        mModel.getChapterDetail(curComicId, curChapterId),
                        BiFunction<ComicCache, QiMiaoComicChapterDetail, ComicReadRecord> { comicCache, pageData ->
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

                            override fun onError(e: Throwable) {
                                super.onError(e)
                                mView.hideLoading()
                            }

                            override fun onNext(comicReadRecord: ComicReadRecord) {
                                val pageData = comicReadRecord.pageData
                                preChapterId = pageData.preChapterId
                                nextChapterId = pageData.nextChapterId
                                mView.showChapterDetail(pageData, comicReadRecord.cache.pagePos)
                            }
                        })
        )
    }

    fun updateScheduleReadRecord(lastPagePos: Int) {
        updateScheduleReadRecord(curChapterId.toInt(), lastPagePos)
    }

    /**
     * 记录上一次漫画观看章节页数
     *
     * @param lastChapterId 上一次观看章节位置
     * @param lastPagePos 上一次观看页面位置
     */
    fun updateScheduleReadRecord(lastChapterId: Int, lastPagePos: Int) {
        addSubscribe(
                mModel.updateComicLastRecord(curComicId, lastChapterId, lastPagePos)
                        .compose(RxUtil.rxSchedulerHelper())
                        .subscribe({
                        }, { throwable -> throwable.printStackTrace() })
        )
    }

}