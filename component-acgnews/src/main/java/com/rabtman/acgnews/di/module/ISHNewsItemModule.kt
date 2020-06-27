package com.rabtman.acgnews.di.module

import com.rabtman.acgnews.mvp.contract.ISHNewsContract
import com.rabtman.acgnews.mvp.model.ISHNewsModel
import com.rabtman.common.di.scope.FragmentScope
import dagger.Module
import dagger.Provides

/**
 * @author Rabtman
 */
@Module
class ISHNewsItemModule(private val view: ISHNewsContract.View) {
    @FragmentScope
    @Provides
    fun providerISHNewsView(): ISHNewsContract.View {
        return view
    }

    @FragmentScope
    @Provides
    fun providerISHNewsModel(model: ISHNewsModel): ISHNewsContract.Model {
        return model
    }

}