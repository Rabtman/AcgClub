package com.rabtman.acgclub.mvp.presenter;

import com.rabtman.acgclub.mvp.contract.ScheduleTimeContract;
import com.rabtman.acgclub.mvp.model.jsoup.AcgScheduleInfo;
import com.rabtman.common.base.CommonSubscriber;
import com.rabtman.common.base.mvp.BasePresenter;
import com.rabtman.common.di.scope.FragmentScope;
import com.rabtman.common.utils.LogUtil;
import com.rabtman.common.utils.RxUtil;
import javax.inject.Inject;

/**
 * @author Rabtman
 */

@FragmentScope
public class ScheduleTimePresenter extends
    BasePresenter<ScheduleTimeContract.Model, ScheduleTimeContract.View> {

  @Inject
  public ScheduleTimePresenter(ScheduleTimeContract.Model model,
      ScheduleTimeContract.View rootView) {
    super(model, rootView);
  }

  public void getAcgSchedule() {
    addSubscribe(
        mModel.getScheduleInfo()
            .compose(RxUtil.<AcgScheduleInfo>rxSchedulerHelper())
            .subscribeWith(new CommonSubscriber<AcgScheduleInfo>(mView) {
              @Override
              protected void onStart() {
                super.onStart();
                mView.showLoading();
              }

              @Override
              public void onComplete() {
                mView.hideLoading();
              }

              @Override
              public void onNext(AcgScheduleInfo acgScheduleInfo) {
                LogUtil.d("getAcgSchedule");
                mView.showAcgScheduleInfo(acgScheduleInfo);
              }
            })
    );
  }

}
