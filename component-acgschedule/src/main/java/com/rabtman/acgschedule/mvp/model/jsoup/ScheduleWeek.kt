package com.rabtman.acgschedule.mvp.model.jsoup

import android.os.Parcel
import android.os.Parcelable
import com.fcannizzaro.jsoup.annotations.interfaces.Attr
import com.fcannizzaro.jsoup.annotations.interfaces.Items
import com.fcannizzaro.jsoup.annotations.interfaces.Selector
import com.fcannizzaro.jsoup.annotations.interfaces.Text

/**
 * @author Rabtman 追番信息
 */
@Selector("div.tlist ul")
data class ScheduleWeek(
        @Items
        var scheduleItems: List<ScheduleItem>? = null
) : Parcelable {

    constructor(source: Parcel) : this(
            source.createTypedArrayList(ScheduleItem.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeTypedList(scheduleItems)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ScheduleWeek> = object : Parcelable.Creator<ScheduleWeek> {
            override fun createFromParcel(source: Parcel): ScheduleWeek = ScheduleWeek(source)
            override fun newArray(size: Int): Array<ScheduleWeek?> = arrayOfNulls(size)
        }
    }

    @Selector("li")
    data class ScheduleItem(
            @Text("li > a")
            var name: String? = null,

            @Text("span a")
            var episode: String? = null,

            @Attr(query = "li > a", attr = "href")
            var animeLink: String? = null,

            @Attr(query = "span a", attr = "href")
            var episodeLink: String? = null
    ) : Parcelable {
        constructor(source: Parcel) : this(
            source.readString() ?: "",
            source.readString() ?: "",
            source.readString() ?: "",
            source.readString() ?: ""
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeString(name)
            writeString(episode)
            writeString(animeLink)
            writeString(episodeLink)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<ScheduleItem> = object : Parcelable.Creator<ScheduleItem> {
                override fun createFromParcel(source: Parcel): ScheduleItem = ScheduleItem(source)
                override fun newArray(size: Int): Array<ScheduleItem?> = arrayOfNulls(size)
            }
        }
    }
}