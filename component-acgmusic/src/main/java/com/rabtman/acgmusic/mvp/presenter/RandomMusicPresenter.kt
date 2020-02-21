package com.rabtman.acgmusic.mvp.presenter

/**
 * @author Rabtman
 */
/*
@ActivityScope
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
}*/
