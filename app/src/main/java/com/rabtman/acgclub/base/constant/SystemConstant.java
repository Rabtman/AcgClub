package com.rabtman.acgclub.base.constant;

import com.rabtman.acgclub.R;
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
  //番剧时间表TAB标题栏
  public static final String[] SCHEDULE_WEEK_TITLE = {"一", "二", "三", "四", "五", "六", "日"};
  //番剧时间表LIST标题栏
  public static final String[] SCHEDULE_WEEK_LIST_TITLE = {"月曜日", "火曜日", "水曜日", "木曜日", "金曜日", "土曜日",
      "日曜日"};
  //番剧时间表LIST标题栏图标
  public static final int[] SCHEDULE_WEEK_LIST_TITLE_DRAWABLE = {R.drawable.ic_schedule_time_monday,
      R.drawable.ic_schedule_time_tuesday, R.drawable.ic_schedule_time_wednesday,
      R.drawable.ic_schedule_time_thursday, R.drawable.ic_schedule_time_friday,
      R.drawable.ic_schedule_time_saturday, R.drawable.ic_schedule_time_sunday};
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
