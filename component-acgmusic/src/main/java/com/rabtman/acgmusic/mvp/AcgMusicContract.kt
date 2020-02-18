package com.rabtman.acgmusic.mvp

import com.rabtman.acgmusic.mvp.model.entity.MusicInfo
import com.rabtman.common.base.mvp.IModel
import com.rabtman.common.base.mvp.IView
import io.reactivex.Flowable

/**
 * @author Rabtman
 * 漫画模块所有契约类
 */

interface RandomMusicContract {

    interface View : IView {
        fun onLoadMusicSuccess(info: MusicInfo, ready2Play: Boolean)
        fun onLoadMoreFail()
    }

    interface Model : IModel {
        fun getRandomSong(): Flowable<MusicInfo>
    }
}


