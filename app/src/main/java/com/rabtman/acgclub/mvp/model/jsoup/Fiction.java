package com.rabtman.acgclub.mvp.model.jsoup;

import com.fcannizzaro.jsoup.annotations.interfaces.Attr;
import com.fcannizzaro.jsoup.annotations.interfaces.Items;
import com.fcannizzaro.jsoup.annotations.interfaces.Selector;
import com.fcannizzaro.jsoup.annotations.interfaces.Text;
import java.util.List;

/**
 * @author Rabtman
 */

@Selector("div#content div#result")
public class Fiction {

  @Items
  private List<FictionItem> fictionItems;

  public List<FictionItem> getFictionItems() {
    return fictionItems;
  }

  public void setFictionItems(
      List<FictionItem> fictionItems) {
    this.fictionItems = fictionItems;
  }

  @Override
  public String toString() {
    return "Fiction{" +
        "fictionItems=" + fictionItems +
        '}';
  }

  @Selector("ul#novel li")
  public static class FictionItem {

    @Attr(query = "a img", attr = "src")
    private String imgUrl;
    @Text("a span")
    private String title;
    @Attr(query = "a", attr = "href")
    private String fictionLink;

    public String getImgUrl() {
      return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
      this.imgUrl = imgUrl;
    }

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public String getFictionLink() {
      return fictionLink;
    }

    public void setFictionLink(String fictionLink) {
      this.fictionLink = fictionLink;
    }

    @Override
    public String toString() {
      return "FictionItem{" +
          "imgUrl='" + imgUrl + '\'' +
          ", title='" + title + '\'' +
          ", fictionLink='" + fictionLink + '\'' +
          '}';
    }
  }
}
