package com.rabtman.acgcomic.di

import com.rabtman.acgcomic.mvp.ComicMainContract
import com.rabtman.acgcomic.mvp.ComicMainModel
import com.rabtman.common.di.scope.FragmentScope
import dagger.Module
import dagger.Provides

/**
 * @author Rabtman
 */
@Module
class ComicMainModule(private val view: ComicMainContract.View) {

    @FragmentScope
    @Provides
    internal fun providerComicMainView(): ComicMainContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    internal fun providerComicMainModel(model: ComicMainModel): ComicMainContract.Model {
        return model
    }
}