package com.rabtman.acgcomic.mvp

import com.rabtman.acgcomic.mvp.model.entity.*
import com.rabtman.acgcomic.mvp.model.entity.db.ComicCache
import com.rabtman.common.base.mvp.IModel
import com.rabtman.common.base.mvp.IView
import io.reactivex.Completable
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
        fun showComicInfos(comicInfos: List<OacgComicItem>?)

        fun showMoreComicInfos(comicInfos: List<OacgComicItem>?, canLoadMore: Boolean?)

        fun showSearchComicInfos(comicInfos: List<OacgComicItem>?)

        fun onLoadMoreFail()
    }

    interface Model : IModel {
        fun getComicInfos(themeId: Int, pageNo: Int): Flowable<OacgComicPage>

        fun getSearchComicInfos(keyword: String): Flowable<OacgComicPage>
    }
}

interface OacgComicDetailContract {

    interface View : IView {
        fun showComicDetail(comicInfos: List<OacgComicEpisode>?)

        fun showComicCacheStatus(comicCache: ComicCache)

        fun start2ComicRead(id: String, lastChapterIndex: String)
    }

    interface Model : IModel {
        fun getComicDetail(comicId: Int): Flowable<List<OacgComicEpisode>>

        fun getComicCacheById(comicId: String): Flowable<ComicCache>

        fun collectComic(comicItem: OacgComicItem, isAdd: Boolean): Completable

        fun updateComicLastChapter(comicId: String, lastChapterPos: Int): Completable
    }
}

interface OacgComicEpisodeDetailContract {

    interface View : IView {
        fun showEpisodeDetail(episodePage: OacgComicEpisodePage, lastPagePos: Int)
    }

    interface Model : IModel {
        fun getEpisodeDetail(comicId: Int, chapterIndex: Int): Flowable<OacgComicEpisodePage>

        fun getComicCacheByChapter(comicId: String, chapterPos: Int): Flowable<ComicCache>

        /**
         * 保存最近一次漫画观看记录
         */
        fun updateComicLastRecord(comicId: String, lastChapterPos: Int, lastPagePos: Int): Completable
    }
}

