package com.rabtman.acgcomic.mvp.model.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


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

open class OacgComicItem(
        @PrimaryKey @SerializedName("id") var id: String = "",
        @SerializedName("comic_name") var comicName: String = "",
        @SerializedName("comic_pic_240_320") var comicPicUrl: String = "",
        @SerializedName("comic_desc") var comicDesc: String = "",
        @SerializedName("comic_tag_name") var comicTagName: String = "",
        @SerializedName("painter_user_nickname") var painterUserNickname: String = "",
        @SerializedName("script_user_nickname") var scriptUserNickname: String = "",
        @SerializedName("click_score") var clickScore: String = "",
        @SerializedName("comic_last_orderidx") var comicLastOrderidx: String = "",
        @SerializedName("comic_theme_id_1") var comicThemeId1: String = "",
        @SerializedName("comic_theme_id_2") var comicThemeId2: String = "",
        @SerializedName("comic_theme_id_3") var comicThemeId3: String = ""
) : RealmObject(), Parcelable {

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(comicName)
        writeString(comicPicUrl)
        writeString(comicDesc)
        writeString(comicTagName)
        writeString(painterUserNickname)
        writeString(scriptUserNickname)
        writeString(clickScore)
        writeString(comicLastOrderidx)
        writeString(comicThemeId1)
        writeString(comicThemeId2)
        writeString(comicThemeId3)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<OacgComicItem> = object : Parcelable.Creator<OacgComicItem> {
            override fun createFromParcel(source: Parcel): OacgComicItem = OacgComicItem(source)
            override fun newArray(size: Int): Array<OacgComicItem?> = arrayOfNulls(size)
        }
    }
}

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
