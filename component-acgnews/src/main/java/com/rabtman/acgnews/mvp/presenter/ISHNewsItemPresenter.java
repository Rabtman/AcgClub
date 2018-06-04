package com.rabtman.acgnews.mvp.presenter;

import com.rabtman.acgnews.mvp.contract.ISHNewsContract;
import com.rabtman.acgnews.mvp.model.entity.SHPage;
import com.rabtman.common.base.CommonSubscriber;
import com.rabtman.common.base.mvp.BasePresenter;
import com.rabtman.common.di.scope.FragmentScope;
import com.rabtman.common.utils.RxUtil;
import javax.inject.Inject;

/**
 * @author Rabtman
 */
@FragmentScope
public class ISHNewsItemPresenter extends
    BasePresenter<ISHNewsContract.Model, ISHNewsContract.View> {

  //当前页面位置
  private int pageNo = 1;

  @Inject
  public ISHNewsItemPresenter(ISHNewsContract.Model model,
      ISHNewsContract.View rootView) {
    super(model, rootView);
  }

  public void getAcgNewsList() {
    pageNo = 1;
    addSubscribe(
        mModel.getAcgNews(pageNo)
            .compose(RxUtil.<SHPage>rxSchedulerHelper())
            .subscribeWith(new CommonSubscriber<SHPage>(mView) {

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
              public void onNext(SHPage page) {
                if (page.getPostItems().size() > 0) {
                  mView.showAcgNews(page.getPostItems());
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
        mModel.getAcgNews(++pageNo)
            .compose(RxUtil.<SHPage>rxSchedulerHelper())
            .subscribeWith(new CommonSubscriber<SHPage>(mView) {
              @Override
              public void onNext(SHPage page) {
                mView.showMoreAcgNews(page.getPostItems(),
                    page.getCurrentPage() < page.getTotalPages());
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
