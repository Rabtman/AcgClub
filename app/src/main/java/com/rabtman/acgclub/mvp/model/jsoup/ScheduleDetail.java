package com.rabtman.acgclub.mvp.model.jsoup;

import com.fcannizzaro.jsoup.annotations.interfaces.Attr;
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
  @Text("div.details-hd div dl dt")
  private String scheduleTitle;
  @Text("div.details-hd div dl dd p i")
  private String scheduleProc;
  @Text("div.details-hd div dl dd p:containsOwn(年代)")
  private String scheduleTime;
  @Text("div.details-hd div dl dd p:containsOwn(地区)")
  private String scheduleAera;
  @Text("div.details-hd div dl dd p:containsOwn(标签)")
  private String scheduleLabel;
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

  public String getScheduleTitle() {
    return scheduleTitle;
  }

  public void setScheduleTitle(String scheduleTitle) {
    this.scheduleTitle = scheduleTitle;
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

  public String getScheduleProc() {
    return scheduleProc;
  }

  public void setScheduleProc(String scheduleProc) {
    this.scheduleProc = scheduleProc;
  }

  public String getScheduleTime() {
    return scheduleTime;
  }

  public void setScheduleTime(String scheduleTime) {
    this.scheduleTime = scheduleTime;
  }

  public String getScheduleAera() {
    return scheduleAera;
  }

  public void setScheduleAera(String scheduleAera) {
    this.scheduleAera = scheduleAera;
  }

  public String getScheduleLabel() {
    return scheduleLabel;
  }

  public void setScheduleLabel(String scheduleLabel) {
    this.scheduleLabel = scheduleLabel;
  }

  @Override
  public String toString() {
    return "ScheduleDetail{" +
        "imgUrl='" + imgUrl + '\'' +
        ", scheduleTitle='" + scheduleTitle + '\'' +
        ", scheduleProc='" + scheduleProc + '\'' +
        ", scheduleTime='" + scheduleTime + '\'' +
        ", scheduleAera='" + scheduleAera + '\'' +
        ", scheduleLabel='" + scheduleLabel + '\'' +
        ", description='" + description + '\'' +
        ", scheduleEpisodes=" + scheduleEpisodes +
        '}';
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

    @Override
    public String toString() {
      return "ScheduleEpisode{" +
          "name='" + name + '\'' +
          ", link='" + link + '\'' +
          '}';
    }
  }
}
