package com.rabtman.acgclub.mvp.model.jsoup;


import com.fcannizzaro.jsoup.annotations.interfaces.Attr;
import com.fcannizzaro.jsoup.annotations.interfaces.Items;
import com.fcannizzaro.jsoup.annotations.interfaces.Selector;
import com.fcannizzaro.jsoup.annotations.interfaces.Text;
import java.util.List;

@Selector("div.container")
public class DilidiliInfo {

  @Items
  private List<ScheduleRecommand> scheduleRecommands; //近期推荐
  @Items
  private List<ScheduleWeek> scheduleWeek;  //追番时间表
  @Items
  private List<ScheduleRecent> scheduleRecents;  //最近更新

  public List<ScheduleWeek> getScheduleWeek() {
    return scheduleWeek;
  }

  public void setScheduleWeek(List<ScheduleWeek> scheduleWeek) {
    this.scheduleWeek = scheduleWeek;
  }

  @Override
  public String toString() {
    return "DilidiliInfo{" +
        "scheduleRecommands=" + scheduleRecommands +
        ", scheduleWeek=" + scheduleWeek +
        ", scheduleRecents=" + scheduleRecents +
        '}';
  }

  @Selector("div.focusimg2.pl10.pr10 div ul li")
  public static class ScheduleRecommand {

    @Attr(query = "a img", attr = "src")
    private String imgUrl;
    @Text("a h4")
    private String name;
    @Attr(query = "a", attr = "href")
    private String animeLink;

    public String getImgUrl() {
      return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
      this.imgUrl = imgUrl;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getAnimeLink() {
      return animeLink;
    }

    public void setAnimeLink(String animeLink) {
      this.animeLink = animeLink;
    }

    @Override
    public String toString() {
      return "ScheduleRecommand{" +
          "imgUrl='" + imgUrl + '\'' +
          ", name='" + name + '\'' +
          ", animeLink='" + animeLink + '\'' +
          '}';
    }
  }

  @Selector("div.nofucs div ul li")
  public static class ScheduleRecent {

    @Attr(query = "a img", attr = "src")
    private String imgUrl;
    @Text("a h4")
    private String name;
    @Text("a p")
    private String desc;
    @Attr(query = "a", attr = "href")
    private String animeLink;

    public String getImgUrl() {
      return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
      this.imgUrl = imgUrl;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getDesc() {
      return desc;
    }

    public void setDesc(String desc) {
      this.desc = desc;
    }

    public String getAnimeLink() {
      return animeLink;
    }

    public void setAnimeLink(String animeLink) {
      this.animeLink = animeLink;
    }


    @Override
    public String toString() {
      return "ScheduleRecent{" +
          "imgUrl='" + imgUrl + '\'' +
          ", name='" + name + '\'' +
          ", desc='" + desc + '\'' +
          ", animeLink='" + animeLink + '\'' +
          '}';
    }
  }
}
