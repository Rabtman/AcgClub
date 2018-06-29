package com.rabtman.acgpicture.mvp.model.entity

import com.fcannizzaro.jsoup.annotations.interfaces.Attr
import com.fcannizzaro.jsoup.annotations.interfaces.Items
import com.fcannizzaro.jsoup.annotations.interfaces.Selector
import com.fcannizzaro.jsoup.annotations.interfaces.Text
import com.google.gson.annotations.SerializedName


/**
 * @author Rabtman
 * acg图数据类
 */

/**
 * animate-picture页面信息
 */
data class AnimatePicturePage(
        @SerializedName("posts_per_page") val postsPerPage: Int = 0,
        @SerializedName("response_posts_count") val responsePostsCount: Int = 0,
        @SerializedName("page_number") val pageNumber: Int = 0,
        @SerializedName("posts") val animatePictureItems: List<AnimatePictureItem>? = null,
        @SerializedName("posts_count") val postsCount: Int = 0,
        @SerializedName("max_pages") val maxPages: Int = 0
)

/**
 * animate-picture图片信息
 */
data class AnimatePictureItem(
        @SerializedName("id") val id: Int = 0,
        @SerializedName("md5") val md5: String = "",
        @SerializedName("md5_pixels") val md5Pixels: String = "",
        @SerializedName("width") val width: Int = 0,
        @SerializedName("height") val height: Int = 0,
        @SerializedName("small_preview") val smallPreview: String = "",
        @SerializedName("medium_preview") val mediumPreview: String = "",
        @SerializedName("big_preview") val bigPreview: String = "",
        @SerializedName("pubtime") val pubtime: String = "",
        @SerializedName("score") val score: Int = 0,
        @SerializedName("score_number") val scoreNumber: Int = 0,
        @SerializedName("size") val size: Int = 0,
        @SerializedName("download_count") val downloadCount: Int = 0,
        @SerializedName("erotics") val erotics: Int = 0,
        @SerializedName("color") val color: List<Int>? = null,
        @SerializedName("ext") val ext: String = "",
        @SerializedName("status") val status: Int = 0
)

@Selector("main")
class APicturePage {
    @Items
    var items: List<APictureItem>? = null
    @Attr(query = "div#page a.extend:containsOwn(尾页)", attr = "href")
    private val pageCount: String? = null

    fun getPageCount(): Int {
        val count = pageCount!!.substring(pageCount.lastIndexOf("/") + 1)
        return try {
            Integer.parseInt(count)
        } catch (e: Exception) {
            e.printStackTrace()
            1
        }
    }
}

@Selector("div.grid-bor div")
class APictureItem {

    @Text("div div div h2.entry-title a")
    var title: String = ""
    @Attr(query = "div div div div", attr = "style")
    var thumbUrl: String = ""
    @Attr(query = "div div div h2.entry-title a", attr = "href")
    var contentLink: String = ""
    @Text("div div.num")
    var count: String = ""
}

