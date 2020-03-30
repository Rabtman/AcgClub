package com.rabtman.acgcomic.mvp

import com.rabtman.acgcomic.mvp.model.entity.DmzjComicItem
import com.rabtman.acgcomic.mvp.model.entity.db.ComicCache
import com.rabtman.acgcomic.mvp.model.entity.jsoup.QiMiaoComicChapterDetail
import com.rabtman.acgcomic.mvp.model.entity.jsoup.QiMiaoComicDetail
import com.rabtman.acgcomic.mvp.model.entity.jsoup.QiMiaoComicItem
import com.rabtman.acgcomic.mvp.model.entity.jsoup.QiMiaoComicPage
import com.rabtman.common.base.mvp.IModel
import com.rabtman.common.base.mvp.IView
import io.reactivex.Flowable

/**
 * @author Rabtman
 * 漫画模块所有契约类
 */

interface DmzjComicContract {

    interface View : IView {
        fun showComicInfo(comicInfos: List<DmzjComicItem>)

        fun showMoreComicInfo(comicInfos: List<DmzjComicItem>, canLoadMore: Boolean)

        fun onLoadMoreFail()
    }

    interface Model : IModel {
        fun getComicInfo(selected: String): Flowable<List<DmzjComicItem>>
    }
}

interface QiMiaoComicContract {

    interface View : IView {
        fun showComicInfo(comicInfos: List<QiMiaoComicItem>?)

        fun showMoreComicInfo(comicInfos: List<QiMiaoComicItem>?, canLoadMore: Boolean)

        fun showSearchComicInfo(comicInfos: List<QiMiaoComicItem>?, canLoadMore: Boolean)

        fun resetComicMenu()

        fun onLoadMoreFail()
    }

    interface Model : IModel {
        fun getComicInfo(keyword: String, pageNo: Int, isSearch: Boolean): Flowable<QiMiaoComicPage>

        fun getSearchComicInfo(keyword: String, pageNo: Int): Flowable<QiMiaoComicPage>
    }
}

interface QiMiaoComicDetailContract {

    interface View : IView {
        fun showComicDetail(comicDetail: QiMiaoComicDetail)

        fun showComicCacheStatus(comicCache: ComicCache)

        fun start2ComicRead(id: String, lastChapterId: String)
    }

    interface Model : IModel {
        fun getComicDetail(detailUrl: String): Flowable<QiMiaoComicDetail>

        fun getComicCacheById(comicId: String): Flowable<ComicCache>

        fun collectComic(comicItem: QiMiaoComicItem, isAdd: Boolean): Flowable<ComicCache>

        fun updateComicLastChapter(comicId: String, lastChapterPos: Int): Flowable<ComicCache>
    }
}

interface QiMIaoComicChapterDetailContract {

    interface View : IView {
        fun showChapterDetail(chapterDetail: QiMiaoComicChapterDetail, lastPagePos: Int)
    }

    interface Model : IModel {
        fun getChapterDetail(comicId: String, chapterId: String): Flowable<QiMiaoComicChapterDetail>

        fun getComicCacheByChapter(comicId: String, chapterPos: Int): Flowable<ComicCache>

        /**
         * 保存最近一次漫画观看记录
         */
        fun updateComicLastRecord(comicId: String, lastChapterPos: Int, lastPagePos: Int): Flowable<ComicCache>
    }
}

