package com.rabtman.acgpicture.di

import com.rabtman.acgpicture.mvp.ui.fragment.APictureFragment
import com.rabtman.acgpicture.mvp.ui.fragment.AcgPictureItemFragment
import com.rabtman.acgpicture.mvp.ui.fragment.AcgPictureMainFragment
import com.rabtman.acgpicture.mvp.ui.fragment.AnimatePictureFragment
import com.rabtman.common.di.component.AppComponent
import com.rabtman.common.di.scope.FragmentScope
import dagger.Component

/**
 * @author Rabtman
 */

@FragmentScope
@Component(modules = arrayOf(AcgPictureMainModule::class), dependencies = arrayOf(AppComponent::class))
interface AcgPictureMainComponent {

    fun inject(fragment: AcgPictureMainFragment)
}

@FragmentScope
@Component(modules = arrayOf(AcgPictureModule::class), dependencies = arrayOf(AppComponent::class))
interface AcgPictureComponent {

    fun inject(fragment: AcgPictureItemFragment)
}

@FragmentScope
@Component(modules = arrayOf(AnimatePictureModule::class), dependencies = arrayOf(AppComponent::class))
interface AnimatePictureComponent {

    fun inject(fragment: AnimatePictureFragment)
}

@FragmentScope
@Component(modules = arrayOf(APictureModule::class), dependencies = arrayOf(AppComponent::class))
interface APictureComponent {

    fun inject(fragment: APictureFragment)
}
