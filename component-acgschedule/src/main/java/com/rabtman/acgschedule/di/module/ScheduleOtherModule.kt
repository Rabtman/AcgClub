package com.rabtman.acgschedule.di.module

import com.rabtman.acgschedule.mvp.contract.ScheduleOtherContract
import com.rabtman.acgschedule.mvp.model.ScheduleOtherModel
import com.rabtman.common.di.scope.ActivityScope
import dagger.Module
import dagger.Provides

/**
 * @author Rabtman
 */
@Module
class ScheduleOtherModule(private val view: ScheduleOtherContract.View) {
    @ActivityScope
    @Provides
    fun providerScheduleOtherView(): ScheduleOtherContract.View {
        return view
    }

    @ActivityScope
    @Provides
    fun providerScheduleOtherModel(model: ScheduleOtherModel): ScheduleOtherContract.Model {
        return model
    }

}