package com.rabtman.acgclub.base.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rabtman
 */
public class SystemConstant {

  //动漫资讯标题栏
  public static final List<String> ACG_NEWS_TITLE;

  static {
    ACG_NEWS_TITLE = new ArrayList<>();
    ACG_NEWS_TITLE.add("动漫新闻");
    ACG_NEWS_TITLE.add("业界动态");
    ACG_NEWS_TITLE.add("其他热点");
  }
}
