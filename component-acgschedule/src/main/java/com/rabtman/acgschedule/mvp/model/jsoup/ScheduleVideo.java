package com.rabtman.acgschedule.mvp.model.jsoup;

import com.fcannizzaro.jsoup.annotations.interfaces.Html;
import com.fcannizzaro.jsoup.annotations.interfaces.Selector;

/**
 * @author Rabtman
 */
@Selector("div.clear")
public class ScheduleVideo {

  @Html("div.player_main")
  private String videoHtml;

  private String videoUrl;

  public String getVideoHtml() {
    return videoHtml;
  }

  public void setVideoHtml(String videoHtml) {
    this.videoHtml = videoHtml;
  }

  public String getVideoUrl() {
    return videoUrl;
  }

  public void setVideoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
  }

  @Override
  public String toString() {
    return "ScheduleVideo{" +
        "videoHtml='" + videoHtml + '\'' +
        ", videoUrl='" + videoUrl + '\'' +
        '}';
  }
}
