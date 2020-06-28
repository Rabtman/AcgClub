package com.rabtman.acgschedule.di.module

import com.rabtman.acgschedule.mvp.contract.ScheduleNewContract
import com.rabtman.acgschedule.mvp.model.ScheduleNewModel
import com.rabtman.common.di.scope.ActivityScope
import dagger.Module
import dagger.Provides

/**
 * @author Rabtman
 */
@Module
class ScheduleNewModule(private val view: ScheduleNewContract.View) {
    @ActivityScope
    @Provides
    fun providerScheduleNewView(): ScheduleNewContract.View {
        return view
    }

    @ActivityScope
    @Provides
    fun providerScheduleNewModel(model: ScheduleNewModel): ScheduleNewContract.Model {
        return model
    }

}