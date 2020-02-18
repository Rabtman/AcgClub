package com.rabtman.acgmusic.mvp.presenter

import com.rabtman.acgmusic.mvp.RandomMusicContract
import com.rabtman.acgmusic.mvp.model.entity.MusicInfo
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
class RandomMusicPresenter
@Inject constructor(model: RandomMusicContract.Model,
                    view: RandomMusicContract.View) : BasePresenter<RandomMusicContract.Model, RandomMusicContract.View>(model, view) {

    fun getRandomMusic(ready2Play: Boolean) {
        addSubscribe(
                mModel.getRandomSong()
                        .compose(RxUtil.rxSchedulerHelper<MusicInfo>())
                        .subscribeWith(object : CommonSubscriber<MusicInfo>(mView) {
                            override fun onNext(musicInfo: MusicInfo) {
                                LogUtil.d("music info:" + musicInfo.toString())
                                if (musicInfo.code == 0) {
                                    mView.onLoadMusicSuccess(musicInfo, ready2Play)
                                } else {
                                    mView.onLoadMoreFail()
                                }
                            }

                            override fun onError(e: Throwable?) {
                                super.onError(e)
                                mView.onLoadMoreFail()
                            }
                        })
        )
    }
}