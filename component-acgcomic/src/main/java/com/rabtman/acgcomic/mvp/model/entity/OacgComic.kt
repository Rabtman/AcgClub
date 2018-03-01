package com.rabtman.acgcomic.mvp.model.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


/**
 * @author Rabtman
 * Oacg漫画数据类
 */

/**
 * 漫画列表信息
 */
data class OacgComicPage(
        @SerializedName("comic_arr") val oacgComicItems: List<OacgComicItem> = listOf(),
        @SerializedName("page_num") val pageNum: String = ""
)

data class OacgComicItem(
        @SerializedName("id") val id: String = "",
        @SerializedName("comic_name") val comicName: String = "",
        @SerializedName("comic_pic_240_320") val comicPicUrl: String = "",
        @SerializedName("comic_desc") val comicDesc: String = "",
        @SerializedName("comic_tag_name") val comicTagName: String = "",
        @SerializedName("painter_user_nickname") val painterUserNickname: String = "",
        @SerializedName("script_user_nickname") val scriptUserNickname: String = "",
        @SerializedName("click_score") val clickScore: String = "",
        @SerializedName("comic_last_orderidx") val comicLastOrderidx: String = "",
        @SerializedName("comic_theme_id_1") val comicThemeId1: String = "",
        @SerializedName("comic_theme_id_2") val comicThemeId2: String = "",
        @SerializedName("comic_theme_id_3") val comicThemeId3: String = ""
) : Parcelable {
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
 * 指定漫画详情信息
 */
data class OacgComicDetail(
        @SerializedName("id") val id: String = "",
        @SerializedName("comic_id") val comicId: String = "",
        @SerializedName("order_idx") val orderIdx: String = "",
        @SerializedName("order_title") val orderTitle: String = ""
)