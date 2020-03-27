package com.rabtman.acgschedule.mvp.model.jsoup;


import com.fcannizzaro.jsoup.annotations.interfaces.Attr;
import com.fcannizzaro.jsoup.annotations.interfaces.Items;
import com.fcannizzaro.jsoup.annotations.interfaces.Selector;
import com.fcannizzaro.jsoup.annotations.interfaces.Text;
import java.util.List;

@Selector("body")
public class DilidiliInfo {

  @Items
  private List<ScheduleBanner> scheduleBanners; //轮播栏信息
  @Items
  private List<ScheduleRecommend> scheduleRecommends; //近期推荐
  @Items
  private List<ScheduleWeek> scheduleWeek;  //追番时间表
  @Items
  private List<ScheduleRecent> scheduleRecent;  //最近更新

  public List<ScheduleWeek> getScheduleWeek() {
    return scheduleWeek;
  }

  public void setScheduleWeek(List<ScheduleWeek> scheduleWeek) {
    this.scheduleWeek = scheduleWeek;
  }

  public List<ScheduleBanner> getScheduleBanners() {
    return scheduleBanners;
  }

  public void setScheduleBanners(
      List<ScheduleBanner> scheduleBanners) {
    this.scheduleBanners = scheduleBanners;
  }

  public List<ScheduleRecommend> getScheduleRecommends() {
    return scheduleRecommends;
  }

  public void setScheduleRecommends(
      List<ScheduleRecommend> scheduleRecommends) {
    this.scheduleRecommends = scheduleRecommends;
  }

  public List<ScheduleRecent> getScheduleRecent() {
    return scheduleRecent;
  }

  public void setScheduleRecent(
      List<ScheduleRecent> scheduleRecent) {
    this.scheduleRecent = scheduleRecent;
  }

  @Override
  public String toString() {
    return "DilidiliInfo{" +
        "scheudleBanners=" + scheduleBanners +
        ", scheduleRecommands=" + scheduleRecommends +
        ", scheduleWeek=" + scheduleWeek +
        ", scheduleRecents=" + scheduleRecent +
        '}';
  }

  @Selector("div.swipe ul li")
  public static class ScheduleBanner {

    @Attr(query = "a img", attr = "src")
    private String imgUrl;
    @Text("a p")
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

  @Selector("div.edit_list ul li")
  public static class ScheduleRecommend {

    @Attr(query = "a div", attr = "style")
    private String imgUrl;
    @Text("a p")
    private String name;
    @Attr(query = "a", attr = "href")
    private String animeLink;

    public String getImgUrl() {
      try {
        return imgUrl.substring(imgUrl.lastIndexOf("(") + 1, imgUrl.lastIndexOf(".") + 4);
      } catch (Exception e) {
        e.printStackTrace();
        return "";
      }
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

  @Selector("div.list ul li")
  public static class ScheduleRecent {

    @Attr(query = "div.imgblock", attr = "style")
    private String imgUrl;
    @Text("a.itemtext")
    private String name;
    @Text("div.itemimgtext")
    private String desc;
    @Attr(query = "div.itemimg a", attr = "href")
    private String animeLink;

    public String getImgUrl() {
      try {
        return imgUrl.substring(imgUrl.lastIndexOf("http"), imgUrl.lastIndexOf(".") + 4);
      } catch (Exception e) {
        e.printStackTrace();
        return "";
      }
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
