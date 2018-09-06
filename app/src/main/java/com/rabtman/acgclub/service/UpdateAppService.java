package com.rabtman.acgclub.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.rabtman.acgclub.BuildConfig;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.api.AcgService;
import com.rabtman.acgclub.base.constant.HtmlConstant;
import com.rabtman.acgclub.base.constant.IntentConstant;
import com.rabtman.acgclub.mvp.model.entity.VersionInfo;
import com.rabtman.common.base.CommonSubscriber;
import com.rabtman.common.utils.IntentUtils;
import com.rabtman.common.utils.RxUtil;
import com.rabtman.common.utils.Utils;
import es.dmoral.toasty.Toasty;

/**
 * @author Rabtman
 */
public class UpdateAppService extends Service {

  private boolean isManual = false;

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    if (intent != null) {
      isManual = intent.getBooleanExtra(IntentConstant.CHECK_APP_UPDATE_MANUAL, false);
    }

    String versionUrl = HtmlConstant.APP_VERSION_URL;
    if (BuildConfig.APP_DEBUG) {
      versionUrl = versionUrl.concat("?debug=on");
    }

    Utils.getAppComponent()
        .repositoryManager()
        .obtainRetrofitService(AcgService.class)
        .getVersionInfo(versionUrl)
        .compose(RxUtil.<VersionInfo>rxSchedulerHelper())
        .subscribeWith(new CommonSubscriber<VersionInfo>(getBaseContext()) {
          @Override
          public void onNext(VersionInfo versionInfo) {
            if (versionInfo.getVersionCode() > BuildConfig.VERSION_CODE) { //比对版本信息
              if (TextUtils.isEmpty(versionInfo.getAppLink())) { //判断下载地址是否为空
                if (isManual) { //如果是手动检查更新，则弹出异常提示
                  Toasty.info(
                      getBaseContext(),
                      getString(R.string.msg_error_version_info),
                      Toast.LENGTH_SHORT
                  ).show();
                }
                return;
              }
              showUpdateDialog(versionInfo);
            } else if (isManual) {
              Toasty.info(
                  getBaseContext(),
                  getString(R.string.msg_no_version_info),
                  Toast.LENGTH_SHORT
              ).show();
            }
          }
        });
    return super.onStartCommand(intent, flags, startId);
  }

  public void showUpdateDialog(final VersionInfo versionInfo) {
    StyledDialog.buildMdAlert("版本更新", versionInfo.getDesc(), new MyDialogListener() {
      @Override
      public void onFirst() {
        //跳转更新APP版本
        if (versionInfo.getAppLink().startsWith("http")) {
          IntentUtils.go2Browser(getBaseContext(), versionInfo.getAppLink());
        } else if (versionInfo.getAppLink().startsWith("market")) {
          IntentUtils.go2Market(getBaseContext(), versionInfo.getAppLink());
        }
      }

      @Override
      public void onSecond() {
      }
    }).setBtnText("现在升级", "下次再说").show();
  }

}
