package com.rabtman.acgschedule.mvp.presenter;

import com.rabtman.acgschedule.base.constant.HtmlConstant;
import com.rabtman.acgschedule.mvp.contract.ScheduleNewContract;
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleNew;
import com.rabtman.common.base.CommonSubscriber;
import com.rabtman.common.base.mvp.BasePresenter;
import com.rabtman.common.di.scope.ActivityScope;
import com.rabtman.common.utils.RxUtil;
import java.util.Calendar;
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

  public void getScheduleNew() {
    String url = getScheduleNewUrl();

    addSubscribe(
        mModel.getScheduleNew(url)
            .compose(RxUtil.<ScheduleNew>rxSchedulerHelper())
            .subscribeWith(new CommonSubscriber<ScheduleNew>(mView) {
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
              public void onNext(ScheduleNew scheduleNew) {
                mView.showScheduleNew(scheduleNew);
                if (scheduleNew.getScheduleNewItems().size() > 0) {
                  mView.showPageContent();
                } else {
                  mView.showPageEmpty();
                }
              }
            })
    );
  }

  //获取本季新番列表地址
  private String getScheduleNewUrl() {
    StringBuilder urlBuilder = new StringBuilder(HtmlConstant.DILIDILI_URL);
    urlBuilder.append("/anime/");
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(System.currentTimeMillis());

    urlBuilder.append(cal.get(Calendar.YEAR));

    int month = cal.get(Calendar.MONTH) + 1;
    if (1 <= month && month < 4) {//一月新番
      urlBuilder.append("01");
    } else if (4 <= month && month < 7) {//四月新番
      urlBuilder.append("04");
    } else if (7 <= month && month < 10) {//七月新番
      urlBuilder.append("07");
    } else {//十月新番
      urlBuilder.append("10");
    }
    return urlBuilder.toString();
  }

}
