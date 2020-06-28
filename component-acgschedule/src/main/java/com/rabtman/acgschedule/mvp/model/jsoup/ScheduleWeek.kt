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
class ScheduleWeek : Parcelable {
    @Items
    var scheduleItems: List<ScheduleItem>? = null

    constructor() {}
    protected constructor(`in`: Parcel) {
        scheduleItems = `in`.createTypedArrayList(ScheduleItem.CREATOR)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeTypedList(scheduleItems)
    }

    override fun toString(): String {
        return "ScheduleWeek{" +
                "scheduleItems=" + scheduleItems +
                '}'
    }

    @Selector("li")
    class ScheduleItem : Parcelable {
        @Text("li > a")
        var name: String? = null

        @Text("span a")
        var episode: String? = null

        @Attr(query = "li > a", attr = "href")
        var animeLink: String? = null

        @Attr(query = "span a", attr = "href")
        var episodeLink: String? = null

        constructor() {}
        protected constructor(`in`: Parcel) {
            name = `in`.readString()
            episode = `in`.readString()
            animeLink = `in`.readString()
            episodeLink = `in`.readString()
        }

        override fun describeContents(): Int {
            return 0
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeString(name)
            dest.writeString(episode)
            dest.writeString(animeLink)
            dest.writeString(episodeLink)
        }

        override fun toString(): String {
            return "ScheduleItem{" +
                    "name='" + name + '\'' +
                    ", episode='" + episode + '\'' +
                    ", animeLink='" + animeLink + '\'' +
                    ", episodeLink='" + episodeLink + '\'' +
                    '}'
        }

        companion object {
            val CREATOR: Parcelable.Creator<ScheduleItem> = object : Parcelable.Creator<ScheduleItem> {
                override fun createFromParcel(source: Parcel): ScheduleItem = ScheduleItem(source)
                override fun newArray(size: Int): Array<ScheduleItem?> = arrayOfNulls(size)
            }
        }
    }

    companion object {
        val CREATOR: Parcelable.Creator<ScheduleWeek> = object : Parcelable.Creator<ScheduleWeek> {
            override fun createFromParcel(source: Parcel): ScheduleWeek = ScheduleWeek(source)
            override fun newArray(size: Int): Array<ScheduleWeek?> = arrayOfNulls(size)
        }
    }
}