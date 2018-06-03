package com.rabtman.acgnews.mvp.model.jsoup;

import com.fcannizzaro.jsoup.annotations.interfaces.Attr;
import com.fcannizzaro.jsoup.annotations.interfaces.Items;
import com.fcannizzaro.jsoup.annotations.interfaces.Selector;
import java.util.List;

/**
 * @author Rabtman
 */
@Selector("div.w article-list-page clearfix")
public class ZeroFiveNewsPage {

  @Items
  private List<ZeroFiveNews> zeroFiveNewsList;
  @Attr(query = "div div.pages div ul li a:last-child", attr = "href")
  private String pageCount;

  public List<ZeroFiveNews> getZeroFiveNewsList() {
    return zeroFiveNewsList;
  }

  public void setZeroFiveNewsList(List<ZeroFiveNews> zeroFiveNewsList) {
    this.zeroFiveNewsList = zeroFiveNewsList;
  }

  public String getPageCount() {
    return pageCount.substring(pageCount.lastIndexOf("_") + 1, pageCount.lastIndexOf("."));
  }

  public void setPageCount(String pageCount) {
    this.pageCount = pageCount;
  }

  @Override
  public String toString() {
    return "ZeroFiveNewsPage{" +
        "zeroFiveNewsList=" + zeroFiveNewsList +
        ", pageCount=" + pageCount +
        '}';
  }
}
