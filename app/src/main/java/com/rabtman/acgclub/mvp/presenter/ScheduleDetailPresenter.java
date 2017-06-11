package com.rabtman.acgclub.mvp.presenter;

import android.text.TextUtils;
import com.rabtman.acgclub.mvp.contract.ScheduleDetailContract;
import com.rabtman.acgclub.mvp.model.jsoup.ScheduleDetail;
import com.rabtman.common.base.CommonSubscriber;
import com.rabtman.common.base.mvp.BasePresenter;
import com.rabtman.common.di.scope.ActivityScope;
import com.rabtman.common.utils.LogUtil;
import com.rabtman.common.utils.RxUtil;
import javax.inject.Inject;

/**
 * @author Rabtman
 */
@ActivityScope
public class ScheduleDetailPresenter extends
    BasePresenter<ScheduleDetailContract.Model, ScheduleDetailContract.View> {

  @Inject
  public ScheduleDetailPresenter(ScheduleDetailContract.Model model,
      ScheduleDetailContract.View rootView) {
    super(model, rootView);
  }

  public void getScheduleDetail(String url) {
    if (TextUtils.isEmpty(url)) {
      mView.showError("大脑一片空白！");
      return;
    }
    addSubscribe(
        mModel.getScheduleDetail(url)
            .compose(RxUtil.<ScheduleDetail>rxSchedulerHelper())
            .subscribeWith(new CommonSubscriber<ScheduleDetail>(mView) {
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
              public void onNext(ScheduleDetail scheduleDetail) {
                LogUtil.d("getScheduleDetail" + scheduleDetail.toString());
                mView.showScheduleDetail(scheduleDetail);
              }
            })
    );
  }

}
