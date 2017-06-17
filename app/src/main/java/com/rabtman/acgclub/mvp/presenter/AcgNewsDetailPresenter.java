package com.rabtman.acgclub.mvp.presenter;

import android.text.TextUtils;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.mvp.contract.AcgNewsDetailContract;
import com.rabtman.acgclub.mvp.model.jsoup.AcgNewsDetail;
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
public class AcgNewsDetailPresenter extends
    BasePresenter<AcgNewsDetailContract.Model, AcgNewsDetailContract.View> {

  @Inject
  public AcgNewsDetailPresenter(AcgNewsDetailContract.Model model,
      AcgNewsDetailContract.View rootView) {
    super(model, rootView);
  }

  public void getNewsDetail(String url) {
    if (TextUtils.isEmpty(url)) {
      mView.showError(R.string.msg_error_url_null);
      return;
    }
    addSubscribe(
        mModel.getAcgNewsDetail(url)
            .compose(RxUtil.<AcgNewsDetail>rxSchedulerHelper())
            .subscribeWith(new CommonSubscriber<AcgNewsDetail>(mView) {
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
              public void onNext(AcgNewsDetail acgNewsDetail) {
                LogUtil.d("getScheduleDetail" + acgNewsDetail.toString());
                mView.showNewsDetail(acgNewsDetail);
              }
            })
    );
  }

}
