package com.rabtman.acgnews.mvp.model.entity

import android.os.Parcel
import android.os.Parcelable

/**
 * @author Rabtman ishuhui post item
 */
data class SHPostItem(
        var id: Int = 0,
        var title: String? = null,
        var brief: String? = null,
        var thumb: String? = null,
        var sticky: Int = 0,
        var time: Long = 0,
        var authorID: String? = null,
        var sourceID: String? = null,
        var authorName: String? = null,
        var categoryIDs: String? = null,
        var sourceName: String? = null
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readInt(),
            source.readLong(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(title)
        writeString(brief)
        writeString(thumb)
        writeInt(sticky)
        writeLong(time)
        writeString(authorID)
        writeString(sourceID)
        writeString(authorName)
        writeString(categoryIDs)
        writeString(sourceName)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<SHPostItem> = object : Parcelable.Creator<SHPostItem> {
            override fun createFromParcel(source: Parcel): SHPostItem = SHPostItem(source)
            override fun newArray(size: Int): Array<SHPostItem?> = arrayOfNulls(size)
        }
    }
}