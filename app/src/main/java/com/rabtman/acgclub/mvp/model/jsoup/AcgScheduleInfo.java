package com.rabtman.acgclub.mvp.model.jsoup;


import com.fcannizzaro.jsoup.annotations.interfaces.ForEach;
import com.fcannizzaro.jsoup.annotations.interfaces.Items;
import com.fcannizzaro.jsoup.annotations.interfaces.Selector;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;

@Selector("week_mend")
public class AcgScheduleInfo {

  private List<String> weekName = new ArrayList<>();
  @Items
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

  @ForEach("li[id~=week?]")
  void iterate(Element element, int index) {
    weekName.add(index, element.text());
  }
}
