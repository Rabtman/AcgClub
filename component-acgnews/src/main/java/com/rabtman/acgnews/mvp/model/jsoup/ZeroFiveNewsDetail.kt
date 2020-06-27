package com.rabtman.acgnews.mvp.model.jsoup

import com.fcannizzaro.jsoup.annotations.interfaces.ForEach
import com.fcannizzaro.jsoup.annotations.interfaces.Html
import com.fcannizzaro.jsoup.annotations.interfaces.Selector
import org.jsoup.nodes.Element
import java.util.*

/**
 * @author Rabtman
 */
@Selector("div.article-article")
class ZeroFiveNewsDetail {
    var labels = ArrayList<String>()

    @Html("div.articleContent")
    var content: String? = null

    @ForEach("div div span a")
    fun iterate(element: Element, index: Int) {
        labels.add(index, element.text())
    }

    override fun toString(): String {
        return "ZeroFiveNewsDetail{" +
                "labels=" + labels +
                ", content='" + content + '\'' +
                '}'
    }
}