package com.rabtman.acgschedule.mvp.model.entity;

import com.chad.library.adapter.base.entity.SectionEntity;
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleWeek.ScheduleItem;

/**
 * @author Rabtman
 */

public class ScheduleTimeItem extends SectionEntity<ScheduleItem> {

  public int headerIndex;

  public ScheduleTimeItem(boolean isHeader, String header, int index) {
    super(isHeader, header);
    headerIndex = index;
  }

  public ScheduleTimeItem(ScheduleItem scheduleItem, int index) {
    super(scheduleItem);
    headerIndex = index;
  }
}
