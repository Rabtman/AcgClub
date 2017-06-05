package com.rabtman.acgclub.mvp.model.jsoup;

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
  //@Text("div div ul li span strong")
  private int pageCount;

  public List<AcgNews> getAcgNewsList() {
    return acgNewsList;
  }

  public void setAcgNewsList(List<AcgNews> acgNewsList) {
    this.acgNewsList = acgNewsList;
  }

  public int getPageCount() {
    return pageCount;
  }

  public void setPageCount(int pageCount) {
    this.pageCount = pageCount;
  }
}
