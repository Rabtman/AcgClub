package com.rabtman.acgclub.mvp.presenter;

import android.text.TextUtils;
import com.rabtman.acgclub.mvp.contract.APicDetailContract;
import com.rabtman.acgclub.mvp.model.jsoup.APicDetail;
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
public class APicDetailPresenter extends
    BasePresenter<APicDetailContract.Model, APicDetailContract.View> {

  @Inject
  public APicDetailPresenter(APicDetailContract.Model model,
      APicDetailContract.View rootView) {
    super(model, rootView);
  }

  public void getAPicDetail(String url) {
    if (TextUtils.isEmpty(url)) {
      mView.showError("大脑一片空白！");
      return;
    }
    addSubscribe(
        mModel.getAPicDetail(url)
            .compose(RxUtil.<APicDetail>rxSchedulerHelper())
            .subscribeWith(new CommonSubscriber<APicDetail>(mView) {
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
              public void onNext(APicDetail acgNewsDetail) {
                LogUtil.d("getScheduleDetail" + acgNewsDetail.toString());
                mView.showAPictures(acgNewsDetail);
              }
            })
    );
  }

}
