package com.rabtman.acgpicture.mvp.model

import com.fcannizzaro.jsoup.annotations.JP
import com.rabtman.acgpicture.api.AcgPictureService
import com.rabtman.acgpicture.mvp.AcgPicContract
import com.rabtman.acgpicture.mvp.AnimatePictureContract
import com.rabtman.acgpicture.mvp.model.entity.APicturePage
import com.rabtman.acgpicture.mvp.model.entity.AnimatePicturePage
import com.rabtman.common.base.mvp.BaseModel
import com.rabtman.common.di.scope.FragmentScope
import com.rabtman.common.integration.IRepositoryManager
import io.reactivex.Flowable
import org.jsoup.Jsoup
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

@FragmentScope
class AcgPicModel @Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), AcgPicContract.Model {

    override fun getAPictures(url: String): Flowable<APicturePage> {
        return mRepositoryManager.obtainRetrofitService(AcgPictureService::class.java)
                .getAcgPic(url)
                .map({ body ->
                    val element = Jsoup.parse(body.string())
                    JP.from<APicturePage>(element, APicturePage::class.java)
                })
    }

}
