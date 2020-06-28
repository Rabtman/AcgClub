package com.rabtman.acgschedule.mvp.model.jsoup

import com.fcannizzaro.jsoup.annotations.interfaces.Attr
import com.fcannizzaro.jsoup.annotations.interfaces.Html
import com.fcannizzaro.jsoup.annotations.interfaces.Selector

/**
 * @author Rabtman
 */
@Selector("body")
class ScheduleVideo {
    @Html("div.player")
    var videoHtml: String? = null

    @Attr(query = "div.player div#playbox", attr = "data-vid")
    var videoUrl: String? = null

    override fun toString(): String {
        return "ScheduleVideo{" +
                "videoHtml='" + videoHtml + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                '}'
    }
}