package com.rabtman.acgclub.base.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rabtman
 */
public class SystemConstant {

  //动漫资讯标题栏
  public static final List<String> ACG_COLLECTION_TITLE;
  //图库标题栏
  public static final String[] ACG_PIC_TITLE = {"动漫"};

  public static final String pathRoot = "/AcgClub";
  //图片保存地址
  public static final String ACG_IMG_PATH = pathRoot;

  static {
    ACG_COLLECTION_TITLE = new ArrayList<>();
    ACG_COLLECTION_TITLE.add("番剧");
  }
}
