package com.rabtman.acgclub.mvp.presenter;

import android.text.TextUtils;
import com.rabtman.acgclub.base.constant.HtmlConstant;
import com.rabtman.acgclub.mvp.contract.AcgPicContract;
import com.rabtman.acgclub.mvp.model.jsoup.MoePic;
import com.rabtman.acgclub.mvp.model.jsoup.MoePic.PicInfo;
import com.rabtman.common.base.CommonSubscriber;
import com.rabtman.common.base.mvp.BasePresenter;
import com.rabtman.common.di.scope.FragmentScope;
import com.rabtman.common.utils.LogUtil;
import com.rabtman.common.utils.RxUtil;
import javax.inject.Inject;

/**
 * @author Rabtman
 */
@FragmentScope
public class CartoonPicPresenter extends
    BasePresenter<AcgPicContract.Model, AcgPicContract.View<PicInfo>> {

  //下一页地址
  private String nextUrl;

  @Inject
  public CartoonPicPresenter(AcgPicContract.Model model, AcgPicContract.View rootView) {
    super(model, rootView);
  }

  public void getCartoonPictures() {
    nextUrl = HtmlConstant.MPP_URL;
    addSubscribe(
        mModel.getMoePictures(HtmlConstant.MPP_URL)
            .compose(RxUtil.<MoePic>rxSchedulerHelper())
            .subscribeWith(new CommonSubscriber<MoePic>(mView) {
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
              public void onNext(MoePic aPicPage) {
                LogUtil.d("getCartoonPictures" + aPicPage.toString());
                nextUrl = aPicPage.getNextUrl();
                mView.showPictures(aPicPage.getPicInfos());
              }
            })
    );
  }

  public void getMoreCartoonPictures() {
    addSubscribe(
        mModel.getMoePictures(nextUrl)
            .compose(RxUtil.<MoePic>rxSchedulerHelper())
            .subscribeWith(new CommonSubscriber<MoePic>(mView) {
              @Override
              public void onNext(MoePic aPicPage) {
                LogUtil.d("getMoreCartoonPictures\n" + aPicPage.toString());
                nextUrl = aPicPage.getNextUrl();
                mView.showMorePictures(aPicPage.getPicInfos(),
                    !TextUtils.isEmpty(nextUrl));
              }

              @Override
              public void onError(Throwable e) {
                super.onError(e);
                mView.onLoadMoreFail();
              }
            })
    );
  }

}
