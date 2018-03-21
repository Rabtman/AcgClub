package com.rabtman.acgschedule.mvp.presenter;

import android.Manifest.permission;
import android.text.TextUtils;
import com.rabtman.acgschedule.R;
import com.rabtman.acgschedule.mvp.contract.ScheduleDetailContract;
import com.rabtman.acgschedule.mvp.model.entity.ScheduleCollection;
import com.rabtman.acgschedule.mvp.model.entity.ScheduleHistory;
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleDetail;
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleDetail.ScheduleEpisode;
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
import java.util.List;
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
   * 当前番剧详情
   */
  private ScheduleDetail currentScheduleDetail;
  /**
   * 当前番剧播放记录
   */
  private ScheduleHistory currentScheduleHistory;
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

                currentScheduleDetail = scheduleDetail;

                mView.showScheduleDetail(scheduleDetail);

                if (currentScheduleHistory != null) {
                  mView.showLastReadRecord(currentScheduleHistory.getLastRecord());
                }
              }
            })
    );
  }

  /**
   * 获取番剧观看历史记录
   *
   * @param isManualClick 是否主动点击
   */
  public void getLastReadRecord(final RxPermissions rxPermissions, final boolean isManualClick) {
    //如果播放记录还没加载成功，则先加载
    if (currentScheduleHistory == null) {
      addSubscribe(
          mModel.getScheduleHistory(currentScheduleUrl)
              .compose(RxUtil.<ScheduleHistory>rxSchedulerHelper())
              .subscribeWith(new ResourceSubscriber<ScheduleHistory>() {
                @Override
                public void onNext(ScheduleHistory scheduleHistory) {
                  currentScheduleHistory = scheduleHistory;
                }

                @Override
                public void onError(Throwable t) {
                  t.printStackTrace();
                  mView.showError(R.string.msg_error_unknown);
                }

                @Override
                public void onComplete() {
                  //如果没有播放记录，则默认播放第一集
                  if (currentScheduleHistory == null) {
                    currentScheduleHistory = new ScheduleHistory();
                    currentScheduleHistory.setScheduleUrl(currentScheduleUrl);
                    currentScheduleHistory.setLastRecord(-1);
                  }
                  //手动点击，则在家加载完记录后跳转到视频播放
                  if (isManualClick) {
                    checkPermission2ScheduleVideo(
                        rxPermissions,
                        getNextScheduleUrl(currentScheduleDetail,
                            currentScheduleHistory.getLastRecord()));
                  }
                }
              })
      );
    } else {
      if (isManualClick) {
        checkPermission2ScheduleVideo(rxPermissions,
            getNextScheduleUrl(currentScheduleDetail, currentScheduleHistory.getLastRecord()));
      }
    }
  }

  /**
   * 根据历史记录获取当前应播放番剧
   */
  private String getNextScheduleUrl(ScheduleDetail scheduleDetail, int lastPos) {
    if (!validScheduleDetail(scheduleDetail)) {
      return "";
    }
    int nextPos = getNextPos(scheduleDetail, lastPos);

    //获取地址的同时，更新历史记录
    updateScheduleReadRecord(nextPos);

    return scheduleDetail.getScheduleEpisodes().get(nextPos).getLink();
  }

  /**
   * 获取下一个观看位置
   */
  private int getNextPos(ScheduleDetail scheduleDetail, int lastPos) {
    if (!validScheduleDetail(scheduleDetail)) {
      return -1;
    }
    List<ScheduleEpisode> scheduleEpisodes = scheduleDetail.getScheduleEpisodes();
    if ((lastPos + 1) >= (scheduleEpisodes.size() - 2)) {
      return scheduleEpisodes.size() - 2;
    } else {
      return lastPos + 1;
    }
  }

  /**
   * 验证番剧信息有效性
   */
  private boolean validScheduleDetail(ScheduleDetail scheduleDetail) {
    if (scheduleDetail == null) {
      return false;
    }
    List<ScheduleEpisode> scheduleEpisodes = scheduleDetail.getScheduleEpisodes();
    return scheduleEpisodes != null && scheduleEpisodes.size() > 0;
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
                isCollected = (scheduleCollection != null);
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

  /**
   * 记录上一次番剧观看位置
   *
   * @param lastPos 上一次观看位置
   */
  public void updateScheduleReadRecord(final int lastPos) {
    mModel.updateScheduleHistory(currentScheduleUrl, lastPos)
        .subscribe(new Action() {
          @Override
          public void run() throws Exception {
            if (currentScheduleHistory == null) {
              currentScheduleHistory = new ScheduleHistory();
            }
            currentScheduleHistory.setScheduleUrl(currentScheduleUrl);
            currentScheduleHistory.setLastRecord(lastPos);
            mView.showLastReadRecord(lastPos);
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {
            throwable.printStackTrace();
          }
        });
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
