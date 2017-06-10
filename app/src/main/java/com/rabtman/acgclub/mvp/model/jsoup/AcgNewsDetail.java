package com.rabtman.acgclub.mvp.model.jsoup;

import com.fcannizzaro.jsoup.annotations.interfaces.ForEach;
import com.fcannizzaro.jsoup.annotations.interfaces.Html;
import com.fcannizzaro.jsoup.annotations.interfaces.Selector;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;

/**
 * @author Rabtman
 */
@Selector("div.article-article")
public class AcgNewsDetail {

  private List<String> labels = new ArrayList<>();
  @Html("div.articleContent")
  private String content;

  @ForEach("div div span a")
  void iterate(Element element, int index) {
    labels.add(index, element.text());
  }

  public List<String> getLabels() {
    return labels;
  }

  public void setLabels(List<String> labels) {
    this.labels = labels;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public String toString() {
    return "AcgNewsDetail{" +
        "labels=" + labels +
        ", content='" + content + '\'' +
        '}';
  }
}
