package com.rabtman.acgschedule.mvp.presenter;

import com.rabtman.acgschedule.mvp.contract.ScheduleOtherContract;
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleOtherPage;
import com.rabtman.common.base.CommonSubscriber;
import com.rabtman.common.base.mvp.BasePresenter;
import com.rabtman.common.di.scope.ActivityScope;
import com.rabtman.common.utils.RxUtil;
import javax.inject.Inject;

/**
 * @author Rabtman
 */
@ActivityScope
public class ScheduleOtherPresenter extends
    BasePresenter<ScheduleOtherContract.Model, ScheduleOtherContract.View> {

  /**
   * 当前视频类别地址
   */
  private String curScheduleOtherUrl;

  /**
   * 当前页数
   */
  private int pageNo = 1;

  @Inject
  public ScheduleOtherPresenter(ScheduleOtherContract.Model model,
      ScheduleOtherContract.View rootView) {
    super(model, rootView);
  }

  public void setCurScheduleOtherUrl(String curScheduleOtherUrl) {
    this.curScheduleOtherUrl = curScheduleOtherUrl;
  }

  public void getScheduleOther() {
    pageNo = 1;
    addSubscribe(
        mModel.getScheduleOtherPage(curScheduleOtherUrl + pageNo + ".html")
            .compose(RxUtil.<ScheduleOtherPage>rxSchedulerHelper())
            .subscribeWith(new CommonSubscriber<ScheduleOtherPage>(mView) {
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
              public void onNext(ScheduleOtherPage scheduleOther) {
                mView.showScheduleOther(scheduleOther);
              }
            })
    );
  }

  public void getMoreScheduleOther() {
    addSubscribe(
        mModel.getScheduleOtherPage(curScheduleOtherUrl + (++pageNo) + ".html")
            .compose(RxUtil.<ScheduleOtherPage>rxSchedulerHelper())
            .subscribeWith(new CommonSubscriber<ScheduleOtherPage>(mView) {
              @Override
              public void onNext(ScheduleOtherPage scheduleOther) {
                mView.showMoreScheduleOther(scheduleOther, pageNo < scheduleOther.getPageCount());
              }

              @Override
              public void onError(Throwable e) {
                super.onError(e);
                mView.onLoadMoreFail();
                pageNo--;
              }
            })
    );
  }

}
