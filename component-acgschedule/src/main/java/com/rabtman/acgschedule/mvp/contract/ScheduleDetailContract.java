package com.rabtman.acgschedule.mvp.contract;

import com.rabtman.acgschedule.mvp.model.entity.ScheduleCollection;
import com.rabtman.acgschedule.mvp.model.entity.ScheduleHistory;
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleDetail;
import com.rabtman.common.base.mvp.IModel;
import com.rabtman.common.base.mvp.IView;
import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * @author Rabtman
 */
public interface ScheduleDetailContract {

  interface View extends IView {

    void showScheduleDetail(ScheduleDetail scheduleDetail);

    void showCollectionView(boolean isCollected);

    void start2ScheduleVideo(String videoUrl);
  }

  interface Model extends IModel {

    Flowable<ScheduleDetail> getScheduleDetail(String url);

    Flowable<ScheduleCollection> getScheduleCollection(String scheduleUrl);

    Flowable<ScheduleHistory> getScheduleHistory(String scheduleUrl);

    Completable addOrDeleteScheduleCollection(ScheduleCollection item, boolean isAdd);
  }
}
