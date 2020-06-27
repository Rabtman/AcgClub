package com.rabtman.acgnews.di.module

import com.rabtman.acgnews.mvp.contract.ZeroFiveNewsDetailContract
import com.rabtman.acgnews.mvp.model.ZeroFiveNewsDetailModel
import com.rabtman.common.di.scope.ActivityScope
import dagger.Module
import dagger.Provides

/**
 * @author Rabtman
 */
@Module
class ZeroFiveNewsDetailModule(private val view: ZeroFiveNewsDetailContract.View) {
    @ActivityScope
    @Provides
    fun providerZeroFiveNewsDetailView(): ZeroFiveNewsDetailContract.View {
        return view
    }

    @ActivityScope
    @Provides
    fun providerZeroFiveNewsDetailModel(model: ZeroFiveNewsDetailModel): ZeroFiveNewsDetailContract.Model {
        return model
    }

}