package com.rabtman.acgclub.mvp.presenter;

import android.text.TextUtils;
import com.rabtman.acgclub.mvp.contract.ScheduleVideoContract;
import com.rabtman.acgclub.mvp.model.jsoup.ScheduleVideo;
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
public class ScheduleVideoPresenter extends
    BasePresenter<ScheduleVideoContract.Model, ScheduleVideoContract.View> {

  @Inject
  public ScheduleVideoPresenter(ScheduleVideoContract.Model model,
      ScheduleVideoContract.View rootView) {
    super(model, rootView);
  }

  public void getScheduleVideo(final String videoUrl) {
    if (TextUtils.isEmpty(videoUrl)) {
      mView.showError("视频链接不见了/(ㄒoㄒ)/~~");
      return;
    }
    addSubscribe(
        mModel.getScheduleVideo(videoUrl)
            .compose(RxUtil.<ScheduleVideo>rxSchedulerHelper())
            .subscribeWith(new CommonSubscriber<ScheduleVideo>(mView) {

              @Override
              public void onComplete() {
                mView.hideLoading();
              }

              @Override
              public void onNext(ScheduleVideo scheduleVideo) {
                LogUtil.d("getScheduleVideo" + scheduleVideo.toString());
                mView.showScheduleVideo(scheduleVideo.getVideoUrl());
              }
            })
    );
  }

}
