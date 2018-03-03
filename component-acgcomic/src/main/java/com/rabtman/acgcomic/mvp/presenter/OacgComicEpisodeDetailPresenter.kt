package com.rabtman.acgcomic.mvp.presenter

import com.rabtman.acgcomic.mvp.OacgComicEpisodeDetailContract
import com.rabtman.acgcomic.mvp.model.entity.OacgComicEpisodePage
import com.rabtman.common.base.CommonSubscriber
import com.rabtman.common.base.mvp.BasePresenter
import com.rabtman.common.di.scope.ActivityScope
import com.rabtman.common.utils.LogUtil
import com.rabtman.common.utils.RxUtil
import javax.inject.Inject

/**
 * @author Rabtman
 */
@ActivityScope
class OacgComicEpisodeDetailPresenter @Inject
constructor(model: OacgComicEpisodeDetailContract.Model,
            rootView: OacgComicEpisodeDetailContract.View) : BasePresenter<OacgComicEpisodeDetailContract.Model, OacgComicEpisodeDetailContract.View>(model, rootView) {
    //当前漫画id
    private var currentComicId = -1
    //上一话
    private var preIndex = 0
    //下一话
    private var nextIndex = 0

    fun setComicId(comicId: Int) {
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
                mModel.getEpisodeDetail(currentComicId, chapterIndex)
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
                                LogUtil.d("getOacgComicEpisodeDetail" + comicEpisodePage.toString())
                                preIndex = comicEpisodePage.preIndex.toInt()
                                nextIndex = comicEpisodePage.nextIndex.toInt()
                                mView.showEpisodeDetail(comicEpisodePage)
                            }
                        })
        )
    }

}