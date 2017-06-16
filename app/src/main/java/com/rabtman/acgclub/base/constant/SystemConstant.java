package com.rabtman.acgclub.base.constant;

import android.content.Context;
import android.os.Environment;
import com.rabtman.common.utils.FileUtils;
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

  static {
    ACG_NEWS_TITLE = new ArrayList<>();
    ACG_NEWS_TITLE.add("动漫新闻");
    ACG_NEWS_TITLE.add("业界动态");
    ACG_NEWS_TITLE.add("其他热点");
  }

  //图片保存地址
  public static String getImgPath(Context context) {
    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
      return Environment.getExternalStorageDirectory() + "/acgclub/images";
    }
    return FileUtils.getCacheFile(context).getAbsolutePath() + "/images";
  }
}
