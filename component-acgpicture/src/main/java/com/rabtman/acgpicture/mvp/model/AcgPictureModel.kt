package com.rabtman.acgpicture.mvp.model

import com.fcannizzaro.jsoup.annotations.JP
import com.rabtman.acgpicture.api.AcgPictureService
import com.rabtman.acgpicture.api.cache.AcgPictureCacheService
import com.rabtman.acgpicture.mvp.APictureContract
import com.rabtman.acgpicture.mvp.AcgPictureContract
import com.rabtman.acgpicture.mvp.AnimatePictureContract
import com.rabtman.acgpicture.mvp.model.entity.APicturePage
import com.rabtman.acgpicture.mvp.model.entity.AcgPictureItem
import com.rabtman.acgpicture.mvp.model.entity.AnimatePicturePage
import com.rabtman.common.base.mvp.BaseModel
import com.rabtman.common.di.scope.FragmentScope
import com.rabtman.common.integration.IRepositoryManager
import com.rabtman.common.utils.LogUtil
import com.rabtman.common.utils.RxUtil
import io.reactivex.Flowable
import io.rx_cache2.DynamicKey
import org.jsoup.Jsoup
import javax.inject.Inject

/**
 * @author Rabtman
 */

@FragmentScope
class AcgPictureModel @Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), AcgPictureContract.Model {

    override fun getAcgPictures(pageNo: Int, type: String): Flowable<List<AcgPictureItem>> {
        return Flowable.just(mRepositoryManager.obtainRetrofitService(AcgPictureService::class.java)
                .getAcgPictures(pageNo, type)
                .compose(RxUtil.handleResult()))
                .flatMap { picturesFlowable ->
                    mRepositoryManager.obtainCacheService(AcgPictureCacheService::class.java)
                            .getAcgPictures(picturesFlowable, DynamicKey("$type$pageNo"))
                }
    }
}

@FragmentScope
class AnimatePictureModel @Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), AnimatePictureContract.Model {
    override fun getAnimatePictures(pageIndex: Int): Flowable<AnimatePicturePage> {
        return mRepositoryManager.obtainRetrofitService(AcgPictureService::class.java)
                .getAnimatePicture(pageIndex)
    }
}

@FragmentScope
class APictureModel @Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), APictureContract.Model {

    override fun getAPictures(url: String): Flowable<APicturePage> {
        return mRepositoryManager.obtainRetrofitService(AcgPictureService::class.java)
                .getParseResponse(url)
                .map({ body ->
                    val element = Jsoup.parse(body.string()).body()
                    val page = JP.from<APicturePage>(element, APicturePage::class.java)
                    LogUtil.d("a-picture size:" + page.items?.size)
                    page
                })
    }
}
