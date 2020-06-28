package com.rabtman.acgschedule.di.component

import com.rabtman.acgschedule.di.module.ScheduleMainModule
import com.rabtman.acgschedule.mvp.ui.fragment.ScheduleMainFragment
import com.rabtman.common.di.component.AppComponent
import com.rabtman.common.di.scope.FragmentScope
import dagger.Component

/**
 * @author Rabtman
 */
@FragmentScope
@Component(modules = [ScheduleMainModule::class], dependencies = [AppComponent::class])
interface ScheduleMainComponent {
    fun inject(fragment: ScheduleMainFragment)
}