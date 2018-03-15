package com.rabtman.acgcomic.mvp.model.entity

import com.google.gson.annotations.SerializedName


/**
 * @author Rabtman
 * Oacg漫画数据类
 */

/**
 * 漫画列表信息
 */
data class OacgComicPage(
        @SerializedName("comic_arr") val oacgComicItems: List<OacgComicItem>? = null,
        @SerializedName("page_num") val pageNum: String = "",
        @SerializedName("len") val len: Int = 0
)

/**
 * 指定漫画章节信息
 */
data class OacgComicEpisode(
        @SerializedName("id") val id: String = "",
        @SerializedName("comic_id") val comicId: String = "",
        @SerializedName("order_idx") val orderIdx: String = "",
        @SerializedName("order_title") val orderTitle: String = ""
)

/**
 * 指定章节的内容
 */
data class OacgComicEpisodePage(
        @SerializedName("page_arr") val pageContent: List<PageContent>?,
        @SerializedName("comic_id") val comicId: String = "",
        @SerializedName("comic_name") val comicName: String = "",
        @SerializedName("pre_index") val preIndex: String = "",
        @SerializedName("pre_title") val preTitle: String = "",
        @SerializedName("curr_index") val currIndex: String = "",
        @SerializedName("curr_title") val currTitle: String = "",
        @SerializedName("next_index") val nextIndex: String = "",
        @SerializedName("next_title") val nextTitle: String = ""
) {
    data class PageContent(
            @SerializedName("id") val id: String = "",
            @SerializedName("comic_id") val comicId: String = "",
            @SerializedName("pager_idx") val pagerIdx: String = "",
            @SerializedName("pager_pic") val pagerPic: String = "",
            @SerializedName("pager_pic_width") val pagerPicWidth: String = "",
            @SerializedName("pager_pic_height") val pagerPicHeight: String = ""
    )
}
