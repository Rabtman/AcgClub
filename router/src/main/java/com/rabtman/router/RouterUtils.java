package com.rabtman.router;

import com.alibaba.android.arouter.launcher.ARouter;


/**
 * @author Rabtman 在ARouter外再包一层，方便以后替换
 */
public class RouterUtils {

  public static ARouter getInstance() {
    return ARouter.getInstance();
  }
}
