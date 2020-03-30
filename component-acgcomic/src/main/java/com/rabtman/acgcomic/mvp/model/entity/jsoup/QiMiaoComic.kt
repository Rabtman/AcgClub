package com.rabtman.acgcomic.mvp.model.entity.jsoup

import android.os.Parcel
import android.os.Parcelable
import com.fcannizzaro.jsoup.annotations.interfaces.Attr
import com.fcannizzaro.jsoup.annotations.interfaces.Items
import com.fcannizzaro.jsoup.annotations.interfaces.Selector
import com.fcannizzaro.jsoup.annotations.interfaces.Text
import java.util.*

/**
 * @author Rabtman
 */
@Selector("div.nmain_cl")
data class QiMiaoComicPage(
        @Items
        var comicItems: List<QiMiaoComicItem>? = null,
        @Text(("div.page-pagination ul li a:containsOwn(>)"))
        var hasMore: String? = ""
) : Parcelable {
    fun hasMore(): Boolean {
        return hasMore.equals(">");
    }

    constructor(source: Parcel) : this(
            source.createTypedArrayList(QiMiaoComicItem.CREATOR),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeTypedList(comicItems)
        writeString(hasMore)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<QiMiaoComicPage> = object : Parcelable.Creator<QiMiaoComicPage> {
            override fun createFromParcel(source: Parcel): QiMiaoComicPage = QiMiaoComicPage(source)
            override fun newArray(size: Int): Array<QiMiaoComicPage?> = arrayOfNulls(size)
        }
    }
}

@Selector("ul#list li")
data class QiMiaoComicItem(var comicId: String = "",
                           @Attr(query = "a img", attr = "data-src")
                           var imgUrl: String = "",
                           @Text("a div.li_div.nmain_cl_tit")
                           var title: String = "",
                           @Text("a div.li_div.nmain_cl_author")
                           var author: String = "",
                           @Text("a div.li_div.nmain_cl_newc p")
                           var now: String = "",
                           var comicLink: String = "") : Parcelable {
    @Attr(query = "a", attr = "href")
    fun comicLink(link: String) {
        comicLink = link
        comicId = link.substring(link.lastIndexOf("/") + 1, link.lastIndexOf("."))
    }

    override fun toString(): String {
        return "ScheduleNewItem(imgUrl=$imgUrl, title=$title, author=$author, now=$now, comicLink=$comicLink)"
    }

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(comicId)
        writeString(imgUrl)
        writeString(title)
        writeString(author)
        writeString(now)
        writeString(comicLink)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<QiMiaoComicItem> = object : Parcelable.Creator<QiMiaoComicItem> {
            override fun createFromParcel(source: Parcel): QiMiaoComicItem = QiMiaoComicItem(source)
            override fun newArray(size: Int): Array<QiMiaoComicItem?> = arrayOfNulls(size)
        }
    }
}

@Selector("div.nmain_con")
data class QiMiaoComicDetail(
        @Attr(query = "div.nmain_com_p1 img", attr = "data-src")
        var imgUrl: String? = null,
        @Text("div.nmain_com_p1 div.ncp1_bac div h1")
        var title: String? = null,
        @Text("div.nmain_com_p1 div.ncp1_bac div span.ncp1b_author")
        var author: String? = null,
        @Text("div.nmain_com_p1 div.ncp1_bac div span.ncp1b_tips")
        var labels: String? = null,
        @Text("div.nmain_com_p.nmain_com_p2 p")
        var desc: String? = null,
        @Items
        var comicChapters: List<QiMiaoComicChapter>? = null
)

@Selector("ul#ncp3_ul li a")
data class QiMiaoComicChapter(
        var chapterId: String = "",
        var comicId: String = "",
        var link: String = "",
        @Text("div.ncp3li_div.ncp3li_title")
        var name: String = "",
        @Text("span.ncp3li_time")
        var date: String? = null
) {
    @Attr(query = "a", attr = "href")
    fun comicLink(link: String) {
        this.link = link
        val split = link.split("/")
        this.comicId = split[split.size - 2]
        val id = split[split.size - 1]
        this.chapterId = id.substring(0, id.indexOf("."))
    }
}

@Selector("body")
data class QiMiaoComicChapterDetail(
        val listImg: MutableList<String> = ArrayList(),
        //var comicId: String = "",
        @Text("header h1.h1")
        var title: String = "",
        var preChapterId: String = "-1",
        var nextChapterId: String = "-1"
) {
    @Attr(query = "div.nmain_conl div.page div.page_div.page_left a", attr = "href")
    fun preChapter(link: String) {
        if (link.contains("html")) {
            val split = link.split("/")
            //this.comicId = split[split.size - 2]
            val id = split[split.size - 1]
            this.preChapterId = id.substring(0, id.indexOf("."))
        }
    }

    @Attr(query = "div.nmain_conl div.page div.page_div.page_right a", attr = "href")
    fun nextChapter(link: String) {
        if (link.contains("html")) {
            val split = link.split("/")
            //this.comicId = split[split.size - 2]
            val id = split[split.size - 1]
            this.nextChapterId = id.substring(0, id.indexOf("."))
        }
    }
}
