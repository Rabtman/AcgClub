package com.rabtman.acgclub.base.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rabtman
 */
public class SystemConstant {

  //动漫资讯标题栏
  public static final List<String> ACG_NEWS_TITLE;
  //番剧标题栏
  public static final String[] ACG_SCHEDULE_TITLE = {"放送表"};
  //图库标题栏
  public static final String[] ACG_PIC_TITLE = {"动漫"};

  public static final String pathRoot = "/AcgClub";
  //图片保存地址
  public static final String ACG_IMG_PATH = pathRoot;

  static {
    ACG_NEWS_TITLE = new ArrayList<>();
    ACG_NEWS_TITLE.add("动漫新闻");
    ACG_NEWS_TITLE.add("业界动态");
    ACG_NEWS_TITLE.add("其他热点");
  }
}
