package com.rabtman.acgclub.base.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rabtman
 */
public class SystemConstant {

  //我的收藏标题栏
  public static final List<String> ACG_COLLECTION_TITLE;

  static {
    ACG_COLLECTION_TITLE = new ArrayList<>(2);
    ACG_COLLECTION_TITLE.add("番剧");
    //ACG_COLLECTION_TITLE.add("漫画");
  }
}
