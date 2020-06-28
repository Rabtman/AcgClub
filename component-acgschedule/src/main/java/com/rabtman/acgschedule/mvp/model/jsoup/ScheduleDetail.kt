package com.rabtman.acgschedule.mvp.model.jsoup

import com.fcannizzaro.jsoup.annotations.interfaces.*
import org.jsoup.nodes.Element

/**
 * @author Rabtman
 */
@Selector("body")
class ScheduleDetail {
    @Attr(query = "div.list div.show img", attr = "src")
    private var imgUrl: String? = null

    @Text("div.list div.show h1")
    var scheduleTitle: String? = null

    @Text("div.list div.show p:contains(连载)")
    var scheduleProc: String? = null

    @Text("div.list div.show p:contains(上映)")
    var scheduleTime: String? = null

    //@Text("div.list div.show p:contains(类型)")
    var scheduleAera: String? = null

    //@Text("div.list div.show p:contains(类型)")
    var scheduleLabel = "类型："

    @Text("div.info")
    var description: String? = null

    @Items
    var scheduleEpisodes: MutableList<ScheduleEpisode>? = null

    @ForEach("div.list div.show p:contains(类型) a")
    fun labels(element: Element, index: Int) {
        if (index == 0) {
            scheduleAera = "地区：" + element.text()
        } else {
            scheduleLabel += element.text() + " "
        }
    }

    fun getImgUrl(): String {
        return try {
            imgUrl!!.substring(imgUrl!!.lastIndexOf("(") + 1, imgUrl!!.lastIndexOf(".") + 4)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    fun setImgUrl(imgUrl: String?) {
        this.imgUrl = imgUrl
    }

    override fun toString(): String {
        return "ScheduleDetail{" +
                "imgUrl='" + imgUrl + '\'' +
                ", scheduleTitle='" + scheduleTitle + '\'' +
                ", scheduleProc='" + scheduleProc + '\'' +
                ", scheduleTime='" + scheduleTime + '\'' +
                ", scheduleAera='" + scheduleAera + '\'' +
                ", scheduleLabel='" + scheduleLabel + '\'' +
                ", description='" + description + '\'' +
                ", scheduleEpisodes=" + scheduleEpisodes +
                '}'
    }

    @Selector("div#playlists ul li")
    class ScheduleEpisode {
        @Text("a")
        var name: String? = null

        @Attr(query = "a", attr = "href")
        var link: String? = null

        override fun toString(): String {
            return "ScheduleEpisode{" +
                    "name='" + name + '\'' +
                    ", link='" + link + '\'' +
                    '}'
        }
    }
}