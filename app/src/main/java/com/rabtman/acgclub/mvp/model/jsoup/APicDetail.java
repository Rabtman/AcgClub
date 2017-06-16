package com.rabtman.acgclub.mvp.model.jsoup;

import android.text.TextUtils;
import com.fcannizzaro.jsoup.annotations.interfaces.ForEach;
import com.fcannizzaro.jsoup.annotations.interfaces.Selector;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;

/**
 * @author Rabtman
 */

@Selector("div#main div#post div.post")
public class APicDetail {

  private List<String> picList = new ArrayList<>();

  @ForEach("p img")
  void iterate(Element element, int index) {
    String src = element.attr("src");
    if (!TextUtils.isEmpty(src)) {
      picList.add(src);
    }
  }

  public List<String> getPicList() {
    return picList;
  }

  public void setPicList(List<String> picList) {
    this.picList = picList;
  }
}
