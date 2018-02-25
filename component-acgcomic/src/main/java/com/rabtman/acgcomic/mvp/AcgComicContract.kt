package com.rabtman.acgcomic.mvp

import com.rabtman.acgcomic.mvp.model.entity.AcgComicItem
import com.rabtman.common.base.mvp.IModel
import com.rabtman.common.base.mvp.IView
import io.reactivex.Flowable

/**
 * @author Rabtman
 * 漫画模块所有契约类
 */

interface ComicMainContract {

    interface View : IView {
        fun showComicInfos(comicInfos: List<AcgComicItem>)
    }

    interface Model : IModel {
        val comicInfos: Flowable<List<AcgComicItem>>
    }
}
