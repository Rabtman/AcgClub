package com.rabtman.acgcomic.di

import com.rabtman.acgcomic.mvp.DmzjComicContract
import com.rabtman.acgcomic.mvp.OacgComicContract
import com.rabtman.acgcomic.mvp.OacgComicDetailContract
import com.rabtman.acgcomic.mvp.OacgComicEpisodeDetailContract
import com.rabtman.acgcomic.mvp.model.DmzjComicModel
import com.rabtman.acgcomic.mvp.model.OacgComicDetailModel
import com.rabtman.acgcomic.mvp.model.OacgComicEpisodeDetailModel
import com.rabtman.acgcomic.mvp.model.OacgComicModel
import com.rabtman.common.di.scope.ActivityScope
import com.rabtman.common.di.scope.FragmentScope
import dagger.Module
import dagger.Provides

/**
 * @author Rabtman
 */
@Module
class DmzjComicModule(private val view: DmzjComicContract.View) {

    @FragmentScope
    @Provides
    internal fun providerDmzjComicView(): DmzjComicContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    internal fun providerDmzjComicModel(model: DmzjComicModel): DmzjComicContract.Model {
        return model
    }
}

@Module
class OacgComicModule(private val view: OacgComicContract.View) {

    @FragmentScope
    @Provides
    internal fun providerOacgComicView(): OacgComicContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    internal fun providerOacgComicModel(model: OacgComicModel): OacgComicContract.Model {
        return model
    }
}

@Module
class OacgComicDetailModule(private val view: OacgComicDetailContract.View) {

    @ActivityScope
    @Provides
    internal fun providerOacgComicDetailView(): OacgComicDetailContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    internal fun providerOacgComicDetailModel(model: OacgComicDetailModel): OacgComicDetailContract.Model {
        return model
    }
}

@Module
class OacgComicEpisodeDetailModule(private val view: OacgComicEpisodeDetailContract.View) {

    @ActivityScope
    @Provides
    internal fun providerOacgComicEpisodeDetailView(): OacgComicEpisodeDetailContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    internal fun providerOacgComicEpisodeDetailModel(model: OacgComicEpisodeDetailModel): OacgComicEpisodeDetailContract.Model {
        return model
    }
}