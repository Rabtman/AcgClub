package com.rabtman.acgmusic.di


import com.rabtman.acgmusic.mvp.ui.activity.AcgMusicActivity
import com.rabtman.common.di.component.AppComponent
import com.rabtman.common.di.scope.ActivityScope
import dagger.Component

/**
 * @author Rabtman
 */
@ActivityScope
@Component(modules = arrayOf(RandomMusicModule::class), dependencies = arrayOf(AppComponent::class))
interface RandomMusicComponent {

    fun inject(activity: AcgMusicActivity)
}
