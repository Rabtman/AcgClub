package com.rabtman.acgschedule.di.module

import com.rabtman.acgschedule.mvp.contract.ScheduleVideoContract
import com.rabtman.acgschedule.mvp.model.ScheduleVideoModel
import com.rabtman.common.di.scope.ActivityScope
import dagger.Module
import dagger.Provides

/**
 * @author Rabtman
 */
@Module
class ScheduleVideoModule(private val view: ScheduleVideoContract.View) {
    @ActivityScope
    @Provides
    fun providerScheduleVideoView(): ScheduleVideoContract.View {
        return view
    }

    @ActivityScope
    @Provides
    fun providerScheduleVideoModel(model: ScheduleVideoModel): ScheduleVideoContract.Model {
        return model
    }

}