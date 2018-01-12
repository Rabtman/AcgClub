package com.rabtman.acgnews.di.module.jsoup;

import com.fcannizzaro.jsoup.annotations.interfaces.Attr;
import com.fcannizzaro.jsoup.annotations.interfaces.Items;
import com.fcannizzaro.jsoup.annotations.interfaces.Selector;
import java.util.List;

/**
 * @author Rabtman
 */
@Selector("div.w article-list-page clearfix")
public class AcgNewsPage {

  @Items
  private List<AcgNews> acgNewsList;
  @Attr(query = "div div.pages div ul li a:last-child", attr = "href")
  private String pageCount;

  public List<AcgNews> getAcgNewsList() {
    return acgNewsList;
  }

  public void setAcgNewsList(List<AcgNews> acgNewsList) {
    this.acgNewsList = acgNewsList;
  }

  public String getPageCount() {
    return pageCount.substring(pageCount.lastIndexOf("_") + 1, pageCount.lastIndexOf("."));
  }

  public void setPageCount(String pageCount) {
    this.pageCount = pageCount;
  }

  @Override
  public String toString() {
    return "AcgNewsPage{" +
        "acgNewsList=" + acgNewsList +
        ", pageCount=" + pageCount +
        '}';
  }
}
