package com.rabtman.acgcomic.mvp

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
    override val comicInfos: Flowable<List<AcgComicItem>>
        get() = TODO("not implemented")
}