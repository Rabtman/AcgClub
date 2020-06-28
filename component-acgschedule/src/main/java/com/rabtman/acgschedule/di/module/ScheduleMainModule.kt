package com.rabtman.acgschedule.di.module

import com.rabtman.acgschedule.mvp.contract.ScheduleMainContract
import com.rabtman.acgschedule.mvp.model.ScheduleMainModel
import com.rabtman.common.di.scope.FragmentScope
import dagger.Module
import dagger.Provides

/**
 * @author Rabtman
 */
@Module
class ScheduleMainModule(private val view: ScheduleMainContract.View) {
    @FragmentScope
    @Provides
    fun providerScheduleMainView(): ScheduleMainContract.View {
        return view
    }

    @FragmentScope
    @Provides
    fun providerScheduleMainModel(model: ScheduleMainModel): ScheduleMainContract.Model {
        return model
    }

}