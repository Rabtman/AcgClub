package com.rabtman.acgclub.mvp.model.jsoup;

import com.fcannizzaro.jsoup.annotations.interfaces.Items;
import com.fcannizzaro.jsoup.annotations.interfaces.Selector;
import com.fcannizzaro.jsoup.annotations.interfaces.Text;
import java.util.List;

/**
 * @author Rabtman
 */
@Selector("div.w article-list-page clearfix")
public class AcgNewsPage {

  @Items
  private List<AcgNews> acgNewsList;
  @Text("div div ul li span strong")
  private String pageCount;

  public List<AcgNews> getAcgNewsList() {
    return acgNewsList;
  }

  public void setAcgNewsList(List<AcgNews> acgNewsList) {
    this.acgNewsList = acgNewsList;
  }

  public String getPageCount() {
    return pageCount;
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
