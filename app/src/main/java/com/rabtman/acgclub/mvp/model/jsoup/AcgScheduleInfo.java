package com.rabtman.acgclub.mvp.model.jsoup;


import java.util.List;
import me.ghui.fruit.annotations.Pick;

public class AcgScheduleInfo {

  @Pick("li[id~=week?]")
  private List<String> weekName;
  @Pick("div[id~=weekdiv?]")
  private List<ScheduleWeek> scheduleWeek;

  public List<String> getWeekName() {
    return weekName;
  }

  public void setWeekName(List<String> weekName) {
    this.weekName = weekName;
  }

  public List<ScheduleWeek> getScheduleWeek() {
    return scheduleWeek;
  }

  public void setScheduleWeek(List<ScheduleWeek> scheduleWeek) {
    this.scheduleWeek = scheduleWeek;
  }
}
