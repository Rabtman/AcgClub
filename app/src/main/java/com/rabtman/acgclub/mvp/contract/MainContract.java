package com.rabtman.acgclub.mvp.contract;

import com.rabtman.acgclub.mvp.model.entity.VersionInfo;
import com.rabtman.common.base.mvp.IModel;
import com.rabtman.common.base.mvp.IView;
import io.reactivex.Flowable;

/**
 * @author Rabtman
 */

public interface MainContract {

  interface View extends IView {

    void showUpdateDialog(VersionInfo versionInfo);
  }

  interface Model extends IModel {

    Flowable<VersionInfo> getVersionInfo();
  }
}
