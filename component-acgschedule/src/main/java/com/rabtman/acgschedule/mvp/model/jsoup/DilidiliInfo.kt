package com.rabtman.acgschedule.mvp.model.jsoup

import com.fcannizzaro.jsoup.annotations.interfaces.Attr
import com.fcannizzaro.jsoup.annotations.interfaces.Items
import com.fcannizzaro.jsoup.annotations.interfaces.Selector
import com.fcannizzaro.jsoup.annotations.interfaces.Text

@Selector("body")
class DilidiliInfo {
    //轮播栏信息
    @Items
    var scheduleBanners: MutableList<ScheduleBanner>? = null

    //近期推荐
    @Items
    var scheduleRecommends: MutableList<ScheduleRecommend>? = null

    //追番时间表
    @Items
    var scheduleWeek: ArrayList<ScheduleWeek>? = null

    //最近更新
    @Items
    var scheduleRecent: MutableList<ScheduleRecent>? = null

    override fun toString(): String {
        return "DilidiliInfo{" +
                "scheudleBanners=" + scheduleBanners +
                ", scheduleRecommands=" + scheduleRecommends +
                ", scheduleWeek=" + scheduleWeek +
                ", scheduleRecents=" + scheduleRecent +
                '}'
    }

    @Selector("div.swipe ul li")
    class ScheduleBanner {
        @Attr(query = "a img", attr = "src")
        var imgUrl: String? = null

        @Text("a p")
        var name: String? = null

        @Attr(query = "a", attr = "href")
        var animeLink: String? = null

        override fun toString(): String {
            return "ScheudleBanner{" +
                    "imgUrl='" + imgUrl + '\'' +
                    ", name='" + name + '\'' +
                    ", animeLink='" + animeLink + '\'' +
                    '}'
        }
    }

    @Selector("div.edit_list ul li")
    class ScheduleRecommend {
        @Attr(query = "a div", attr = "style")
        private var imgUrl: String? = null

        @Text("a p")
        var name: String? = null

        @Attr(query = "a", attr = "href")
        var animeLink: String? = null

        fun getImgUrl(): String {
            return imgUrl?.let {
                try {
                    it.substring(it.lastIndexOf("(") + 1, it.lastIndexOf(".") + 4)
                } catch (e: Throwable) {
                    ""
                }
            } ?: run {
                ""
            }
        }

        fun setImgUrl(imgUrl: String?) {
            this.imgUrl = imgUrl
        }

        override fun toString(): String {
            return "ScheduleRecommand{" +
                    "imgUrl='" + imgUrl + '\'' +
                    ", name='" + name + '\'' +
                    ", animeLink='" + animeLink + '\'' +
                    '}'
        }
    }

    @Selector("div.list ul li")
    class ScheduleRecent {
        @Attr(query = "div.imgblock", attr = "style")
        private var imgUrl: String? = null

        @Text("a.itemtext")
        var name: String? = null

        @Text("div.itemimgtext")
        var desc: String? = null

        @Attr(query = "div.itemimg a", attr = "href")
        var animeLink: String? = null
        fun getImgUrl(): String {
            return try {
                imgUrl!!.substring(imgUrl!!.lastIndexOf("http"), imgUrl!!.lastIndexOf(".") + 4)
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
        }

        fun setImgUrl(imgUrl: String?) {
            this.imgUrl = imgUrl
        }

        override fun toString(): String {
            return "ScheduleRecent{" +
                    "imgUrl='" + imgUrl + '\'' +
                    ", name='" + name + '\'' +
                    ", desc='" + desc + '\'' +
                    ", animeLink='" + animeLink + '\'' +
                    '}'
        }
    }
}