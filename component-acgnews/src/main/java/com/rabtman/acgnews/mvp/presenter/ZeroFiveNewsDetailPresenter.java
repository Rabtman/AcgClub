package com.rabtman.acgnews.mvp.presenter;

import android.Manifest;
import android.text.TextUtils;
import com.rabtman.acgnews.R;
import com.rabtman.acgnews.mvp.contract.ZeroFiveNewsDetailContract;
import com.rabtman.acgnews.mvp.model.jsoup.ZeroFiveNewsDetail;
import com.rabtman.common.base.CommonSubscriber;
import com.rabtman.common.base.mvp.BasePresenter;
import com.rabtman.common.di.scope.ActivityScope;
import com.rabtman.common.utils.RxUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import javax.inject.Inject;

/**
 * @author Rabtman
 */
@ActivityScope
public class ZeroFiveNewsDetailPresenter extends
    BasePresenter<ZeroFiveNewsDetailContract.Model, ZeroFiveNewsDetailContract.View> {

  @Inject
  public ZeroFiveNewsDetailPresenter(ZeroFiveNewsDetailContract.Model model,
      ZeroFiveNewsDetailContract.View rootView) {
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
            .compose(RxUtil.<ZeroFiveNewsDetail>rxSchedulerHelper())
            .subscribeWith(new CommonSubscriber<ZeroFiveNewsDetail>(mView) {

              @Override
              public void onError(Throwable e) {
                super.onError(e);
                mView.showPageError();
              }

              @Override
              public void onNext(ZeroFiveNewsDetail zeroFiveNewsDetail) {
                mView.showNewsDetail(zeroFiveNewsDetail);
                mView.showPageContent();
              }
            })
    );
  }

}
