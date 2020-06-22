package com.rabtman.acgclub.mvp.ui.activity;

import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.hss01248.dialog.StyledDialog;
import com.rabtman.acgclub.BuildConfig;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.base.constant.IntentConstant;
import com.rabtman.acgclub.service.UpdateAppService;
import com.rabtman.common.base.SimpleActivity;
import com.rabtman.common.utils.IntentUtils;
import com.rabtman.router.RouterConstants;

/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_SETTING)
public class SettingActivity extends SimpleActivity {

  @BindView(R.id.toolbar)
  Toolbar mToolBar;
  @BindView(R.id.tv_setting_version)
  TextView tvSettingVersion;

  @Override
  protected int getLayoutId() {
    return R.layout.activity_setting;
  }

  @Override
  protected void initData() {
    setToolBar(mToolBar, getString(R.string.nav_setting));
    tvSettingVersion.setText("当前版本号 v" + BuildConfig.VERSION_NAME);
  }

  @OnClick(R.id.tv_setting_opinion)
  public void opinionFeedback() {
    FeedbackAPI.openFeedbackActivity();
  }

  @OnClick(R.id.layout_setting_update)
  public void checkAppVersion() {
    Intent updateAppIntent = new Intent(this, UpdateAppService.class);
    updateAppIntent.putExtra(IntentConstant.CHECK_APP_UPDATE_MANUAL, true);
    startService(updateAppIntent);
  }

  @OnClick(R.id.tv_setting_opensource)
  public void go2projectSite() {
    IntentUtils.go2Browser(this, "https://github.com/Rabtman/AcgClub");
  }

  @OnClick(R.id.tv_setting_about)
  public void showAbout() {
    StyledDialog.buildCustom(
        LayoutInflater.from(
            this).inflate(R.layout.view_setting_about, null),
        Gravity.CENTER)
        .setCancelable(true, true)
        .show();
  }
}
