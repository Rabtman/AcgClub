package com.rabtman.acgnews.di.module

import com.rabtman.acgnews.mvp.contract.ZeroFiveNewsContract
import com.rabtman.acgnews.mvp.model.ZeroFiveNewsModel
import com.rabtman.common.di.scope.FragmentScope
import dagger.Module
import dagger.Provides

/**
 * @author Rabtman
 */
@Module
class ZeroFiveNewsItemModule(private val view: ZeroFiveNewsContract.View) {
    @FragmentScope
    @Provides
    fun providerZeroFiveNewsView(): ZeroFiveNewsContract.View {
        return view
    }

    @FragmentScope
    @Provides
    fun providerZeroFiveNewsModel(model: ZeroFiveNewsModel): ZeroFiveNewsContract.Model {
        return model
    }

}