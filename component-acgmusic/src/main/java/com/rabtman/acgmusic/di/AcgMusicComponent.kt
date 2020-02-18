package com.rabtman.acgmusic.di


import com.rabtman.acgmusic.mvp.ui.fragment.AcgMusicFragment
import com.rabtman.common.di.component.AppComponent
import com.rabtman.common.di.scope.FragmentScope
import dagger.Component

/**
 * @author Rabtman
 */
@FragmentScope
@Component(modules = arrayOf(RandomMusicModule::class), dependencies = arrayOf(AppComponent::class))
interface RandomMusicComponent {

    fun inject(fragment: AcgMusicFragment)
}
