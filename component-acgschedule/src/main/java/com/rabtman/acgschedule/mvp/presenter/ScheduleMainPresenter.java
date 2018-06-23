package com.rabtman.acgschedule.mvp.presenter;

import android.Manifest.permission;
import android.text.TextUtils;
import com.rabtman.acgschedule.R;
import com.rabtman.acgschedule.mvp.contract.ScheduleMainContract;
import com.rabtman.acgschedule.mvp.model.jsoup.DilidiliInfo;
import com.rabtman.common.base.CommonSubscriber;
import com.rabtman.common.base.mvp.BasePresenter;
import com.rabtman.common.di.scope.FragmentScope;
import com.rabtman.common.utils.RxUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
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
              public void onComplete() {
                mView.hideLoading();
              }

              @Override
              public void onError(Throwable e) {
                super.onError(e);
                mView.showPageError();
              }

              @Override
              public void onNext(DilidiliInfo dilidiliInfo) {
                mView.showDilidiliInfo(dilidiliInfo);
                mView.showPageContent();
              }
            })
    );
  }

  /**
   * 视频观看权限申请
   */
  public void checkPermission2ScheduleVideo(RxPermissions rxPermissions, final String videoUrl) {
    if (TextUtils.isEmpty(videoUrl)) {
      mView.showError(R.string.msg_error_url_null);
      return;
    }
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
