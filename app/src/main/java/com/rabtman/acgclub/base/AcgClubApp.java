package com.rabtman.acgclub.base;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * @author Rabtman
 */

public class AcgClubApp extends TinkerApplication {

  public AcgClubApp() {
    super(ShareConstants.TINKER_ENABLE_ALL,
        "com.rabtman.acgclub.base.AcgClubLike",
        "com.tencent.tinker.loader.TinkerLoader",
        false);
  }
}
