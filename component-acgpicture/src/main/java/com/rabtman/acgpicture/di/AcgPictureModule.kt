package com.rabtman.acgpicture.di

import com.rabtman.acgpicture.mvp.APictureContract
import com.rabtman.acgpicture.mvp.AcgPictureContract
import com.rabtman.acgpicture.mvp.AcgPictureMainContract
import com.rabtman.acgpicture.mvp.AnimatePictureContract
import com.rabtman.acgpicture.mvp.model.APictureModel
import com.rabtman.acgpicture.mvp.model.AcgPictureMainModel
import com.rabtman.acgpicture.mvp.model.AcgPictureModel
import com.rabtman.acgpicture.mvp.model.AnimatePictureModel
import com.rabtman.common.di.scope.FragmentScope
import dagger.Module
import dagger.Provides

/**
 * @author Rabtman
 */

@Module
class AcgPictureMainModule(private val view: AcgPictureMainContract.View) {

    @FragmentScope
    @Provides
    internal fun providerAcgPictureMainView(): AcgPictureMainContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    internal fun providerAcgPictureMainModel(model: AcgPictureMainModel): AcgPictureMainContract.Model {
        return model
    }
}

@Module
class AcgPictureModule(private val view: AcgPictureContract.View) {

    @FragmentScope
    @Provides
    internal fun providerAcgPictureView(): AcgPictureContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    internal fun providerAcgPictureModel(model: AcgPictureModel): AcgPictureContract.Model {
        return model
    }
}

@Module
class AnimatePictureModule(private val view: AnimatePictureContract.View) {

    @FragmentScope
    @Provides
    internal fun providerAnimatePictureView(): AnimatePictureContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    internal fun providerAnimatePictureModel(model: AnimatePictureModel): AnimatePictureContract.Model {
        return model
    }
}

@Module
class APictureModule(private val view: APictureContract.View) {

    @FragmentScope
    @Provides
    internal fun providerAPictureView(): APictureContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    internal fun providerAPictureModel(model: APictureModel): APictureContract.Model {
        return model
    }
}
