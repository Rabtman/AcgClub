package com.rabtman.acgclub.mvp.contract;

import com.rabtman.acgclub.mvp.model.jsoup.AcgScheduleInfo;
import com.rabtman.acgclub.mvp.model.jsoup.ScheduleWeek;
import com.rabtman.common.base.mvp.IModel;
import com.rabtman.common.base.mvp.IView;
import io.reactivex.Flowable;

/**
 * @author Rabtman
 */

public interface ScheduleMainContract {

  interface View extends IView {
    void showAcgScheduleInfo(AcgScheduleInfo info);
  }

  interface Model extends IModel {
    Flowable<AcgScheduleInfo> getScheduleInfo();
  }
}
