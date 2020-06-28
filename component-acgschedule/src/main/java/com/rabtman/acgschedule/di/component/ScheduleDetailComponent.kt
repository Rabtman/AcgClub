package com.rabtman.acgschedule.di.component

import com.rabtman.acgschedule.di.module.ScheduleDetailModule
import com.rabtman.acgschedule.mvp.ui.activity.ScheduleDetailActivity
import com.rabtman.common.di.component.AppComponent
import com.rabtman.common.di.scope.ActivityScope
import dagger.Component

/**
 * @author Rabtman
 */
@ActivityScope
@Component(modules = [ScheduleDetailModule::class], dependencies = [AppComponent::class])
interface ScheduleDetailComponent {
    fun inject(activity: ScheduleDetailActivity)
}