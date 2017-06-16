package com.rabtman.acgclub.mvp.presenter;

import android.Manifest.permission;
import android.app.Activity;
import android.text.TextUtils;
import com.rabtman.acgclub.base.constant.SystemConstant;
import com.rabtman.acgclub.mvp.contract.APicDetailContract;
import com.rabtman.acgclub.mvp.model.jsoup.APicDetail;
import com.rabtman.common.base.CommonSubscriber;
import com.rabtman.common.base.mvp.BasePresenter;
import com.rabtman.common.di.scope.ActivityScope;
import com.rabtman.common.utils.LogUtil;
import com.rabtman.common.utils.RxUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;
import zlc.season.rxdownload2.RxDownload;

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

  public void downloadPicture(Activity context, String imgUrl) {
    new RxPermissions(context)
        .request(permission.WRITE_EXTERNAL_STORAGE)
        .doOnNext(new Consumer<Boolean>() {
          @Override
          public void accept(@NonNull Boolean aBoolean) throws Exception {
            if (!aBoolean) {
              throw new RuntimeException("no permission");
            }
          }
        })
        .observeOn(Schedulers.io())
        .compose(RxDownload.getInstance(context)
            .transformService(imgUrl, null, SystemConstant.getImgPath(context)))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<Object>() {

          @Override
          public void accept(@NonNull Object o) throws Exception {
            mView.start2Download();
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(@NonNull Throwable throwable) throws Exception {
            throwable.printStackTrace();
            mView.showError("图片保存失败(╯﹏╰)");
          }
        });

  }
}
