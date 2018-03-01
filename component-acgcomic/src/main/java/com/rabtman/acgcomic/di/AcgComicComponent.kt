package com.rabtman.acgcomic.di

import com.rabtman.acgcomic.mvp.ui.activity.OacgComicDetailActivity
import com.rabtman.acgcomic.mvp.ui.fragment.DmzjComicFragment
import com.rabtman.acgcomic.mvp.ui.fragment.OacgComicFragment
import com.rabtman.common.di.component.AppComponent
import com.rabtman.common.di.scope.ActivityScope
import com.rabtman.common.di.scope.FragmentScope
import dagger.Component

/**
 * @author Rabtman
 */

@FragmentScope
@Component(modules = arrayOf(DmzjComicModule::class), dependencies = arrayOf(AppComponent::class))
interface DmzjComicComponent {

    fun inject(fragment: DmzjComicFragment)
}

@FragmentScope
@Component(modules = arrayOf(OacgComicModule::class), dependencies = arrayOf(AppComponent::class))
interface OacgComicComponent {

    fun inject(fragment: OacgComicFragment)
}

@ActivityScope
@Component(modules = arrayOf(OacgComicDetailModule::class), dependencies = arrayOf(AppComponent::class))
interface OacgComicDetailComponent {

    fun inject(activity: OacgComicDetailActivity)
}