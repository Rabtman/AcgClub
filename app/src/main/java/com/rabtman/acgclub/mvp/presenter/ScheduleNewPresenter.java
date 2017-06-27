package com.rabtman.acgclub.mvp.presenter;

import android.text.TextUtils;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.mvp.contract.ScheduleNewContract;
import com.rabtman.acgclub.mvp.model.jsoup.ScheduleNew;
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
public class ScheduleNewPresenter extends
    BasePresenter<ScheduleNewContract.Model, ScheduleNewContract.View> {

  @Inject
  public ScheduleNewPresenter(ScheduleNewContract.Model model, ScheduleNewContract.View rootView) {
    super(model, rootView);
  }

  public void getScheduleNew(String url) {
    if (TextUtils.isEmpty(url)) {
      mView.showError(R.string.msg_error_url_null);
      return;
    }

    addSubscribe(
        mModel.getScheduleNew(url)
            .compose(RxUtil.<ScheduleNew>rxSchedulerHelper())
            .subscribeWith(new CommonSubscriber<ScheduleNew>(mView) {
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
              public void onNext(ScheduleNew scheduleNew) {
                LogUtil.d("getScheduleNewList" + scheduleNew.toString());
                mView.showScheduleNew(scheduleNew);
              }
            })
    );
  }

}
