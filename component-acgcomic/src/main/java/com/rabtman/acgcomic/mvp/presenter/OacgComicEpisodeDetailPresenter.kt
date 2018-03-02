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

    fun getEpisodeDetail(comicId: String, chapterIndex: String) {
        addSubscribe(
                mModel.getEpisodeDetail(comicId.toInt(), chapterIndex.toInt())
                        .compose(RxUtil.rxSchedulerHelper<OacgComicEpisodePage>())
                        .subscribeWith(object : CommonSubscriber<OacgComicEpisodePage>(mView) {
                            override fun onStart() {
                                super.onStart()
                                mView.showLoading()
                            }

                            override fun onComplete() {
                                mView.hideLoading()
                            }

                            override fun onNext(comicEpisodes: OacgComicEpisodePage) {
                                LogUtil.d("getOacgComicEpisodeDetail" + comicEpisodes.toString())
                                mView.showEpisodeDetail(comicEpisodes)
                            }
                        })
        )
    }

}