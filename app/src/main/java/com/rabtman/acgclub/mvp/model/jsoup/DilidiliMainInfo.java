package com.rabtman.acgclub.mvp.model.jsoup;


import java.util.List;
import me.ghui.fruit.annotations.Pick;

public class DilidiliMainInfo {

  @Pick("li[id~=week?]")
  private List<String> weekName;
  @Pick("div[id~=weekdiv?]")
  private List<AcgScheduleInfo> acgScheduleInfo;

  public List<String> getWeekName() {
    return weekName;
  }

  public void setWeekName(List<String> weekName) {
    this.weekName = weekName;
  }

  public List<AcgScheduleInfo> getAcgScheduleInfo() {
    return acgScheduleInfo;
  }

  public void setAcgScheduleInfo(List<AcgScheduleInfo> acgScheduleInfo) {
    this.acgScheduleInfo = acgScheduleInfo;
  }
}
