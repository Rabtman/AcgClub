package com.rabtman.acgcomic.mvp

import com.rabtman.acgcomic.mvp.model.entity.DmzjComicItem
import com.rabtman.acgcomic.mvp.model.entity.OacgComicDetail
import com.rabtman.acgcomic.mvp.model.entity.OacgComicItem
import com.rabtman.acgcomic.mvp.model.entity.OacgComicPage
import com.rabtman.common.base.mvp.IModel
import com.rabtman.common.base.mvp.IView
import io.reactivex.Flowable

/**
 * @author Rabtman
 * 漫画模块所有契约类
 */

interface DmzjComicContract {

    interface View : IView {
        fun showComicInfos(comicInfos: List<DmzjComicItem>)

        fun showMoreComicInfos(comicInfos: List<DmzjComicItem>, canLoadMore: Boolean)

        fun onLoadMoreFail()
    }

    interface Model : IModel {
        fun getComicInfos(selected: String): Flowable<List<DmzjComicItem>>
    }
}

interface OacgComicContract {

    interface View : IView {
        fun showComicInfos(comicInfos: List<OacgComicItem>)

        fun showMoreComicInfos(comicInfos: List<OacgComicItem>, canLoadMore: Boolean)

        fun onLoadMoreFail()
    }

    interface Model : IModel {
        fun getComicInfos(themeId: Int, pageNo: Int): Flowable<OacgComicPage>
    }
}

interface OacgComicDetailContract {

    interface View : IView {
        fun showComicDetail(comicInfos: List<OacgComicDetail>)
    }

    interface Model : IModel {
        fun getComicDetail(comicId: Int): Flowable<List<OacgComicDetail>>
    }
}
