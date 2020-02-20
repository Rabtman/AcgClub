package com.rabtman.acgmusic.mvp.model

import com.rabtman.acgmusic.api.AcgMusicService
import com.rabtman.acgmusic.mvp.RandomMusicContract
import com.rabtman.acgmusic.mvp.model.entity.MusicInfo
import com.rabtman.common.base.mvp.BaseModel
import com.rabtman.common.di.scope.ActivityScope
import com.rabtman.common.integration.IRepositoryManager
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * @author Rabtman
 */
@ActivityScope
class RandomMusicModel @Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), RandomMusicContract.Model {

    override fun getRandomSong(): Flowable<MusicInfo> {
        return mRepositoryManager.obtainRetrofitService(AcgMusicService::class.java)
                .getRandomSong()
    }
}

