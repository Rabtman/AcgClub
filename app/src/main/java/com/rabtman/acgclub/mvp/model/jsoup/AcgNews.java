package com.rabtman.acgclub.mvp.model.jsoup;

import com.fcannizzaro.jsoup.annotations.interfaces.Attr;
import com.fcannizzaro.jsoup.annotations.interfaces.Selector;
import com.fcannizzaro.jsoup.annotations.interfaces.Text;

/**
 * @author Rabtman
 */
@Selector("div.article-list ul li")
public class AcgNews {

  @Attr(query = "a img", attr = "src")
  private String imgUrl;
  @Text("div div h3 a")
  private String title;
  @Text("div div div.p-row")
  private String description;
  @Text("div div div span")
  private String dateTime;
  @Attr(query = "a", attr = "href")
  private String contentLink;

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDateTime() {
    return dateTime;
  }

  public void setDateTime(String dateTime) {
    this.dateTime = dateTime;
  }

  public String getContentLink() {
    return contentLink;
  }

  public void setContentLink(String contentLink) {
    this.contentLink = contentLink;
  }

  @Override
  public String toString() {
    return "AcgNews{" +
        "imgUrl='" + imgUrl + '\'' +
        ", title='" + title + '\'' +
        ", description='" + description + '\'' +
        ", dateTime='" + dateTime + '\'' +
        ", contentLink='" + contentLink + '\'' +
        '}';
  }
}
