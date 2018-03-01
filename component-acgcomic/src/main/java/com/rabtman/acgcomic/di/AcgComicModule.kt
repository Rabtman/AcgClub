package com.rabtman.acgcomic.di

import com.rabtman.acgcomic.mvp.*
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