package com.rabtman.acgnews.di.module

import com.rabtman.acgnews.mvp.contract.ISHNewsDetailContract
import com.rabtman.acgnews.mvp.model.ISHNewsDetailModel
import com.rabtman.common.di.scope.ActivityScope
import dagger.Module
import dagger.Provides

/**
 * @author Rabtman
 */
@Module
class ISHNewsDetailModule(private val view: ISHNewsDetailContract.View) {
    @ActivityScope
    @Provides
    fun providerISHNewsDetailView(): ISHNewsDetailContract.View {
        return view
    }

    @ActivityScope
    @Provides
    fun providerISHNewsDetailModel(model: ISHNewsDetailModel): ISHNewsDetailContract.Model {
        return model
    }

}