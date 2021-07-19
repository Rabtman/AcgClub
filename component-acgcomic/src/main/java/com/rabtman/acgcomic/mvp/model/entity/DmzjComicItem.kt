package com.rabtman.acgcomic.mvp.model.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


/**
 * @author Rabtman
 */

/**
 * 动漫之家漫画信息
 */
data class DmzjComicItem(
        @SerializedName("id") val id: Int = 0,
        @SerializedName("name") val name: String = "",
        @SerializedName("zone") val zone: String = "",
        @SerializedName("status") val status: String = "",
        @SerializedName("last_update_chapter_name") val lastUpdateChapterName: String = "",
        @SerializedName("last_update_chapter_id") val lastUpdateChapterId: Int = 0,
        @SerializedName("last_updatetime") val lastUpdatetime: Int = 0,
        @SerializedName("hidden") val hidden: Int = 0,
        @SerializedName("cover") val cover: String = "",
        @SerializedName("first_letter") val firstLetter: String = "",
        @SerializedName("comic_py") val comicPy: String = "",
        @SerializedName("authors") val authors: String = "",
        @SerializedName("types") val types: String = "",
        @SerializedName("readergroup") val readergroup: String = "",
        @SerializedName("copyright") val copyright: Int = 0,
        @SerializedName("hot_hits") val hotHits: Int = 0,
        @SerializedName("app_click_count") val appClickCount: Int = 0,
        @SerializedName("num") val num: String = ""
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString() ?: "",
        source.readString() ?: "",
        source.readString() ?: "",
        source.readString() ?: "",
        source.readInt(),
        source.readInt(),
        source.readInt(),
        source.readString() ?: "",
        source.readString() ?: "",
        source.readString() ?: "",
        source.readString() ?: "",
        source.readString() ?: "",
        source.readString() ?: "",
        source.readInt(),
        source.readInt(),
        source.readInt(),
        source.readString() ?: ""
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(name)
        writeString(zone)
        writeString(status)
        writeString(lastUpdateChapterName)
        writeInt(lastUpdateChapterId)
        writeInt(lastUpdatetime)
        writeInt(hidden)
        writeString(cover)
        writeString(firstLetter)
        writeString(comicPy)
        writeString(authors)
        writeString(types)
        writeString(readergroup)
        writeInt(copyright)
        writeInt(hotHits)
        writeInt(appClickCount)
        writeString(num)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<DmzjComicItem> = object : Parcelable.Creator<DmzjComicItem> {
            override fun createFromParcel(source: Parcel): DmzjComicItem = DmzjComicItem(source)
            override fun newArray(size: Int): Array<DmzjComicItem?> = arrayOfNulls(size)
        }
    }
}
