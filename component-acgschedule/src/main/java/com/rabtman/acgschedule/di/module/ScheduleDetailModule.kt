package com.rabtman.acgschedule.di.module

import com.rabtman.acgschedule.mvp.contract.ScheduleDetailContract
import com.rabtman.acgschedule.mvp.model.ScheduleDetailModel
import com.rabtman.common.di.scope.ActivityScope
import dagger.Module
import dagger.Provides

/**
 * @author Rabtman
 */
@Module
class ScheduleDetailModule(private val view: ScheduleDetailContract.View) {
    @ActivityScope
    @Provides
    fun providerScheduleDetailView(): ScheduleDetailContract.View {
        return view
    }

    @ActivityScope
    @Provides
    fun providerScheduleDetailModel(model: ScheduleDetailModel): ScheduleDetailContract.Model {
        return model
    }

}