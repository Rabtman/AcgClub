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

        fun showMoreComicInfos(comicInfos: List<AcgComicItem>, canLoadMore: Boolean)

        fun onLoadMoreFail()
    }

    interface Model : IModel {
        fun getComicInfos(selected: String): Flowable<List<AcgComicItem>>
    }
}
