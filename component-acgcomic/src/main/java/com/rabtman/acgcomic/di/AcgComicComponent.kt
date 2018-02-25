package com.rabtman.acgcomic.di

import com.rabtman.common.di.component.AppComponent
import com.rabtman.common.di.scope.FragmentScope
import dagger.Component

/**
 * @author Rabtman
 */

@FragmentScope
@Component(modules = arrayOf(ComicMainModule::class), dependencies = arrayOf(AppComponent::class))
interface ComicMainComponent {

    fun inject(fragment: comicmainFragment)
}