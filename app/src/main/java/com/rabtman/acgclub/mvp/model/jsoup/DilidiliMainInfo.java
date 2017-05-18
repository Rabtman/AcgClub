package com.rabtman.acgclub.mvp.model.jsoup;


import java.util.List;
import me.ghui.fruit.annotations.Pick;

public class DilidiliMainInfo {

  @Pick("li[id~=week?]")
  private List<String> weekName;
  @Pick("div[id~=weekdiv?]")
  private List<AnimateWeek> weekItems;

  public List<String> getWeekName() {
    return weekName;
  }

  public void setWeekName(List<String> weekName) {
    this.weekName = weekName;
  }

  public static class AnimateWeek {

    @Pick("div.week_item")
    private List<AnimateItem> animateItems;

    public List<AnimateItem> getAnimateItems() {
      return animateItems;
    }

    public void setAnimateItems(List<AnimateItem> animateItems) {
      this.animateItems = animateItems;
    }
  }

  public static class AnimateItem {

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
