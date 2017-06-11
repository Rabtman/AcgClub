package com.rabtman.acgclub.mvp.model.jsoup;

import com.fcannizzaro.jsoup.annotations.interfaces.Attr;
import com.fcannizzaro.jsoup.annotations.interfaces.Html;
import com.fcannizzaro.jsoup.annotations.interfaces.Items;
import com.fcannizzaro.jsoup.annotations.interfaces.Selector;
import com.fcannizzaro.jsoup.annotations.interfaces.Text;
import java.util.List;

/**
 * @author Rabtman
 */

@Selector("div.container")
public class ScheduleDetail {

  @Attr(query = "div div img", attr = "src")
  private String imgUrl;
  @Html("div.details-hd div dl")
  private String scheduleInfo;
  @Text("div.details-hd div[class~=details-about?]")
  private String description;
  @Items
  private List<ScheduleEpisode> scheduleEpisodes;

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }

  public String getScheduleInfo() {
    return scheduleInfo;
  }

  public void setScheduleInfo(String scheduleInfo) {
    this.scheduleInfo = scheduleInfo;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<ScheduleEpisode> getScheduleEpisodes() {
    return scheduleEpisodes;
  }

  public void setScheduleEpisodes(
      List<ScheduleEpisode> scheduleEpisodes) {
    this.scheduleEpisodes = scheduleEpisodes;
  }

  @Selector("div[class~=episodeWrap?] ul li")
  public static class ScheduleEpisode {

    @Text("a")
    private String name;
    @Attr(query = "a", attr = "href")
    private String link;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getLink() {
      return link;
    }

    public void setLink(String link) {
      this.link = link;
    }
  }
}
