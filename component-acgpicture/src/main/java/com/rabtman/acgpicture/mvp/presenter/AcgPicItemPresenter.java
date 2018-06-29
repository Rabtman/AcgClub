package com.rabtman.acgpicture.mvp.presenter;


import com.rabtman.acgpicture.base.constant.HtmlConstant;
import com.rabtman.acgpicture.mvp.AcgPicContract;
import com.rabtman.acgpicture.mvp.model.entity.APicturePage;
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
        mModel.getAPictures(HtmlConstant.APICTURE_ANIME_URL + pageNo)
            .compose(RxUtil.<APicturePage>rxSchedulerHelper())
            .subscribeWith(new CommonSubscriber<APicturePage>(mView) {
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
              public void onNext(APicturePage aPicturePagePage) {
                if (aPicturePagePage.getItems() == null || aPicturePagePage.getItems().isEmpty()) {
                  mView.showPageEmpty();
                } else {
                  mView.showPictures(aPicturePagePage.getItems());
                  mView.showPageContent();
                }
              }
            })
    );
  }

  public void getMoreAcgPicList() {
    pageNo++;
    addSubscribe(
        mModel.getAPictures(HtmlConstant.APICTURE_ANIME_URL + pageNo)
            .compose(RxUtil.<APicturePage>rxSchedulerHelper())
            .subscribeWith(new CommonSubscriber<APicturePage>(mView) {
              @Override
              public void onNext(APicturePage aPicturePagePage) {
                if (aPicturePagePage.getItems() == null || aPicturePagePage.getItems().isEmpty()) {
                  mView.showPageEmpty();
                } else {
                  mView.showMorePictures(aPicturePagePage.getItems(),
                      pageNo < aPicturePagePage.getPageCount());
                  mView.showPageContent();
                }
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
