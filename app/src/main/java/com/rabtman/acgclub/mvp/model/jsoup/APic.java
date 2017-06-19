package com.rabtman.acgclub.mvp.model.jsoup;

import com.fcannizzaro.jsoup.annotations.interfaces.Attr;
import com.fcannizzaro.jsoup.annotations.interfaces.Items;
import com.fcannizzaro.jsoup.annotations.interfaces.Selector;
import com.fcannizzaro.jsoup.annotations.interfaces.Text;
import com.rabtman.common.utils.LogUtil;
import java.util.List;

/**
 * @author Rabtman
 */
@Selector("div#main")
public class APic {

  @Items
  private List<PicInfo> picInfos;
  @Attr(query = "div#page a.extend:containsOwn(尾页)", attr = "href")
  private String pageCount;

  public List<PicInfo> getPicInfos() {
    return picInfos;
  }

  public void setPicInfos(List<PicInfo> picInfos) {
    this.picInfos = picInfos;
  }

  public int getPageCount() {
    String count = pageCount.substring(pageCount.lastIndexOf("/") + 1);
    try {
      return Integer.parseInt(count);
    } catch (Exception e) {
      LogUtil.d("pageCount:" + count);
      e.printStackTrace();
      return 1;
    }
  }

  @Override
  public String toString() {
    return "APic{" +
        "picInfos=" + picInfos +
        ", pageCount='" + pageCount + '\'' +
        '}';
  }

  @Selector("div.loop")
  public static class PicInfo {

    @Attr(query = "div.content a", attr = "title")
    private String title;
    @Attr(query = "div.content a img", attr = "src")
    private String thumbUrl;
    @Attr(query = "div.content a", attr = "href")
    private String contentLink;
    @Text("div.date")
    private String date;
    @Text("div div.num")
    private String count;

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
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

    public String getDate() {
      return date.substring(0, date.indexOf(" "));
    }

    public void setDate(String date) {
      this.date = date;
    }

    public String getCount() {
      return count;
    }

    public void setCount(String count) {
      this.count = count;
    }

    @Override
    public String toString() {
      return "PicInfo{" +
          "title='" + title + '\'' +
          ", thumbUrl='" + thumbUrl + '\'' +
          ", contentLink='" + contentLink + '\'' +
          ", date='" + date + '\'' +
          ", count='" + count + '\'' +
          '}';
    }
  }
}
