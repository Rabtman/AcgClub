package com.rabtman.acgcomic.mvp.model.entity.db

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * @author Rabtman
 */

/**
 * 漫画本地储存信息
 * @param comicUrl 漫画地址
 * @param comicName 漫画名
 * @param comicImgUrl 漫画封面地址
 * @param comicDetailJson 漫画详细内容json
 * @param comicSource 漫画来源,当前来源有：oacg
 * @param isCollect 是否收藏
 * @param chapterPos 历史章节位置
 * @param pagePos 历史章节观看的页面位置
 */
open class ComicCache(
        @PrimaryKey
        var comicId: String = "",
        var comicName: String = "",
        var comicImgUrl: String = "",
        var comicDetailJson: String = "",
        var comicSource: String = "",
        var isCollect: Boolean = false,
        var chapterPos: Int = 0,
        var pagePos: Int = 0
) : RealmObject() {
    override fun toString(): String {
        return "ComicCache(comicId='$comicId', comicName='$comicName', comicImgUrl='$comicImgUrl', comicDetailJson='$comicDetailJson', comicSource='$comicSource', isCollect=$isCollect, chapterPos=$chapterPos, pagePos=$pagePos)"
    }
}