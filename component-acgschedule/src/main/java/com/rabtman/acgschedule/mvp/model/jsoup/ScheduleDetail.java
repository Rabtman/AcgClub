package com.rabtman.acgschedule.mvp.model.jsoup;

import com.fcannizzaro.jsoup.annotations.interfaces.Attr;
import com.fcannizzaro.jsoup.annotations.interfaces.ForEach;
import com.fcannizzaro.jsoup.annotations.interfaces.Items;
import com.fcannizzaro.jsoup.annotations.interfaces.Selector;
import com.fcannizzaro.jsoup.annotations.interfaces.Text;
import java.util.List;
import org.jsoup.nodes.Element;

/**
 * @author Rabtman
 */

@Selector("body")
public class ScheduleDetail {

  @Attr(query = "div.list div.show img", attr = "src")
  private String imgUrl;
  @Text("div.list div.show h1")
  private String scheduleTitle;
  @Text("div.list div.show p:contains(连载)")
  private String scheduleProc;
  @Text("div.list div.show p:contains(上映)")
  private String scheduleTime;
  //@Text("div.list div.show p:contains(类型)")
  private String scheduleAera;
  //@Text("div.list div.show p:contains(类型)")
  private String scheduleLabel = "类型：";
  @Text("div.info")
  private String description;
  @Items
  private List<ScheduleEpisode> scheduleEpisodes;

  @ForEach("div.list div.show p:contains(类型) a")
  void labels(Element element, int index) {
    if (index == 0) {
      scheduleAera = "地区：" + element.text();
    } else {
      scheduleLabel += element.text() + " ";
    }
  }

  public String getImgUrl() {
    try {
      return imgUrl.substring(imgUrl.lastIndexOf("(") + 1, imgUrl.lastIndexOf(".") + 4);
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
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

  @Selector("div#playlists ul li")
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
