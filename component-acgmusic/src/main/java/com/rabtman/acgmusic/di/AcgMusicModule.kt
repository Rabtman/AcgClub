package com.rabtman.acgmusic.di

import com.rabtman.acgmusic.mvp.RandomMusicContract
import com.rabtman.acgmusic.mvp.model.RandomMusicModel
import com.rabtman.common.di.scope.ActivityScope
import dagger.Module
import dagger.Provides

/**
 * @author Rabtman
 */
@Module
class RandomMusicModule(private val view: RandomMusicContract.View) {

    @ActivityScope
    @Provides
    internal fun providerRandomMusicView(): RandomMusicContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    internal fun providerRandomMusicModel(model: RandomMusicModel): RandomMusicContract.Model {
        return model
    }
}
