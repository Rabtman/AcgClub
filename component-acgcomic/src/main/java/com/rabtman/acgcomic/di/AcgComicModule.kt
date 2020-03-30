package com.rabtman.acgcomic.di

import com.rabtman.acgcomic.mvp.DmzjComicContract
import com.rabtman.acgcomic.mvp.QiMIaoComicChapterDetailContract
import com.rabtman.acgcomic.mvp.QiMiaoComicContract
import com.rabtman.acgcomic.mvp.QiMiaoComicDetailContract
import com.rabtman.acgcomic.mvp.model.DmzjComicModel
import com.rabtman.acgcomic.mvp.model.QiMiaoComicDetailModel
import com.rabtman.acgcomic.mvp.model.QiMiaoComicEpisodeDetailModel
import com.rabtman.acgcomic.mvp.model.QiMiaoComicModel
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
class QiMiaoComicModule(private val view: QiMiaoComicContract.View) {

    @FragmentScope
    @Provides
    internal fun providerQiMiaoComicView(): QiMiaoComicContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    internal fun providerQiMiaoComicModel(model: QiMiaoComicModel): QiMiaoComicContract.Model {
        return model
    }
}

@Module
class QiMiaoComicDetailModule(private val view: QiMiaoComicDetailContract.View) {

    @ActivityScope
    @Provides
    internal fun providerQiMiaoComicDetailView(): QiMiaoComicDetailContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    internal fun providerQiMiaoComicDetailModel(model: QiMiaoComicDetailModel): QiMiaoComicDetailContract.Model {
        return model
    }
}

@Module
class QiMiaoComicEpisodeDetailModule(private val view: QiMIaoComicChapterDetailContract.View) {

    @ActivityScope
    @Provides
    internal fun providerQiMiaoComicEpisodeDetailView(): QiMIaoComicChapterDetailContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    internal fun providerQiMiaoComicEpisodeDetailModel(model: QiMiaoComicEpisodeDetailModel): QiMIaoComicChapterDetailContract.Model {
        return model
    }
}