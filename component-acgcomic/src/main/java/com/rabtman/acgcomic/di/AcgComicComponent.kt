package com.rabtman.acgcomic.di

import com.rabtman.acgcomic.mvp.ui.activity.QiMiaoComicDetailActivity
import com.rabtman.acgcomic.mvp.ui.activity.QiMiaoComicReadActivity
import com.rabtman.acgcomic.mvp.ui.fragment.DmzjComicFragment
import com.rabtman.acgcomic.mvp.ui.fragment.QiMiaoComicFragment
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
@Component(modules = arrayOf(QiMiaoComicModule::class), dependencies = arrayOf(AppComponent::class))
interface QiMiaoComicComponent {

    fun inject(fragment: QiMiaoComicFragment)
}

@ActivityScope
@Component(modules = arrayOf(QiMiaoComicDetailModule::class), dependencies = arrayOf(AppComponent::class))
interface QiMiaoComicDetailComponent {

    fun inject(activity: QiMiaoComicDetailActivity)
}

@ActivityScope
@Component(modules = arrayOf(QiMiaoComicEpisodeDetailModule::class), dependencies = arrayOf(AppComponent::class))
interface QiMiaoComicEpisodeDetailComponent {

    fun inject(activity: QiMiaoComicReadActivity)
}