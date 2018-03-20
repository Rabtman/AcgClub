package com.rabtman.acgschedule.mvp.model.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Rabtman
 */

public class ScheduleHistory extends RealmObject {

  @PrimaryKey
  private String scheduleUrl;
  private int lastRecord;

  public String getScheduleUrl() {
    return scheduleUrl;
  }

  public void setScheduleUrl(String scheduleUrl) {
    this.scheduleUrl = scheduleUrl;
  }

  public int getLastRecord() {
    return lastRecord;
  }

  public void setLastRecord(int lastRecord) {
    this.lastRecord = lastRecord;
  }
}
