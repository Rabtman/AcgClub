package com.rabtman.acgschedule.mvp.presenter;

import android.Manifest.permission;
import com.rabtman.acgschedule.R;
import com.rabtman.acgschedule.mvp.contract.ScheduleDetailContract;
import com.rabtman.acgschedule.mvp.model.entity.ScheduleCollection;
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleDetail;
import com.rabtman.common.base.CommonSubscriber;
import com.rabtman.common.base.mvp.BasePresenter;
import com.rabtman.common.di.scope.ActivityScope;
import com.rabtman.common.utils.LogUtil;
import com.rabtman.common.utils.RxUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.subscribers.ResourceSubscriber;
import javax.inject.Inject;

/**
 * @author Rabtman
 */
@ActivityScope
public class ScheduleDetailPresenter extends
    BasePresenter<ScheduleDetailContract.Model, ScheduleDetailContract.View> {

  /**
   * 当前番剧链接
   */
  private String currentScheduleUrl;
  /**
   * 番剧收藏对象
   */
  private ScheduleCollection collection = new ScheduleCollection();

  @Inject
  public ScheduleDetailPresenter(ScheduleDetailContract.Model model,
      ScheduleDetailContract.View rootView) {
    super(model, rootView);
  }

  public void setCurrentScheduleUrl(String url) {
    this.currentScheduleUrl = url;
  }

  public void getScheduleDetail() {
    addSubscribe(
        mModel.getScheduleDetail(currentScheduleUrl)
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

                collection.setScheduleUrl(currentScheduleUrl);
                collection.setName(scheduleDetail.getScheduleTitle());
                collection.setImgUrl(scheduleDetail.getImgUrl());

                mView.showScheduleDetail(scheduleDetail);
              }
            })
    );
  }

  /**
   * 查询是否已经收藏过该番剧
   */
  public void isCollected() {
    addSubscribe(
        mModel.getScheduleCollection(currentScheduleUrl)
            .compose(RxUtil.<ScheduleCollection>rxSchedulerHelper())
            .subscribeWith(new ResourceSubscriber<ScheduleCollection>() {

              private boolean isCollected = false;

              @Override
              public void onNext(ScheduleCollection scheduleCollection) {
                if (scheduleCollection != null) {
                  isCollected = true;
                }
              }

              @Override
              public void onError(Throwable t) {
                t.printStackTrace();
              }

              @Override
              public void onComplete() {
                mView.showCollectionView(isCollected);
              }
            })
    );
  }

  /**
   * 番剧收藏、取消
   */
  public void collectOrCancelSchedule(final boolean isCollected) {
    addSubscribe(
        mModel.addOrDeleteScheduleCollection(collection, !isCollected)
            .subscribe(new Action() {
              @Override
              public void run() throws Exception {
                mView.showCollectionView(!isCollected);
                if (!isCollected) {
                  mView.showMsg(R.string.msg_success_collect_add);
                } else {
                  mView.showMsg(R.string.msg_success_collect_cancel);
                }
              }
            }, new Consumer<Throwable>() {
              @Override
              public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
                if (!isCollected) {
                  mView.showError(R.string.msg_error_collect_add);
                } else {
                  mView.showError(R.string.msg_error_collect_cancel);
                }
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
