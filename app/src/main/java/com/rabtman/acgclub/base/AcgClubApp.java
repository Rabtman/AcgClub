package com.rabtman.acgclub.base;

import com.rabtman.common.base.App;
import com.rabtman.common.di.component.AppComponent;
import com.rabtman.common.utils.LogUtil;
import com.tencent.bugly.beta.tinker.TinkerManager;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * @author Rabtman
 */

public class AcgClubApp extends TinkerApplication implements App {

  public AcgClubApp() {
    super(ShareConstants.TINKER_ENABLE_ALL,
        "com.rabtman.acgclub.base.AcgClubLike",
        "com.tencent.tinker.loader.TinkerLoader",
        false);
  }


  @Override
  public AppComponent getAppComponent() {
    ApplicationLike like = TinkerManager.getTinkerApplicationLike();
    LogUtil.i("application like:" + like);
    if (like != null) {
      LogUtil.i("application component:" + ((App) like).getAppComponent());
    }
    return ((App) TinkerManager.getTinkerApplicationLike()).getAppComponent();
  }
}
