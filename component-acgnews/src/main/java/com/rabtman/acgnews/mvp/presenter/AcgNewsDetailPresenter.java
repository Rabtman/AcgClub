package com.rabtman.acgnews.mvp.presenter;

import android.Manifest;
import android.text.TextUtils;
import com.rabtman.acgnews.R;
import com.rabtman.acgnews.mvp.contract.AcgNewsDetailContract;
import com.rabtman.acgnews.mvp.model.jsoup.AcgNewsDetail;
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
public class AcgNewsDetailPresenter extends
    BasePresenter<AcgNewsDetailContract.Model, AcgNewsDetailContract.View> {

  @Inject
  public AcgNewsDetailPresenter(AcgNewsDetailContract.Model model,
      AcgNewsDetailContract.View rootView) {
    super(model, rootView);
  }

  public void start2Share(RxPermissions rxPermissions) {
    rxPermissions.request(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.WRITE_EXTERNAL_STORAGE)
        .subscribe(new Consumer<Boolean>() {
          @Override
          public void accept(@NonNull Boolean aBoolean) throws Exception {
            if (aBoolean) {
              mView.showShareView();
            } else {
              mView.showError(R.string.msg_error_check_permission);
            }
          }
        });
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
