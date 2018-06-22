package com.rabtman.acgschedule.mvp.model.jsoup;


import com.fcannizzaro.jsoup.annotations.interfaces.Attr;
import com.fcannizzaro.jsoup.annotations.interfaces.Items;
import com.fcannizzaro.jsoup.annotations.interfaces.Selector;
import com.fcannizzaro.jsoup.annotations.interfaces.Text;
import java.util.List;

@Selector("main")
public class DilidiliInfo {

  //本季新番
  /*@Text("div div div.mainMenu ul li:last-of-type a span")
  private String scheduleNewName;
  @Attr(flowableExec = "div div div.mainMenu ul li:last-of-type a", attr = "href")
  private String scheduleNewLink;*/
  @Items
  private List<ScheudleBanner> scheudleBanners; //轮播栏信息
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

  public List<ScheudleBanner> getScheudleBanners() {
    return scheudleBanners;
  }

  public void setScheudleBanners(
      List<ScheudleBanner> scheudleBanners) {
    this.scheudleBanners = scheudleBanners;
  }

  public List<ScheduleRecommand> getScheduleRecommands() {
    return scheduleRecommands;
  }

  public void setScheduleRecommands(
      List<ScheduleRecommand> scheduleRecommands) {
    this.scheduleRecommands = scheduleRecommands;
  }

  public List<ScheduleRecent> getScheduleRecents() {
    return scheduleRecents;
  }

  public void setScheduleRecents(
      List<ScheduleRecent> scheduleRecents) {
    this.scheduleRecents = scheduleRecents;
  }

  /*public String getScheduleNewName() {
    return scheduleNewName;
  }

  public void setScheduleNewName(String scheduleNewName) {
    this.scheduleNewName = scheduleNewName;
  }

  public String getScheduleNewLink() {
    return scheduleNewLink;
  }

  public void setScheduleNewLink(String scheduleNewLink) {
    this.scheduleNewLink = scheduleNewLink;
  }*/

  @Override
  public String toString() {
    return "DilidiliInfo{" +
        "scheudleBanners=" + scheudleBanners +
        ", scheduleRecommands=" + scheduleRecommands +
        ", scheduleWeek=" + scheduleWeek +
        ", scheduleRecents=" + scheduleRecents +
        '}';
  }

  @Selector("div.swiper-wrapper a.swiper-slide")
  public static class ScheudleBanner {

    @Attr(query = "img", attr = "src")
    private String imgUrl;
    @Text("div")
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
      return "ScheudleBanner{" +
          "imgUrl='" + imgUrl + '\'' +
          ", name='" + name + '\'' +
          ", animeLink='" + animeLink + '\'' +
          '}';
    }
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
    @Text("h4")
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
