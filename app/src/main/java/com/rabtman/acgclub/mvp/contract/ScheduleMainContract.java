package com.rabtman.acgclub.mvp.contract;

import com.rabtman.acgclub.mvp.model.jsoup.DilidiliInfo;
import com.rabtman.common.base.mvp.IModel;
import com.rabtman.common.base.mvp.IView;
import io.reactivex.Flowable;

/**
 * @author Rabtman
 */

public interface ScheduleMainContract {

  interface View extends IView {

    void showDilidiliInfo(DilidiliInfo dilidiliInfo);
  }

  interface Model extends IModel {

    Flowable<DilidiliInfo> getDilidiliInfo();
  }
}
