package com.rabtman.acgschedule.di.component

import com.rabtman.acgschedule.di.module.ScheduleNewModule
import com.rabtman.acgschedule.mvp.ui.activity.ScheduleNewActivity
import com.rabtman.common.di.component.AppComponent
import com.rabtman.common.di.scope.ActivityScope
import dagger.Component

/**
 * @author Rabtman
 */
@ActivityScope
@Component(modules = [ScheduleNewModule::class], dependencies = [AppComponent::class])
interface ScheduleNewComponent {
    fun inject(activity: ScheduleNewActivity)
}