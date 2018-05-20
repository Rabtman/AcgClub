package com.rabtman.acgpicture.di

import com.rabtman.acgpicture.mvp.AnimatePictureContract
import com.rabtman.acgpicture.mvp.model.AnimatePictureModel
import com.rabtman.common.di.scope.FragmentScope
import dagger.Module
import dagger.Provides

/**
 * @author Rabtman
 */
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
