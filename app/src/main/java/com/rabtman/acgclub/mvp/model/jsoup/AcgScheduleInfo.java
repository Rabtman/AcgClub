package com.rabtman.acgclub.mvp.model.jsoup;

import java.util.List;
import me.ghui.fruit.annotations.Pick;

/**
 * @author Rabtman 追番信息
 */
public class AcgScheduleInfo {

  @Pick("div.week_item")
  private List<ScheduleItem> scheduleItems;

  public List<ScheduleItem> getScheduleItems() {
    return scheduleItems;
  }

  public void setScheduleItems(List<ScheduleItem> scheduleItems) {
    this.scheduleItems = scheduleItems;
  }

  public static class ScheduleItem {

    @Pick("li.week_item_left")
    private String name;
    @Pick("li.week_item_right")
    private String episode;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getEpisode() {
      return episode;
    }

    public void setEpisode(String episode) {
      this.episode = episode;
    }
  }

}
