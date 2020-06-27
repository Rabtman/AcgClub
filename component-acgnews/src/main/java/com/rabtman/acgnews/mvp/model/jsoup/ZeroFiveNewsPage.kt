package com.rabtman.acgnews.mvp.model.jsoup

import com.fcannizzaro.jsoup.annotations.interfaces.Attr
import com.fcannizzaro.jsoup.annotations.interfaces.Items
import com.fcannizzaro.jsoup.annotations.interfaces.Selector

/**
 * @author Rabtman
 */
@Selector("div.w article-list-page clearfix")
class ZeroFiveNewsPage {
    @Items
    var zeroFiveNewsList: List<ZeroFiveNews>? = null

    @Attr(query = "div div.pages div ul li a:last-child", attr = "href")
    private var pageCount: String? = null

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
        return "ZeroFiveNewsPage{" +
                "zeroFiveNewsList=" + zeroFiveNewsList +
                ", pageCount=" + pageCount +
                '}'
    }
}