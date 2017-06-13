package com.rabtman.acgclub.mvp.model.jsoup;

import com.fcannizzaro.jsoup.annotations.interfaces.Html;
import com.fcannizzaro.jsoup.annotations.interfaces.Selector;

/**
 * @author Rabtman
 */
@Selector("div.container")
public class ScheduleVideo {

  @Html("div#vedio")
  private String videoHtml;

  public String getVideoHtml() {
    return videoHtml;
  }

  public void setVideoHtml(String videoHtml) {
    this.videoHtml = videoHtml;
  }
}
