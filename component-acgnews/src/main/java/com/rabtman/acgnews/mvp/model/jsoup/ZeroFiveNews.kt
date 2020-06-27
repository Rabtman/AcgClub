package com.rabtman.acgnews.mvp.model.jsoup

import android.os.Parcel
import android.os.Parcelable
import com.fcannizzaro.jsoup.annotations.interfaces.Attr
import com.fcannizzaro.jsoup.annotations.interfaces.Selector
import com.fcannizzaro.jsoup.annotations.interfaces.Text

/**
 * @author Rabtman
 */
@Selector("div.article-list ul li")
class ZeroFiveNews : Parcelable {
    @Attr(query = "a img", attr = "src")
    var imgUrl: String? = null

    @Text("div div h3 a")
    var title: String? = null

    @Text("div div div.p-row")
    var description: String? = null

    @Text("div div div span")
    var dateTime: String? = null

    @Attr(query = "a", attr = "href")
    var contentLink: String? = null

    constructor() {}
    protected constructor(`in`: Parcel) {
        imgUrl = `in`.readString()
        title = `in`.readString()
        description = `in`.readString()
        dateTime = `in`.readString()
        contentLink = `in`.readString()
    }

    override fun toString(): String {
        return "ZeroFiveNews{" +
                "imgUrl='" + imgUrl + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", contentLink='" + contentLink + '\'' +
                '}'
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(imgUrl)
        dest.writeString(title)
        dest.writeString(description)
        dest.writeString(dateTime)
        dest.writeString(contentLink)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ZeroFiveNews> = object : Parcelable.Creator<ZeroFiveNews> {
            override fun createFromParcel(source: Parcel): ZeroFiveNews = ZeroFiveNews(source)
            override fun newArray(size: Int): Array<ZeroFiveNews?> = arrayOfNulls(size)
        }
    }
}