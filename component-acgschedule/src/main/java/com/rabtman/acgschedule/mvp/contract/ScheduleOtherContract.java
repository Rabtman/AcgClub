package com.rabtman.acgschedule.mvp.contract;

import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleOtherPage;
import com.rabtman.common.base.mvp.IModel;
import com.rabtman.common.base.mvp.IView;
import io.reactivex.Flowable;

/**
 * @author Rabtman
 */
public interface ScheduleOtherContract {

  interface View extends IView {

    void showScheduleOther(ScheduleOtherPage scheduleOtherPage);

    void showMoreScheduleOther(ScheduleOtherPage scheduleOtherPage, boolean canLoadMore);

    void onLoadMoreFail();
  }

  interface Model extends IModel {

    Flowable<ScheduleOtherPage> getScheduleOtherPage(String url);
  }
}
