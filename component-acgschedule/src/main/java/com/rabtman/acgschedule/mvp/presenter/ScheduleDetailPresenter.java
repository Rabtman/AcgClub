package com.rabtman.acgschedule.mvp.presenter;

import android.Manifest.permission;
import android.text.TextUtils;
import com.rabtman.acgschedule.R;
import com.rabtman.acgschedule.mvp.contract.ScheduleDetailContract;
import com.rabtman.acgschedule.mvp.model.entity.ScheduleCache;
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleDetail;
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleDetail.ScheduleEpisode;
import com.rabtman.common.base.CommonSubscriber;
import com.rabtman.common.base.mvp.BasePresenter;
import com.rabtman.common.di.scope.ActivityScope;
import com.rabtman.common.utils.RxUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.annotations.NonNull;
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
   * 本地缓存加载标识
   */
  private boolean loadCache = false;
  /**
   * 当前番剧的本地缓存
   */
  private ScheduleCache curScheduleCache = new ScheduleCache();

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
              public void onComplete() {
                mView.hideLoading();
              }

              @Override
              public void onError(Throwable e) {
                super.onError(e);
                mView.showPageError();
              }

              @Override
              public void onNext(ScheduleDetail scheduleDetail) {
                curScheduleCache.setScheduleUrl(currentScheduleUrl);
                curScheduleCache.setName(scheduleDetail.getScheduleTitle());
                curScheduleCache.setImgUrl(scheduleDetail.getImgUrl());

                currentScheduleDetail = scheduleDetail;

                mView.showScheduleDetail(scheduleDetail);
                mView.showPageContent();

                if (loadCache) {
                  mView.showScheduleCacheStatus(curScheduleCache);
                }
              }
            })
    );
  }

  /**
   * 查询该番剧的缓存信息
   *
   * @param isManualClick 是否主动点击
   */
  public void getCurrentScheduleCache(final RxPermissions rxPermissions,
      final boolean isManualClick) {
    //如果播放记录还没加载成功，则先加载
    if (!TextUtils.isEmpty(curScheduleCache.getScheduleUrl()) && isManualClick) {
      checkPermission2ScheduleVideo(rxPermissions,
          getNextScheduleUrl(currentScheduleDetail, curScheduleCache.getLastWatchPos()));
    } else {
      addSubscribe(
          mModel.getScheduleCacheByUrl(currentScheduleUrl)
              .compose(RxUtil.<ScheduleCache>rxSchedulerHelper())
              .subscribeWith(new ResourceSubscriber<ScheduleCache>() {
                @Override
                public void onNext(ScheduleCache scheduleCache) {
                  curScheduleCache.setCollect(scheduleCache.isCollect());
                  curScheduleCache.setLastWatchPos(scheduleCache.getLastWatchPos());
                }

                @Override
                public void onError(Throwable t) {
                  t.printStackTrace();
                  mView.showError(R.string.msg_error_unknown);
                }

                @Override
                public void onComplete() {
                  loadCache = true;
                  if (!TextUtils.isEmpty(curScheduleCache.getScheduleUrl())) {
                    mView.showScheduleCacheStatus(curScheduleCache);
                  }
                  //手动点击，则在加载完记录后跳转到视频播放
                  if (isManualClick) {
                    checkPermission2ScheduleVideo(
                        rxPermissions,
                        getNextScheduleUrl(currentScheduleDetail,
                            curScheduleCache.getLastWatchPos()));
                  }
                }
              })
      );
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
   * 番剧收藏、取消
   */
  public void collectOrCancelSchedule(final boolean isCollected) {
    addSubscribe(
        mModel.collectSchedule(curScheduleCache, !isCollected)
            .compose(RxUtil.<ScheduleCache>rxSchedulerHelper())
            .subscribe(new Consumer<ScheduleCache>() {
              @Override
              public void accept(ScheduleCache scheduleCache) throws Exception {
                curScheduleCache.setCollect(!isCollected);
                mView.showScheduleCacheStatus(curScheduleCache);
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
   * @param lastWatchPos 上一次观看位置
   */
  public void updateScheduleReadRecord(final int lastWatchPos) {
    addSubscribe(
        mModel.updateScheduleWatchRecord(curScheduleCache, lastWatchPos)
            .compose(RxUtil.<ScheduleCache>rxSchedulerHelper())
            .subscribe(new Consumer<ScheduleCache>() {
              @Override
              public void accept(ScheduleCache scheduleCache) throws Exception {
                curScheduleCache.setLastWatchPos(lastWatchPos);
                mView.showScheduleCacheStatus(curScheduleCache);
              }
            }, new Consumer<Throwable>() {
              @Override
              public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
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
