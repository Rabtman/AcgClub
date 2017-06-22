package com.rabtman.acgclub.mvp.contract;

import com.rabtman.acgclub.mvp.model.entity.ScheduleTimeItem;
import com.rabtman.acgclub.mvp.model.jsoup.AcgScheduleInfo;
import com.rabtman.common.base.mvp.IModel;
import com.rabtman.common.base.mvp.IView;
import io.reactivex.Flowable;
import java.util.List;

/**
 * @author Rabtman
 */

public interface ScheduleTimeContract {

  interface View extends IView {

    void showAcgScheduleInfo(List<ScheduleTimeItem> info);
  }

  interface Model extends IModel {

    Flowable<AcgScheduleInfo> getScheduleInfo();
  }
}
