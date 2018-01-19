package com.rabtman.acgschedule.mvp.presenter;

import android.Manifest.permission;
import android.text.TextUtils;
import com.rabtman.acgschedule.R;
import com.rabtman.acgschedule.mvp.contract.ScheduleDetailContract;
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleDetail;
import com.rabtman.common.base.CommonSubscriber;
import com.rabtman.common.base.mvp.BasePresenter;
import com.rabtman.common.di.scope.ActivityScope;
import com.rabtman.common.utils.LogUtil;
import com.rabtman.common.utils.RxUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
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
      mView.showError(R.string.msg_error_url_null);
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

  public void checkPermission2ScheduleVideo(RxPermissions rxPermissions, final String videoUrl) {
    rxPermissions.request(permission.WRITE_EXTERNAL_STORAGE,
        permission.READ_PHONE_STATE,
        permission.ACCESS_NETWORK_STATE,
        permission.ACCESS_WIFI_STATE)
        .subscribe(new Consumer<Boolean>() {
          @Override
          public void accept(@NonNull Boolean aBoolean) throws Exception {
            if (aBoolean) {
              mView.start2ScheduleVideo(videoUrl);
            } else {
              mView.showError(R.string.msg_error_check_permission);
            }
          }
        });
  }

}
