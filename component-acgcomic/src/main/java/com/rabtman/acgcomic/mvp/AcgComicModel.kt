package com.rabtman.acgcomic.mvp

import com.rabtman.acgcomic.api.AcgComicService
import com.rabtman.acgcomic.mvp.model.entity.AcgComicItem
import com.rabtman.common.base.mvp.BaseModel
import com.rabtman.common.di.scope.FragmentScope
import com.rabtman.common.integration.IRepositoryManager
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * @author Rabtman
 */
@FragmentScope
class ComicMainModel @Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), ComicMainContract.Model {

    override fun getComicInfos(selected: String): Flowable<List<AcgComicItem>> {
        return mRepositoryManager.obtainRetrofitService(AcgComicService::class.java)
                .getComicList(selected)
    }

}