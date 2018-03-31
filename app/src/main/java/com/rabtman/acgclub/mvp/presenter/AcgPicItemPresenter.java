package com.rabtman.acgclub.mvp.presenter;

import com.rabtman.acgclub.base.constant.HtmlConstant;
import com.rabtman.acgclub.mvp.contract.AcgPicContract;
import com.rabtman.acgclub.mvp.model.jsoup.APic;
import com.rabtman.common.base.CommonSubscriber;
import com.rabtman.common.base.mvp.BasePresenter;
import com.rabtman.common.di.scope.FragmentScope;
import com.rabtman.common.utils.RxUtil;
import javax.inject.Inject;

/**
 * @author Rabtman
 */
@FragmentScope
public class AcgPicItemPresenter extends
    BasePresenter<AcgPicContract.Model, AcgPicContract.View> {

  //当前页面位置
  private int pageNo = 1;

  @Inject
  public AcgPicItemPresenter(AcgPicContract.Model model, AcgPicContract.View rootView) {
    super(model, rootView);
  }

  public void getAcgPicList() {
    pageNo = 1;
    addSubscribe(
        mModel.getAcgPic(HtmlConstant.APIC_URL + pageNo)
            .compose(RxUtil.<APic>rxSchedulerHelper())
            .subscribeWith(new CommonSubscriber<APic>(mView) {
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
              public void onNext(APic aPicPage) {
                mView.showPictures(aPicPage.getPicInfos());
              }
            })
    );
  }

  public void getMoreAcgPicList() {
    pageNo++;
    addSubscribe(
        mModel.getAcgPic(HtmlConstant.APIC_URL + pageNo)
            .compose(RxUtil.<APic>rxSchedulerHelper())
            .subscribeWith(new CommonSubscriber<APic>(mView) {
              @Override
              public void onNext(APic aPicPage) {
                mView.showMorePictures(aPicPage.getPicInfos(),
                    pageNo < aPicPage.getPageCount());
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
