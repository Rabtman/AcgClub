package com.rabtman.acgschedule.mvp.contract;

import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleVideo;
import com.rabtman.common.base.mvp.IModel;
import com.rabtman.common.base.mvp.IView;
import io.reactivex.Flowable;

/**
 * @author Rabtman
 */
public interface ScheduleVideoContract {

  interface View extends IView {

    void showScheduleVideo(String videoUrl, boolean isVideo);
  }

  interface Model extends IModel {

    Flowable<ScheduleVideo> getScheduleVideo(String url);
  }
}
