package com.rabtman.acgclub.mvp.presenter;

import com.rabtman.acgclub.base.constant.HtmlConstant;
import com.rabtman.acgclub.mvp.contract.FictionContract;
import com.rabtman.acgclub.mvp.model.jsoup.Fiction;
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
public class FictionPresenter extends
    BasePresenter<FictionContract.Model, FictionContract.View> {

  @Inject
  public FictionPresenter(FictionContract.Model model, FictionContract.View rootView) {
    super(model, rootView);
  }

  public void getFictionRecent() {
    addSubscribe(
        mModel.getFictionRecent(HtmlConstant.FICTION_URL + "#n")
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
              public void onNext(Fiction fictionRecent) {
                LogUtil.d("getFictionRecent" + fictionRecent.toString());
                mView.showFictionRecent(fictionRecent);
              }
            })
    );
  }

}
