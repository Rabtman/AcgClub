package com.rabtman.acgclub.mvp.presenter;

import com.rabtman.acgclub.BuildConfig;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.mvp.contract.MainContract;
import com.rabtman.acgclub.mvp.model.entity.VersionInfo;
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
public class MainPresenter extends
    BasePresenter<MainContract.Model, MainContract.View> {

  @Inject
  public MainPresenter(MainContract.Model model,
      MainContract.View rootView) {
    super(model, rootView);
  }

  public void getVersionInfo(final boolean isManual) {
    addSubscribe(
        mModel.getVersionInfo()
            .compose(RxUtil.<VersionInfo>rxSchedulerHelper())
            .subscribeWith(new CommonSubscriber<VersionInfo>(mView) {
              @Override
              protected void onStart() {
                if (isManual) {
                  mView.showLoading();
                }
                super.onStart();
              }

              @Override
              public void onComplete() {
                if (isManual) {
                  mView.hideLoading();
                }
              }

              @Override
              public void onNext(VersionInfo versionInfo) {
                LogUtil.d("getVersionInfo:\n" + versionInfo.toString());
                if (versionInfo.getVersionCode() > BuildConfig.VERSION_CODE) {
                  mView.showUpdateDialog(versionInfo);
                } else if (isManual) {
                  mView.showMsg(R.string.msg_no_version_info);
                }
              }
            })
    );
  }

}
