package com.rabtman.acgschedule.mvp.model.entity;

import com.chad.library.adapter.base.entity.JSectionEntity;
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleWeek.ScheduleItem;

/**
 * @author Rabtman
 */

public class ScheduleTimeItem extends JSectionEntity {

  public boolean isHeader;
  public String header;
  public int headerIndex;
  public ScheduleItem data;


  public ScheduleTimeItem(boolean isHeader, String header, int index) {
    this.isHeader = isHeader;
    this.header = header;
    this.headerIndex = index;
  }

  public ScheduleTimeItem(ScheduleItem scheduleItem, int index) {
    data = scheduleItem;
    headerIndex = index;
  }

  @Override
  public boolean isHeader() {
    return isHeader;
  }
}
