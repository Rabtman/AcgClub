package com.rabtman.acgcomic.di

import com.rabtman.acgcomic.mvp.ui.fragment.AcgComicMainFragment
import com.rabtman.common.di.component.AppComponent
import com.rabtman.common.di.scope.FragmentScope
import dagger.Component

/**
 * @author Rabtman
 */

@FragmentScope
@Component(modules = arrayOf(ComicMainModule::class), dependencies = arrayOf(AppComponent::class))
interface ComicMainComponent {

    fun inject(fragment: AcgComicMainFragment)
}