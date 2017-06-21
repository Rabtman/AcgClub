package com.rabtman.acgclub.mvp.model.jsoup;

import com.fcannizzaro.jsoup.annotations.interfaces.Attr;
import com.fcannizzaro.jsoup.annotations.interfaces.Items;
import com.fcannizzaro.jsoup.annotations.interfaces.Selector;
import java.util.List;

/**
 * @author Rabtman
 */
@Selector("div#list-most-recent")
public class MoePic {

  @Items
  private List<PicInfo> picInfos;
  @Attr(query = "ul li.pagination-next a", attr = "href")
  private String nextUrl;

  public List<PicInfo> getPicInfos() {
    return picInfos;
  }

  public void setPicInfos(List<PicInfo> picInfos) {
    this.picInfos = picInfos;
  }

  public String getNextUrl() {
    return nextUrl;
  }

  @Override
  public String toString() {
    return "MoePic{" +
        "picInfos=" + picInfos +
        ", nextUrl='" + nextUrl + '\'' +
        '}';
  }

  @Selector("div.pad-content-listing div.list-item.fixed-size.c8.gutter-margin-right-bottom.privacy-public.safe")
  public static class PicInfo {

    @Attr(query = "div.list-item-image.fixed-size a.image-container img", attr = "src")
    private String thumbUrl;
    @Attr(query = "div.list-item-image.fixed-size a.image-container", attr = "href")
    private String contentLink;

    @Override
    public String toString() {
      return "PicInfo{" +
          "thumbUrl='" + thumbUrl + '\'' +
          ", contentLink='" + contentLink + '\'' +
          '}';
    }

    public String getThumbUrl() {
      return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
      this.thumbUrl = thumbUrl;
    }

    public String getContentLink() {
      return contentLink;
    }

    public void setContentLink(String contentLink) {
      this.contentLink = contentLink;
    }

  }
}
