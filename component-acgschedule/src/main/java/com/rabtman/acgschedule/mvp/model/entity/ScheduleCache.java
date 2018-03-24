package com.rabtman.acgschedule.mvp.model.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Rabtman
 */
public class ScheduleCache extends RealmObject {

  @PrimaryKey
  private String scheduleUrl;
  private String name;
  private String imgUrl;
  private boolean isCollect = false;
  private int lastWatchPos = -1;

  public ScheduleCache() {
  }

  public ScheduleCache(String scheduleUrl, int lastWatchPos) {
    this.scheduleUrl = scheduleUrl;
    this.lastWatchPos = lastWatchPos;
  }

  public ScheduleCache(String scheduleUrl, String name, String imgUrl, boolean isCollect,
      int lastWatchPos) {
    this.scheduleUrl = scheduleUrl;
    this.name = name;
    this.imgUrl = imgUrl;
    this.isCollect = isCollect;
    this.lastWatchPos = lastWatchPos;
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

  public boolean isCollect() {
    return isCollect;
  }

  public void setCollect(boolean collect) {
    isCollect = collect;
  }

  @Override
  public String toString() {
    return "ScheduleCache{" +
        "scheduleUrl='" + scheduleUrl + '\'' +
        ", name='" + name + '\'' +
        ", imgUrl='" + imgUrl + '\'' +
        ", isCollect=" + isCollect +
        ", lastWatchPos=" + lastWatchPos +
        '}';
  }

  public int getLastWatchPos() {
    return lastWatchPos;
  }

  public void setLastWatchPos(int lastWatchPos) {
    this.lastWatchPos = lastWatchPos;
  }
}
