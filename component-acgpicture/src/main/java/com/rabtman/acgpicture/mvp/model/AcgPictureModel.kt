package com.rabtman.acgpicture.mvp.model

import com.rabtman.acgpicture.api.AcgPictureService
import com.rabtman.acgpicture.mvp.AnimatePictureContract
import com.rabtman.acgpicture.mvp.model.entity.AnimatePicturePage
import com.rabtman.common.base.mvp.BaseModel
import com.rabtman.common.di.scope.FragmentScope
import com.rabtman.common.integration.IRepositoryManager
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * @author Rabtman
 */
@FragmentScope
class AnimatePictureModel @Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), AnimatePictureContract.Model {
    override fun getAnimatePictures(pageIndex: Int): Flowable<AnimatePicturePage> {
        return mRepositoryManager.obtainRetrofitService(AcgPictureService::class.java)
                .getAnimatePicture(pageIndex)
    }
}
