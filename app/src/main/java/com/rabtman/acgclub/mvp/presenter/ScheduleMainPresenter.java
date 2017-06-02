package com.rabtman.acgclub.mvp.presenter;

import com.rabtman.acgclub.mvp.contract.ScheduleMainContract;
import com.rabtman.acgclub.mvp.model.jsoup.AcgScheduleInfo;
import com.rabtman.acgclub.mvp.model.jsoup.ScheduleWeek;
import com.rabtman.acgclub.mvp.model.jsoup.ScheduleWeek.ScheduleItem;
import com.rabtman.common.base.CommonSubscriber;
import com.rabtman.common.base.mvp.BasePresenter;
import com.rabtman.common.di.scope.FragmentScope;
import com.rabtman.common.utils.RxUtil;
import java.util.List;
import javax.inject.Inject;

/**
 * @author Rabtman
 */

@FragmentScope
public class ScheduleMainPresenter extends
    BasePresenter<ScheduleMainContract.Model, ScheduleMainContract.View> {

  @Inject
  public ScheduleMainPresenter(ScheduleMainContract.Model model, ScheduleMainContract.View rootView) {
    super(model, rootView);
  }

  public void getAcgSchedule(){
    addSubscribe(
        mModel.getScheduleInfo()
        .compose(RxUtil.<AcgScheduleInfo>rxSchedulerHelper())
        .subscribeWith(new CommonSubscriber<AcgScheduleInfo>(mView) {
          @Override
          public void onNext(AcgScheduleInfo acgScheduleInfo) {
            mView.showAcgScheduleInfo(acgScheduleInfo);
          }
        })
    );
  }

}
