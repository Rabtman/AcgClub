package com.rabtman.acgschedule.mvp.model.jsoup;

import com.fcannizzaro.jsoup.annotations.interfaces.Attr;
import com.fcannizzaro.jsoup.annotations.interfaces.Items;
import com.fcannizzaro.jsoup.annotations.interfaces.Selector;
import com.fcannizzaro.jsoup.annotations.interfaces.Text;
import java.util.List;

/**
 * @author Rabtman 其他类别视频信息
 */
@Selector("div.container")
public class ScheduleOtherPage {

  @Text("div div.title-box div h2")
  private String title;  //分区标题
  @Attr(query = "div div div.img3Wrap ul ul.pagelistbox li a", attr = "href")
  private String pageCount;   //总页数
  @Items
  private List<ScheduleOtherItem> scheduleOtherItems;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getPageCount() {
    try {
      return Integer.parseInt(pageCount.substring(0, pageCount.indexOf(".")));
    } catch (Exception e) {
      return 1;
    }
  }

  public void setPageCount(String pageCount) {
    this.pageCount = pageCount;
  }

  public List<ScheduleOtherItem> getScheduleOtherItems() {
    return scheduleOtherItems;
  }

  public void setScheduleOtherItems(
      List<ScheduleOtherItem> scheduleOtherItems) {
    this.scheduleOtherItems = scheduleOtherItems;
  }

  @Override
  public String toString() {
    return "ScheduleOtherPage{" +
        "title='" + title + '\'' +
        ", pageCount='" + pageCount + '\'' +
        ", scheduleOtherItems=" + scheduleOtherItems +
        '}';
  }

  @Selector("div div div.img3Wrap ul li:has(h4)")
  public static class ScheduleOtherItem {

    @Attr(query = "a img", attr = "src")
    private String imgUrl;
    @Text("a h4")
    private String title;
    @Attr(query = "a", attr = "href")
    private String videolLink;

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

    public String getVideolLink() {
      return videolLink;
    }

    public void setVideolLink(String videolLink) {
      this.videolLink = videolLink;
    }

    @Override
    public String toString() {
      return "ScheduleOtherItem{" +
          "imgUrl='" + imgUrl + '\'' +
          ", title='" + title + '\'' +
          ", videolLink='" + videolLink + '\'' +
          '}';
    }
  }
}
