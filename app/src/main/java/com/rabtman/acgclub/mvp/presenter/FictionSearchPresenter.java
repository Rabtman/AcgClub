package com.rabtman.acgclub.mvp.presenter;

import com.rabtman.acgclub.base.constant.HtmlConstant;
import com.rabtman.acgclub.mvp.contract.FictionSearchContract;
import com.rabtman.acgclub.mvp.model.jsoup.Fiction;
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
public class FictionSearchPresenter extends
    BasePresenter<FictionSearchContract.Model, FictionSearchContract.View> {

  @Inject
  public FictionSearchPresenter(FictionSearchContract.Model model,
      FictionSearchContract.View rootView) {
    super(model, rootView);
  }

  public void searchFiction(String keyword) {
    addSubscribe(
        mModel.getSearchResult(HtmlConstant.FICTION_SEARCH_URL + keyword)
            .compose(RxUtil.<Fiction>rxSchedulerHelper())
            .subscribeWith(new CommonSubscriber<Fiction>(mView) {
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
              public void onNext(Fiction searchResult) {
                LogUtil.d("searchFiction" + searchResult.toString());
                mView.showSearchResult(searchResult);
              }
            })
    );
  }

}
