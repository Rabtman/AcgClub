package com.rabtman.acgmusic.di

import com.rabtman.acgmusic.mvp.RandomMusicContract
import com.rabtman.acgmusic.mvp.model.RandomMusicModel
import com.rabtman.common.di.scope.FragmentScope
import dagger.Module
import dagger.Provides

/**
 * @author Rabtman
 */
@Module
class RandomMusicModule(private val view: RandomMusicContract.View) {

    @FragmentScope
    @Provides
    internal fun providerRandomMusicView(): RandomMusicContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    internal fun providerRandomMusicModel(model: RandomMusicModel): RandomMusicContract.Model {
        return model
    }
}
