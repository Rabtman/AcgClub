package com.rabtman.acgschedule.mvp.model.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Rabtman
 */
public class ScheduleCollection extends RealmObject {

  @PrimaryKey
  private String scheduleUrl;
  private String name;
  private String imgUrl;

  public ScheduleCollection() {
  }

  public ScheduleCollection(String scheduleUrl, String name, String imgUrl) {
    this.scheduleUrl = scheduleUrl;
    this.name = name;
    this.imgUrl = imgUrl;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }

  public String getScheduleUrl() {
    return scheduleUrl;
  }

  public void setScheduleUrl(String scheduleUrl) {
    this.scheduleUrl = scheduleUrl;
  }
}
