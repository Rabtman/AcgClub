package com.rabtman.acgcomic.mvp.presenter

import com.rabtman.acgcomic.R
import com.rabtman.acgcomic.mvp.OacgComicEpisodeDetailContract
import com.rabtman.acgcomic.mvp.model.entity.ComicCache
import com.rabtman.acgcomic.mvp.model.entity.OacgComicEpisodePage
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
class OacgComicEpisodeDetailPresenter @Inject
constructor(model: OacgComicEpisodeDetailContract.Model,
            rootView: OacgComicEpisodeDetailContract.View) : BasePresenter<OacgComicEpisodeDetailContract.Model, OacgComicEpisodeDetailContract.View>(model, rootView) {
    //当前漫画id
    private var currentComicId = "-1"
    //上一话
    private var preIndex = 0
    //下一话
    private var nextIndex = 0

    private var currentComicCache: ComicCache = ComicCache()

    fun setComicId(comicId: String) {
        currentComicId = comicId
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
    fun getEpisodeDetail(chapterIndex: Int) {
        addSubscribe(
                mModel.getEpisodeDetail(currentComicId.toInt(), chapterIndex)
                        .compose(RxUtil.rxSchedulerHelper<OacgComicEpisodePage>())
                        .subscribeWith(object : CommonSubscriber<OacgComicEpisodePage>(mView) {
                            override fun onStart() {
                                super.onStart()
                                mView.showLoading()
                            }

                            override fun onComplete() {
                                mView.hideLoading()
                            }

                            override fun onNext(comicEpisodePage: OacgComicEpisodePage) {
                                preIndex = comicEpisodePage.preIndex.toInt()
                                nextIndex = comicEpisodePage.nextIndex.toInt()

                                mView.showEpisodeDetail(comicEpisodePage)
                            }
                        })
        )
    }

    /**
     * 查询该漫画的缓存信息
     */
    fun getCurrentComicCache() {
        addSubscribe(
                mModel.getComicCacheById(currentComicId)
                        .compose(RxUtil.rxSchedulerHelper())
                        .subscribeWith(object : ResourceSubscriber<ComicCache>() {

                            override fun onNext(item: ComicCache) {
                                currentComicCache = item
                            }

                            override fun onComplete() {

                            }

                            override fun onError(t: Throwable) {
                                t.printStackTrace()
                                mView.showError(R.string.msg_error_unknown)
                            }

                        })
        )
    }

    /**
     * 记录上一次漫画观看章节页数
     *
     * @param lastChapterPos 上一次观看章节位置
     * @param lastPagePos 上一次观看页面位置
     */
    fun updateScheduleReadRecord(lastPagePos: Int) {

    }

}