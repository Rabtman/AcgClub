package com.rabtman.acgschedule.mvp.model.jsoup;

import com.fcannizzaro.jsoup.annotations.interfaces.Attr;
import com.fcannizzaro.jsoup.annotations.interfaces.Items;
import com.fcannizzaro.jsoup.annotations.interfaces.Selector;
import com.fcannizzaro.jsoup.annotations.interfaces.Text;
import java.util.List;

/**
 * @author Rabtman
 */
@Selector("div.container")
public class ScheduleNew {

  @Items
  private List<ScheduleNewItem> scheduleNewItems;

  public List<ScheduleNewItem> getScheduleNewItems() {
    return scheduleNewItems;
  }

  public void setScheduleNewItems(
      List<ScheduleNewItem> scheduleNewItems) {
    this.scheduleNewItems = scheduleNewItems;
  }

  @Override
  public String toString() {
    return "ScheduleNew{" +
        "scheduleNewItems=" + scheduleNewItems +
        '}';
  }

  @Selector("div div div div div.img2Wrap ul li")
  public static class ScheduleNewItem {

    @Attr(query = "a img", attr = "src")
    private String imgUrl;
    @Text("a h4")
    private String title;
    @Text("a p.update:containsOwn(看点)")
    private String spot;
    @Text("a p.update:containsOwn(类型)")
    private String type;
    @Text("a p.update:containsOwn(简介)")
    private String desc;
    @Attr(query = "a", attr = "href")
    private String animeLink;

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

    public String getSpot() {
      return spot;
    }

    public void setSpot(String spot) {
      this.spot = spot;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public String getDesc() {
      return desc;
    }

    public void setDesc(String desc) {
      this.desc = desc;
    }

    public String getAnimeLink() {
      return animeLink;
    }

    public void setAnimeLink(String animeLink) {
      this.animeLink = animeLink;
    }

    @Override
    public String toString() {
      return "ScheduleNewItem{" +
          "imgUrl='" + imgUrl + '\'' +
          ", title='" + title + '\'' +
          ", spot='" + spot + '\'' +
          ", type='" + type + '\'' +
          ", desc='" + desc + '\'' +
          ", animeLink='" + animeLink + '\'' +
          '}';
    }
  }
}
