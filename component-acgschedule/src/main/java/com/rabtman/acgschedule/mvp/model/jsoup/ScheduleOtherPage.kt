package com.rabtman.acgschedule.mvp.model.jsoup

import com.fcannizzaro.jsoup.annotations.interfaces.Attr
import com.fcannizzaro.jsoup.annotations.interfaces.Items
import com.fcannizzaro.jsoup.annotations.interfaces.Selector
import com.fcannizzaro.jsoup.annotations.interfaces.Text

/**
 * @author Rabtman 其他类别视频信息
 */
@Selector("div.container")
class ScheduleOtherPage {
    @Text("div div.title-box div h2")
    var title: String? = null

    @Attr(query = "div div div.img3Wrap ul ul.pagelistbox li a", attr = "href")
    private var pageCount: String? = null

    @Items
    var scheduleOtherItems: List<ScheduleOtherItem>? = null

    fun getPageCount(): Int {
        pageCount?.let {
            it.substring(it.lastIndexOf("_") + 1, it.lastIndexOf(".")).toIntOrNull()?.let { count ->
                return count
            } ?: run {
                return 1
            }
        } ?: run {
            return 1
        }
    }

    fun setPageCount(pageCount: String?) {
        this.pageCount = pageCount
    }

    override fun toString(): String {
        return "ScheduleOtherPage{" +
                "title='" + title + '\'' +
                ", pageCount='" + pageCount + '\'' +
                ", scheduleOtherItems=" + scheduleOtherItems +
                '}'
    }

    @Selector("div div div.img3Wrap ul li:has(h4)")
    class ScheduleOtherItem {
        @Attr(query = "a img", attr = "src")
        var imgUrl: String? = null

        @Text("a h4")
        var title: String? = null

        @Attr(query = "a", attr = "href")
        var videolLink: String? = null

        override fun toString(): String {
            return "ScheduleOtherItem{" +
                    "imgUrl='" + imgUrl + '\'' +
                    ", title='" + title + '\'' +
                    ", videolLink='" + videolLink + '\'' +
                    '}'
        }
    }
}