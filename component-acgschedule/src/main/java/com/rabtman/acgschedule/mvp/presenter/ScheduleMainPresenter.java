package com.rabtman.acgschedule.mvp.presenter;

import com.rabtman.acgschedule.mvp.contract.ScheduleMainContract;
import com.rabtman.acgschedule.mvp.model.jsoup.DilidiliInfo;
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
public class ScheduleMainPresenter extends
    BasePresenter<ScheduleMainContract.Model, ScheduleMainContract.View> {

  @Inject
  public ScheduleMainPresenter(ScheduleMainContract.Model model,
      ScheduleMainContract.View rootView) {
    super(model, rootView);
  }

  public void getDilidiliInfo() {
    addSubscribe(
        mModel.getDilidiliInfo()
            .compose(RxUtil.<DilidiliInfo>rxSchedulerHelper())
            .subscribeWith(new CommonSubscriber<DilidiliInfo>(mView) {
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
              public void onNext(DilidiliInfo dilidiliInfo) {
                LogUtil.d("getDilidiliInfo");
                mView.showDilidiliInfo(dilidiliInfo);
              }
            })
    );
  }

}
