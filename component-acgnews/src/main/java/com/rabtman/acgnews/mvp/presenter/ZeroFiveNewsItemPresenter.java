package com.rabtman.acgnews.mvp.presenter;

import com.rabtman.acgnews.base.constant.HtmlConstant;
import com.rabtman.acgnews.mvp.contract.ZeroFiveNewsContract;
import com.rabtman.acgnews.mvp.model.jsoup.ZeroFiveNewsPage;
import com.rabtman.common.base.CommonSubscriber;
import com.rabtman.common.base.mvp.BasePresenter;
import com.rabtman.common.di.scope.FragmentScope;
import com.rabtman.common.utils.RxUtil;
import javax.inject.Inject;

/**
 * @author Rabtman
 */
@FragmentScope
public class ZeroFiveNewsItemPresenter extends
    BasePresenter<ZeroFiveNewsContract.Model, ZeroFiveNewsContract.View> {

  //当前页面位置
  private int pageNo = 1;

  @Inject
  public ZeroFiveNewsItemPresenter(ZeroFiveNewsContract.Model model,
      ZeroFiveNewsContract.View rootView) {
    super(model, rootView);
  }

  public void getAcgNewsList() {
    pageNo = 1;
    addSubscribe(
        mModel.getAcgNews(HtmlConstant.ZERO_FIVE_URL + mView.getNewsUrl(pageNo))
            .compose(RxUtil.<ZeroFiveNewsPage>rxSchedulerHelper())
            .subscribeWith(new CommonSubscriber<ZeroFiveNewsPage>(mView) {

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
              public void onNext(ZeroFiveNewsPage zeroFiveNewsPage) {
                if (zeroFiveNewsPage.getZeroFiveNewsList().size() > 0) {
                  mView.showAcgNews(zeroFiveNewsPage.getZeroFiveNewsList());
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
        mModel.getAcgNews(HtmlConstant.ZERO_FIVE_URL + mView.getNewsUrl(++pageNo))
            .compose(RxUtil.<ZeroFiveNewsPage>rxSchedulerHelper())
            .subscribeWith(new CommonSubscriber<ZeroFiveNewsPage>(mView) {
              @Override
              public void onNext(ZeroFiveNewsPage zeroFiveNewsPage) {
                int pageCount;
                try {
                  pageCount = Integer.parseInt(zeroFiveNewsPage.getPageCount());
                } catch (NumberFormatException e) {
                  e.printStackTrace();
                  pageCount = 1;
                }
                mView.showMoreAcgNews(zeroFiveNewsPage.getZeroFiveNewsList(),
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
