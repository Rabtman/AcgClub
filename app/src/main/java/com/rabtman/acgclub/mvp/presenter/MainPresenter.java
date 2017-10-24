package com.rabtman.acgclub.mvp.presenter;

import android.text.TextUtils;
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

  /**
   * 获取APP版本信息
   *
   * @param isManual 是否是通过设置页面手动点击的检查更新，如果是，则弹出加载框；否则，在后台静默检查更新
   */
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
                if (versionInfo.getVersionCode() > BuildConfig.VERSION_CODE) { //比对版本信息
                  if (TextUtils.isEmpty(versionInfo.getAppLink())) { //判断下载地址是否为空
                    if (isManual) { //如果是手动检查更新，则弹出异常提示
                      mView.showMsg(R.string.msg_error_version_info);
                    }
                  }
                  mView.showUpdateDialog(versionInfo);
                } else if (isManual) {
                  mView.showMsg(R.string.msg_no_version_info);
                }
              }
            })
    );
  }

}
