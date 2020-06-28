package com.rabtman.acgschedule.mvp.model.jsoup

import com.fcannizzaro.jsoup.annotations.interfaces.*
import org.jsoup.nodes.Element

/**
 * @author Rabtman
 */
@Selector("div.area")
class ScheduleNew {
    @Items
    var scheduleNewItems: List<ScheduleNewItem>? = null

    override fun toString(): String {
        return "ScheduleNew{" +
                "scheduleNewItems=" + scheduleNewItems +
                '}'
    }

    @Selector("div.pics ul li")
    class ScheduleNewItem {
        @Attr(query = "a img", attr = "src")
        var imgUrl: String? = null

        @Text("h2 a")
        var title: String? = null

        @Text("span:containsOwn(状态)")
        var spot: String? = null

        //@Text("a p.kandian span:containsOwn(类型)")
        var type = "类型："

        @Text("p")
        var desc: String? = null

        @Attr(query = "a", attr = "href")
        var animeLink: String? = null

        @ForEach("span:containsOwn(类型) a")
        fun labels(element: Element, index: Int) {
            type += element.text() + " "
        }

        override fun toString(): String {
            return "ScheduleNewItem{" +
                    "imgUrl='" + imgUrl + '\'' +
                    ", title='" + title + '\'' +
                    ", spot='" + spot + '\'' +
                    ", type='" + type + '\'' +
                    ", desc='" + desc + '\'' +
                    ", animeLink='" + animeLink + '\'' +
                    '}'
        }
    }
}