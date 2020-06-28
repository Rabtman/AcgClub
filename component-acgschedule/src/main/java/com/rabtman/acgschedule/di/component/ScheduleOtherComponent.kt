package com.rabtman.acgschedule.di.component

import com.rabtman.acgschedule.di.module.ScheduleOtherModule
import com.rabtman.acgschedule.mvp.ui.activity.ScheduleOtherActivity
import com.rabtman.common.di.component.AppComponent
import com.rabtman.common.di.scope.ActivityScope
import dagger.Component

/**
 * @author Rabtman
 */
@ActivityScope
@Component(modules = [ScheduleOtherModule::class], dependencies = [AppComponent::class])
interface ScheduleOtherComponent {
    fun inject(activity: ScheduleOtherActivity)
}