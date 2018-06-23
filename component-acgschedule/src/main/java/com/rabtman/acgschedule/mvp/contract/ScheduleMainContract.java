package com.rabtman.acgschedule.mvp.contract;

import com.rabtman.acgschedule.mvp.model.jsoup.DilidiliInfo;
import com.rabtman.common.base.mvp.IModel;
import com.rabtman.common.base.mvp.IView;
import io.reactivex.Flowable;

/**
 * @author Rabtman
 */

public interface ScheduleMainContract {

  interface View extends IView {

    void showDilidiliInfo(DilidiliInfo dilidiliInfo);

    void start2ScheduleVideo(String videoUrl);
  }

  interface Model extends IModel {

    Flowable<DilidiliInfo> getDilidiliInfo();
  }
}
