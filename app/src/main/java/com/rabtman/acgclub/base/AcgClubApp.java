package com.rabtman.acgclub.base;

import com.rabtman.common.base.App;
import com.rabtman.common.di.component.AppComponent;
import com.tencent.bugly.beta.tinker.TinkerManager;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * @author Rabtman
 */

public class AcgClubApp extends TinkerApplication implements App {

  public AcgClubApp() {
    super(ShareConstants.TINKER_ENABLE_ALL, "com.rabtman.acgclub.base.AppLike",
        "com.tencent.tinker.loader.TinkerLoader", false);
  }

  @Override
  public AppComponent getAppComponent() {
    return ((AppLike) TinkerManager.getTinkerApplicationLike()).getAppComponent();
  }
}
