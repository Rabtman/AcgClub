package com.rabtman.acgnews.mvp.presenter;

import com.rabtman.acgnews.base.constant.HtmlConstant;
import com.rabtman.acgnews.mvp.contract.AcgNewsContract;
import com.rabtman.acgnews.mvp.model.jsoup.AcgNewsPage;
import com.rabtman.common.base.CommonSubscriber;
import com.rabtman.common.base.mvp.BasePresenter;
import com.rabtman.common.di.scope.FragmentScope;
import com.rabtman.common.utils.RxUtil;
import javax.inject.Inject;

/**
 * @author Rabtman
 */
@FragmentScope
public class AcgNewsItemPresenter extends
    BasePresenter<AcgNewsContract.Model, AcgNewsContract.View> {

  //当前页面位置
  private int pageNo = 1;

  @Inject
  public AcgNewsItemPresenter(AcgNewsContract.Model model, AcgNewsContract.View rootView) {
    super(model, rootView);
  }

  public void getAcgNewsList() {
    pageNo = 1;
    addSubscribe(
        mModel.getAcgNews(HtmlConstant.BASE_URL + mView.getNewsUrl(pageNo))
            .compose(RxUtil.<AcgNewsPage>rxSchedulerHelper())
            .subscribeWith(new CommonSubscriber<AcgNewsPage>(mView) {

              @Override
              public void onError(Throwable e) {
                super.onError(e);
                mView.showPageError();
              }

              @Override
              public void onComplete() {
                mView.hideLoading();
              }

              @Override
              public void onNext(AcgNewsPage acgNewsPage) {
                if (acgNewsPage.getAcgNewsList().size() > 0) {
                  mView.showAcgNews(acgNewsPage.getAcgNewsList());
                  mView.showPageContent();
                } else {
                  mView.showPageEmpty();
                }
              }
            })
    );
  }

  public void getMoreAcgNewsList() {
    addSubscribe(
        mModel.getAcgNews(HtmlConstant.BASE_URL + mView.getNewsUrl(++pageNo))
            .compose(RxUtil.<AcgNewsPage>rxSchedulerHelper())
            .subscribeWith(new CommonSubscriber<AcgNewsPage>(mView) {
              @Override
              public void onNext(AcgNewsPage acgNewsPage) {
                int pageCount;
                try {
                  pageCount = Integer.parseInt(acgNewsPage.getPageCount());
                } catch (NumberFormatException e) {
                  e.printStackTrace();
                  pageCount = 1;
                }
                mView.showMoreAcgNews(acgNewsPage.getAcgNewsList(),
                    pageNo < pageCount);
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
